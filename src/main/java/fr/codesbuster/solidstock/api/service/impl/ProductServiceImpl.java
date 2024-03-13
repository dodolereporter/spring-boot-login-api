package fr.codesbuster.solidstock.api.service.impl;

import fr.codesbuster.solidstock.api.entity.ProductEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.ProductRepository;
import fr.codesbuster.solidstock.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductEntity createProduct(ProductEntity productEntity) {
        if (productEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Product cannot be null");
        }

        if (productRepository.existsById(productEntity.getId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Product already exists");
        }

        return productRepository.save(productEntity);
    }


    @Override
    public List<ProductEntity> getProducts() {
        return productRepository.findAll();
    }


    @Override
    public ProductEntity getProduct(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Product id cannot be null");
        }

        return productRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "Product does not exist"));
    }


    @Override
    public ProductEntity updateProduct(ProductEntity productEntity) {
        if (productEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Product cannot be null");
        }

        if (!productRepository.existsById(productEntity.getId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Product does not exist");
        }

        return productRepository.save(productEntity);
    }

    
    @Override
    public void deleteProduct(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Product id cannot be null");
        }

        if (!productRepository.existsById(id)) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Product does not exist");
        }

        productRepository.deleteById(id);
    }


}
