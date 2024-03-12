package fr.codesbuster.solidstock.api.service.impl;


import fr.codesbuster.solidstock.api.entity.SupplierEntity;
import fr.codesbuster.solidstock.api.repository.SupplierRepository;
import fr.codesbuster.solidstock.api.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public SupplierEntity createSupplier(SupplierEntity supplierEntity) {
        if (supplierEntity == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Supplier cannot be null");
        }

        if (supplierRepository.findById(supplierEntity.getId()).isPresent()) {
            throw new ResponseStatusException(BAD_REQUEST, "Supplier with id " + supplierEntity.getId() + " already exists");
        }

        return supplierRepository.save(supplierEntity);
    }

    @Override
    public List<SupplierEntity> getSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public void deleteSupplier(Long id) {
        if (id == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Supplier id cannot be null");
        }

        if (!supplierRepository.existsById(id)) {
            throw new ResponseStatusException(BAD_REQUEST, "Supplier does not exist");
        }

        supplierRepository.deleteById(id);
    }

    @Override
    public SupplierEntity updateSupplier(SupplierEntity supplierEntity) {
        if (supplierEntity == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Supplier cannot be null");
        }

        if (!supplierRepository.existsById(supplierEntity.getId())) {
            throw new ResponseStatusException(BAD_REQUEST, "Supplier does not exist");
        }

        return supplierRepository.save(supplierEntity);
    }

    @Override
    public SupplierEntity getSupplier(Long id) {
        if (id == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Supplier id cannot be null");
        }

        Optional<SupplierEntity> supplierEntity = supplierRepository.findById(id);

        if (supplierEntity.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Supplier does not exist");
        }

        return supplierEntity.get();
    }
}
