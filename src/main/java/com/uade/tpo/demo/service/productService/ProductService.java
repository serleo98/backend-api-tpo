package com.uade.tpo.demo.service.productService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.demo.entity.ImageEntity;
import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.StockAndType;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.exceptions.ProductDuplicateExecption;
import com.uade.tpo.demo.repository.db.IProductRepository;
import com.uade.tpo.demo.repository.db.IStock;

@Service
public class ProductService implements IProductService {
    
    @Autowired
    private IStock stockRepository;
    
    @Autowired
    private IProductRepository productRepository;

    private TransactionController transactionController;
    
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
    public ProductoEntity createProduct(User publisherId, String brand, String category, String name,
    BigDecimal price, String description, List<StockAndType> stock, List<ImageEntity> image) throws ProductDuplicateExecption{
        List<ProductoEntity> products = productRepository.findByName(name);
        if (products.isEmpty()) {
            ProductoEntity productBuild = ProductoEntity.builder()
                    .publisherId(publisherId)
                    .brand(brand)
                    .category(category)
                    .name(name)
                    .price(price)
                    .description(description)
                    .stock(stock)
                    .image(image)
                    .build();
            stockRepository.saveAll(stock);
            productRepository.save(productBuild); //TODO: Guardar stock en base
            return productBuild;
        }
        else {
            throw new ProductDuplicateExecption();
        }
    }

    @Override
    public void purchaseProduct(Integer id, StockAndType stock) {
        Optional<ProductoEntity> productEntity = productRepository.findById(id);
        if(!productEntity.isEmpty()){
            stock.setQuantity(stock.getQuantity() - 1);
            transactionController.createTransaction();
        }
        else{
            throw new ProductNotFoundException("Product with id: " + id + " not found")
        }
    }

    @Override
    public List<ProductoEntity> getProductsBySellerId(Integer userId) {
        return productRepository.findByPublisherId(userId);
    }

    @Override
    public List<ProductoEntity> getProductsWithStock() {
        List<ProductoEntity> productsWithStock = new ArrayList<>();
        List<ProductoEntity> products = productRepository.findAll();
        for(ProductoEntity p : products){
            boolean hasStock = false;
            List<StockAndType> stocks = p.getStock();
            for(StockAndType s : stocks){
                if(s.getQuantity() >= 1){
                    hasStock = true;
                    break;
                }
            }
            if(hasStock == true){
                productsWithStock.add(p);
            }
        }  
        return productsWithStock;
    }

    @Override
    public List<ProductoEntity> getProductsFiltered(String brand, String category, String name, BigDecimal minPrice,
            BigDecimal maxPrice) {
                Set<ProductoEntity> filtered = new HashSet<>();
                if(brand != null){
                    filtered.addAll(productRepository.findByBrand(brand));
                }
                if(category != null){
                    filtered.addAll(productRepository.findByCategory(category));
                }
                if(name != null){
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

    

