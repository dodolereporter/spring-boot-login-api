package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductEntity createProduct(ProductEntity productEntity);

    // get all products
    List<ProductEntity> getProducts();

    // get product by id
    ProductEntity getProduct(Long id);

    // update product
    ProductEntity updateProduct(ProductEntity productEntity);

    // delete product by id
    void deleteProduct(Long id);
}
