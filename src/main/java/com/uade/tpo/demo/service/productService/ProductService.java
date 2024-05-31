package com.uade.tpo.demo.service.productService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import com.uade.tpo.demo.entity.dto.ProductDTO;
import com.uade.tpo.demo.entity.dto.ProductToModifiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.uade.tpo.demo.entity.ImageEntity;
import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.StockAndType;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.repository.db.IProductRepository;
import com.uade.tpo.demo.repository.db.IStock;
import com.uade.tpo.demo.repository.db.UserRepository;
import com.uade.tpo.demo.service.exceptions.ProductNotFoundException;
import com.uade.tpo.demo.service.exceptions.StockNotFoundException;
import com.uade.tpo.demo.service.exceptions.UserNotFoundException;
import com.uade.tpo.demo.service.transactionService.TransactionService;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IStock stockRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserRepository userRepository;

    public Page<ProductoEntity> getProducts(PageRequest pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<ProductoEntity> getProductById(Integer productId) {
        return productRepository.findById(productId);
    }


    // //Para la venta del producto tengo que cambiar los datos de List<StockAndType> stock, color, type(talle)
    // //recibir una lista de productos (puede tener 1 o N) y editar el stock de cada uno
    // //Si el stock es 0, no se puede vender
    // //Para la creacion de un producto tengo que validar que no exista un producto con el mismo nombre
    // public void sellProduct(List<ProductoEntity> products, String specifiedType) throws Exception {
    //     for (ProductoEntity product : products) {
    //         List<StockAndType> stock = product.getStock();
    //         //TODO: Pedir stock del articulo en base (devuelve lista de StockAndType)
    //         for (StockAndType stockAndType : stock) {
    //             //TODO: Agregar otro for que cicle sobre el stock de la base a menos que haga Query para saber si hay de ese stock especifico
    //             if (stockAndType.getType().equals(specifiedType)) { //Comparar stockAndType con los que estoy ciclando de la DB
    //                 if (stockAndType.getQuantity() <= 0) {
    //                     throw new Exception("Stock insuficiente");
    //                 }
    //                 stockAndType.setQuantity(stockAndType.getQuantity() - 1);
    //                 //Levantar excepcion si no hay stock de ese tipo
    //             }
    //         }
    //     }
    // }


    //jwt donde hacemos validaciones que llega al controller tenemos que saber que usuario esta logueado *** PREGUNTAR SERGIO ***
    //Verificar vigencia de token
    //verificar que tiene permisos para crear producto 

    @Transactional(rollbackFor = Throwable.class)
    public ProductoEntity createProduct(ProductDTO productDTO) {

        Optional<User> publisher = userRepository.findById(productDTO.getPublisherId());
        if (publisher.isPresent()) {

            ProductoEntity productBuild = ProductoEntity.builder()
                    .publisherId(publisher.get())
                    .brand(productDTO.getBrand())
                    .category(productDTO.getCategory())
                    .name(productDTO.getName())
                    .price(productDTO.getPrice())
                    .description(productDTO.getDescription())
                    .build();

            productBuild.addAllStockFromDTO(productDTO.getStock());

            stockRepository.saveAllAndFlush(productBuild.getStock());

            productRepository.saveAndFlush(productBuild); //TODO: Guardar stock en base
            return productBuild;
        } else {
            throw new UserNotFoundException("Publisher not found");
        }

    }

    @Override
    public ProductoEntity modifiProduct(ProductToModifiDTO productToModifiDTO) throws Exception {
        Optional<ProductoEntity> productdb = productRepository.findById(productToModifiDTO.getId());

        if (!productdb.isPresent()) {
            throw new ProductNotFoundException("Product with id: " + productToModifiDTO.getId() + " not found");
        }

        ProductoEntity productEntity = productdb.get();

        if (productToModifiDTO.getBrand() != null) {
            productEntity.setBrand(productToModifiDTO.getBrand());
        }

        if (productToModifiDTO.getCategory() != null) {
            productEntity.setCategory(productToModifiDTO.getCategory());
        }

        if (productToModifiDTO.getName() != null) {
            productEntity.setName(productToModifiDTO.getName());
        }

        if (productToModifiDTO.getPrice() != null) {
            productEntity.setPrice(productToModifiDTO.getPrice());
        }

        if (productToModifiDTO.getDescription() != null) {
            productEntity.setDescription(productToModifiDTO.getDescription());
        }

        if (productToModifiDTO.getStock() != null) {
            // Assuming that you have a method to convert StockAndTypeDto to StockAndType
            List<StockAndType> stock = productToModifiDTO.getStock().stream()
                    .map(StockAndType::fromDto)
                    .collect(Collectors.toList());
            productEntity.setStock(stock);
        }

        productRepository.save(productEntity);

        return productEntity;
    }

    @Override
    public void purchaseProducts(List<Integer> ids, List<StockAndType> stocks, List<Integer> quantities, Integer buyerId, Integer sellerId, float discount) {
        if (ids.size() != stocks.size()) {
            throw new IllegalArgumentException("Products amount does not match with stocks amount");
        }

        for (int i = 0; i < ids.size(); i++) {
            Optional<ProductoEntity> productEntity = productRepository.findById(ids.get(i));
            if (!productEntity.isEmpty()) {
                Optional<StockAndType> stock = stockRepository.findById(stocks.get(i).getId());
                if (!stock.isEmpty()) {
                    stock.get().setQuantity(stock.get().getQuantity() - quantities.get(i));
                } else {
                    throw new StockNotFoundException("Stock with id: " + stocks.get(i).getId() + " not found");
                }
            } else {
                throw new ProductNotFoundException("Product with id: " + ids.get(i) + " not found");
            }

        }
        Date currentDate = new Date();
        transactionService.createTransaction(currentDate, ids, quantities, buyerId, sellerId, discount);

    }

    @Override
    public List<ProductoEntity> getProductsBySellerId(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isEmpty()) {
            return productRepository.findByPublisherId(user.get());
        } else {
            throw new UserNotFoundException("Seller with id: " + userId + " not found");
        }

    }

    @Override
    public List<ProductoEntity> getProductsWithStock() {
        List<ProductoEntity> productsWithStock = new ArrayList<>();
        List<ProductoEntity> products = productRepository.findAll();
        for (ProductoEntity p : products) {
            boolean hasStock = false;
            List<StockAndType> stocks = p.getStock();
            for (StockAndType s : stocks) {
                if (s.getQuantity() >= 1) {
                    hasStock = true;
                    break;
                }
            }
            if (hasStock == true) {
                productsWithStock.add(p);
            }
        }
        return productsWithStock;
    }

    @Override
    public List<ProductoEntity> getProductsFiltered(String brand, String category, String name, BigDecimal minPrice,
                                                    BigDecimal maxPrice) {
        Set<ProductoEntity> filtered = new HashSet<>();
        if (brand != null) {
            filtered.addAll(productRepository.findByBrand(brand));
        }
        if (category != null) {
            filtered.addAll(productRepository.findByCategory(category));
        }
        if (name != null) {
            filtered.addAll(productRepository.findByName(name));
        }
        if (minPrice != null && maxPrice != null) {
            filtered.addAll(productRepository.findByPriceBetween(minPrice, maxPrice));
        } else {
            if (minPrice != null) {
                filtered.addAll(productRepository.findByPriceGreaterThanEqual(minPrice));
            }
            if (maxPrice != null) {
                filtered.addAll(productRepository.findByPriceLessThanEqual(maxPrice));
            }
        }


        return new ArrayList<>(filtered);
    }
}

    

