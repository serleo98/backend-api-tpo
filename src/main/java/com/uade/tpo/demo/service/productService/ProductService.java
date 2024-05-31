package com.uade.tpo.demo.service.productService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
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
    
    public Page<ProductoEntity> getProducts(PageRequest pageable) {
        return productRepository.findAll(pageable);
    }
    
    public Optional<ProductoEntity> getProductById(Integer productId) {
        return productRepository.findById(productId);
    }
    
    
    //Para la venta del producto tengo que cambiar los datos de List<StockAndType> stock, color, type(talle)
    //recibir una lista de productos (puede tener 1 o N) y editar el stock de cada uno
    //Si el stock es 0, no se puede vender
    //Para la creacion de un producto tengo que validar que no exista un producto con el mismo nombre
    public void sellProduct(List<ProductoEntity> products, String specifiedType) throws Exception {
        for (ProductoEntity product : products) {
            List<StockAndType> stock = product.getStock();
            //TODO: Pedir stock del articulo en base (devuelve lista de StockAndType)
            for (StockAndType stockAndType : stock) {
                //TODO: Agregar otro for que cicle sobre el stock de la base a menos que haga Query para saber si hay de ese stock especifico
                if (stockAndType.getType().equals(specifiedType)) { //Comparar stockAndType con los que estoy ciclando de la DB
                    if (stockAndType.getQuantity() <= 0) {
                        throw new Exception("Stock insuficiente");
                    }
                    stockAndType.setQuantity(stockAndType.getQuantity() - 1);
                    //Levantar excepcion si no hay stock de ese tipo
                }
            }
        }
    }
    

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
    }

    

