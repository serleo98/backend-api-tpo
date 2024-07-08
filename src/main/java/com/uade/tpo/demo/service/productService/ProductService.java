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
import com.uade.tpo.demo.entity.dto.StockAndTypeDto;
import com.uade.tpo.demo.repository.db.ImageEntityRepository;
import com.uade.tpo.demo.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.uade.tpo.demo.config.CloudinaryConfig;
import com.uade.tpo.demo.entity.ImageEntity;
import com.uade.tpo.demo.entity.ProductoEntity;
import com.uade.tpo.demo.entity.StockAndType;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.repository.cloudinary.CloudinaryRepository;
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

    @Autowired
    private ImageEntityRepository imageEntityRepository;
    
    @Autowired
    private CloudinaryRepository cloudinaryRepository;

    public Page<ProductoEntity> getProducts(PageRequest pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<ProductoEntity> getProductById(Integer productId) {
        return productRepository.findById(productId);
    }

    @Transactional(rollbackFor = Throwable.class)
    public ProductoEntity createProduct(ProductDTO productDTO, List<MultipartFile> imagenes) {

        User currentUser = AuthUtils.getCurrentAuthUser(User.class);
        List<ImageEntity> fotos = new ArrayList<>();

        for (MultipartFile imagen : imagenes) {
            String url = cloudinaryRepository.savePhoto(imagen.getName(), imagen);
            ImageEntity newimg = ImageEntity.builder().url(url).build();
            imageEntityRepository.saveAndFlush(newimg);
            fotos.add(newimg);
        }
        ProductoEntity productBuild = ProductoEntity.builder()
            .publisherId(currentUser)
            .brand(productDTO.getBrand())
            .category(productDTO.getCategory())
            .name(productDTO.getName())
            .price(productDTO.getPrice())
            .description(productDTO.getDescription())
            .image(fotos) // Set the list of ImageEntity objects here
            .build();

            //productBuild.addAllStockFromDTO(productDTO.getStock());

            //stockRepository.saveAllAndFlush(productBuild.getStock());

            productRepository.saveAndFlush(productBuild); //TODO: Guardar stock en base
            return productBuild;


    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public ProductoEntity modifiProduct(ProductToModifiDTO productToModifiDTO) throws Exception {
        Optional<ProductoEntity> productdb = productRepository.findById(productToModifiDTO.getId());

        User currentUser = AuthUtils.getCurrentAuthUser(User.class);

        if (!productdb.isPresent()) {
            throw new ProductNotFoundException("Product with id: " + productToModifiDTO.getId() + " not found");
        }

        if(currentUser.getId().compareTo(productdb.get().getPublisherId().getId()) != 0){
            throw new UserNotFoundException("Publisher is not owner of product");
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

        productToModifiDTO.getStock()
                .forEach(this::modifiStock);

        productRepository.save(productEntity);

        return productEntity;
    }

    private void modifiStock(StockAndTypeDto stock) {
        StockAndType st = stockRepository.getReferenceById(stock.getId());
        st.setSex(stock.getSex());
        st.setColor(stock.getColor());
        st.setType(stock.getType());
        st.setQuantity(stock.getQuantity());
        stockRepository.saveAndFlush(st);
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

    @Override
    public void addStock(Integer productid, List<StockAndTypeDto> stockAndTypeDtos) throws Exception {

        ProductoEntity p = productRepository.findById(productid)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: " + productid + " not found"));

        stockAndTypeDtos.forEach(stockAndTypeDto -> {
            StockAndType stockAndType = StockAndType.fromDto(stockAndTypeDto);
            stockRepository.saveAndFlush(stockAndType);
            p.addStock(stockAndType);
        });
        productRepository.saveAndFlush(p);
    }
}

    

