package fr.codesbuster.solidstock.api.service;


import fr.codesbuster.solidstock.api.entity.ProductFamilyEntity;

import java.util.List;

public interface ProductFamilyService {

    ProductFamilyEntity createProductFamily(ProductFamilyEntity unitEntity);

    List<ProductFamilyEntity> getProductFamilies();

    void deleteProductFamily(Long id);

    ProductFamilyEntity updateProductFamily(ProductFamilyEntity unitEntity);

    ProductFamilyEntity getProductFamily(Long id);
}