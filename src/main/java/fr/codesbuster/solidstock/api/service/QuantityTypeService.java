package fr.codesbuster.solidstock.api.service;


import fr.codesbuster.solidstock.api.entity.QuantityTypeEntity;
import fr.codesbuster.solidstock.api.entity.VATEntity;

import java.util.List;

public interface QuantityTypeService {

    QuantityTypeEntity createQuantityType(QuantityTypeEntity quantityTypeEntity);

    List<QuantityTypeEntity> getQuantityTypes();

    void deleteQuantityType(Long id);

    QuantityTypeEntity updateQuantityType(QuantityTypeEntity vatEntity);

    QuantityTypeEntity getQuantityType(Long id);
}