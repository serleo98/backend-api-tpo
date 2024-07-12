package com.uade.tpo.demo.service.productService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import com.uade.tpo.demo.entity.*;
import com.uade.tpo.demo.entity.dto.ProductDTO;
import com.uade.tpo.demo.entity.dto.ProductToModifiDTO;
import com.uade.tpo.demo.entity.dto.StockAndTypeDto;
import com.uade.tpo.demo.repository.cloudinary.CloudinaryRepository;
import com.uade.tpo.demo.repository.db.ImageEntityRepository;
import com.uade.tpo.demo.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.uade.tpo.demo.repository.db.IProductRepository;
import com.uade.tpo.demo.repository.db.IStock;
import com.uade.tpo.demo.repository.db.UserRepository;
import com.uade.tpo.demo.service.exceptions.ProductNotFoundException;
import com.uade.tpo.demo.service.exceptions.StockNotFoundException;
import com.uade.tpo.demo.service.exceptions.UserNotFoundException;
import com.uade.tpo.demo.service.transactionService.TransactionService;
import org.springframework.web.multipart.MultipartFile;

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
    private CloudinaryRepository cloudinaryRepository;

    @Autowired
    private ImageEntityRepository imageEntityRepository;

    public Page<ProductoEntity> getProducts(PageRequest pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public void inactivateProduct(Integer productId) {
        Optional<ProductoEntity> productdb = productRepository.findById(productId);
        if (productdb.isEmpty()) {
            throw new ProductNotFoundException("Product with id: " + productId + " not found");
        }
        ProductoEntity product = productdb.get();
        product.setStatus(Status.INACTIVE);
        productRepository.save(product);
    }

    public List<String> getBrands() {
        return productRepository.findAll().stream()
                .filter(productoEntity -> productoEntity.getStatus().equals(Status.ACTIVE))
                .map(ProductoEntity::getBrand)
                .collect(Collectors.toList());
    }

    public Optional<ProductoEntity> getProductById(Integer productId) {
        return productRepository.findById(productId);
    }

    @Transactional(rollbackFor = Throwable.class)
    public ProductoEntity createProduct(ProductDTO productDTO) {

        User currentUser = AuthUtils.getCurrentAuthUser(User.class);

            ProductoEntity productBuild = ProductoEntity.builder()
                    .publisherId(currentUser)
                    .brand(productDTO.getBrand())
                    .category(productDTO.getCategory())
                    .name(productDTO.getName())
                    .price(productDTO.getPrice())
                    .size(productDTO.getSize())
                    .color(productDTO.getColor())
                    .sex(productDTO.getSex())
                    .stock(productDTO.getStock())
                    .image(productDTO.getImage())
                    .build();

            productRepository.saveAndFlush(productBuild); //TODO: Guardar stock en base
            return productBuild;
    }

    // @Override
    // public void addPhoto(MultipartFile img, Integer productId) throws Exception {

    //     Optional<ProductoEntity> productdb = productRepository.findById(productId);
    //     if (productdb.isEmpty()) {
    //         throw new ProductNotFoundException("Product with id: " + productId + " not found");
    //     }
    //     ProductoEntity product = productdb.get();
    //     String url = cloudinaryRepository.savePhoto(img.getName(), img);
    //     ImageEntity image = ImageEntity.builder()
    //             .url(url)
    //             .build();

    //     if(product.getImage() == null){
    //         product.setImage(new ArrayList<>());
    //     }

    //     imageEntityRepository.saveAndFlush(image);
    //     product.getImage().add(image);
    //     productRepository.save(product);
    // }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public ProductoEntity modifyProduct(ProductToModifiDTO productToModifiDTO) throws Exception {
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

        if (productToModifiDTO.getSize() != null) {
            productEntity.setSize(productToModifiDTO.getSize());
        }
        if (productToModifiDTO.getColor() != null) {
            productEntity.setColor(productToModifiDTO.getColor());
        }
        if (productToModifiDTO.getSex() != null) {
            productEntity.setSex(productToModifiDTO.getSex());
        }
        if (productToModifiDTO.getStock() != null) {
            productEntity.setStock(productToModifiDTO.getStock());
        }
        if (productToModifiDTO.getImage() != null) {
            productEntity.setImage(productToModifiDTO.getImage());
        }

        productRepository.save(productEntity);

        return productEntity;
    }

    // private void modifiStock(StockAndTypeDto stock) {
    //     StockAndType st = stockRepository.getReferenceById(stock.getId());
    //     st.setSex(stock.getSex());
    //     st.setColor(stock.getColor());
    //     st.setType(stock.getType());
    //     st.setQuantity(stock.getQuantity());
    //     stockRepository.saveAndFlush(st);
    // }

    @Override
    public void purchaseProducts(List<Integer> ids, List<Integer> quantities, Integer buyerId, float discount) {
        if (ids.size() != quantities.size()) {
            throw new IllegalArgumentException("Products amount does not match with stocks amount");
        }

        for (int i = 0; i < ids.size(); i++) {
            Optional<ProductoEntity> productEntity = productRepository.findById(ids.get(i));
            if (!productEntity.isEmpty()) {
                productEntity.get().setStock(productEntity.get().getStock() - quantities.get(i));
                productRepository.saveAndFlush(productEntity.get());
            } else {
                throw new ProductNotFoundException("Product with id: " + ids.get(i) + " not found");
            }

        }
        Date currentDate = new Date();
        // transactionService.createTransaction(currentDate, ids, quantities, buyerId, discount);

    }

    @Override
    public List<ProductoEntity> getProductsBySellerId(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isEmpty()) {
            return productRepository.findByPublisherId(user.get()).stream().filter(productoEntity -> productoEntity.getStatus().equals(Status.ACTIVE)).toList();
        } else {
            throw new UserNotFoundException("Seller with id: " + userId + " not found");
        }

    }

    @Override
    public List<ProductoEntity> getProductsWithStock() {
        List<ProductoEntity> productsWithStock = new ArrayList<>();
        List<ProductoEntity> products = productRepository.findAll().stream().filter(productoEntity -> productoEntity.getStatus().equals(Status.ACTIVE)).toList();
        for (ProductoEntity p : products) {
            if(p.getStock() != null && p.getStock() > 0){
                productsWithStock.add(p);
            }
        }
        return productsWithStock;
    }

    @Override
    public List<ProductoEntity> getProductsFiltered(String brand, String category, String name, BigDecimal minPrice,
                                                    BigDecimal maxPrice) {
        List<ProductoEntity> filtered = productRepository.findAll().stream().filter(productoEntity -> productoEntity.getStatus().equals(Status.ACTIVE)).toList();
        if (brand != null) {
            List<ProductoEntity> filteredByBrand = productRepository.findByBrand(brand);
            filtered.retainAll(filteredByBrand);
        }
        if (category != null) {
            List<ProductoEntity> filteredByCategory = productRepository.findByCategory(category);
            filtered.retainAll(filteredByCategory);
        }
        if (name != null) {
            List<ProductoEntity> filteredByName = productRepository.findByName(name);
            filtered.retainAll(filteredByName);
        }
        if (minPrice != null && maxPrice != null) {
            List<ProductoEntity> filteredByMinMax = (List<ProductoEntity>) productRepository.findByPriceBetween(minPrice, maxPrice);
            filtered.retainAll(filteredByMinMax);
        } else {
            if (minPrice != null) {
                List<ProductoEntity> filteredByMin = (List<ProductoEntity>) productRepository.findByPriceGreaterThanEqual(minPrice);
                filtered.retainAll(filteredByMin);
            }
            if (maxPrice != null) {
                List<ProductoEntity> filteredByMax = (List<ProductoEntity>) productRepository.findByPriceLessThanEqual(maxPrice);
                filtered.retainAll(filteredByMax);
            }
        }

        return filtered;
    }
}

    

