package fr.codesbuster.solidstock.api.service;


import fr.codesbuster.solidstock.api.entity.SupplierEntity;

import java.util.List;

public interface SupplierService {

    SupplierEntity createSupplier(SupplierEntity quantityTypeEntity);

    List<SupplierEntity> getSuppliers();

    void deleteSupplier(Long id);

    SupplierEntity updateSupplier(SupplierEntity vatEntity);

    SupplierEntity getSupplier(Long id);
}