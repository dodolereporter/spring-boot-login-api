package fr.codesbuster.solidstock.api.service.impl;


import fr.codesbuster.solidstock.api.entity.QuantityTypeEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.QuantityTypeRepository;
import fr.codesbuster.solidstock.api.service.QuantityTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class QuantityTypeServiceImpl implements QuantityTypeService {

    @Autowired
    QuantityTypeRepository quantityTypeRepository;

    @Override
    public QuantityTypeEntity createQuantityType(QuantityTypeEntity quantityTypeEntity) {
        if (quantityTypeEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "QuantityType cannot be null");
        }

        if (quantityTypeRepository.existsByUnit(quantityTypeEntity.getUnit())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "QuantityType already exists");
        }

        return quantityTypeRepository.save(quantityTypeEntity);
    }

    @Override
    public List<QuantityTypeEntity> getQuantityTypes() {
        return quantityTypeRepository.findAll();
    }

    @Override
    public void deleteQuantityType(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "QuantityType id cannot be null");
        }

        if (!quantityTypeRepository.existsById(id)) {
            throw new APIException(HttpStatus.BAD_REQUEST, "QuantityType does not exist");
        }

        quantityTypeRepository.deleteById(id);
    }

    @Override
    public QuantityTypeEntity updateQuantityType(QuantityTypeEntity vatEntity) {
        if (vatEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "QuantityType cannot be null");
        }

        if (!quantityTypeRepository.existsById(vatEntity.getId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "QuantityType does not exist");
        }

       return quantityTypeRepository.save(vatEntity);
    }

    @Override
    public QuantityTypeEntity getQuantityType(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "QuantityType id cannot be null");
        }

        Optional<QuantityTypeEntity> quantityTypeEntity = quantityTypeRepository.findById(id);

        if (quantityTypeEntity.isEmpty()) {
            throw new APIException(HttpStatus.BAD_REQUEST, "QuantityType does not exist");
        }

        return quantityTypeEntity.get();
    }
}