package fr.codesbuster.solidstock.api.service.impl;


import fr.codesbuster.solidstock.api.entity.UnitEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.UnitRepository;
import fr.codesbuster.solidstock.api.service.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitRepository unitRepository;

    @Override
    public UnitEntity createUnit(UnitEntity unitEntity) {
        if (unitEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Unit cannot be null");
        }

        if (unitRepository.existsByUnit(unitEntity.getUnit())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Unit already exists");
        }

        return unitRepository.save(unitEntity);
    }

    @Override
    public List<UnitEntity> getUnits() {
        return unitRepository.findAll();
    }

    @Override
    public void deleteUnit(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Unit id cannot be null");
        }

        if (!unitRepository.existsById(id)) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Unit does not exist");
        }

        unitRepository.deleteById(id);
    }

    @Override
    public UnitEntity updateUnit(UnitEntity unitEntity) {
        if (unitEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Unit cannot be null");
        }

        if (!unitRepository.existsById(unitEntity.getId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Unit does not exist");
        }

       return unitRepository.save(unitEntity);
    }

    @Override
    public UnitEntity getUnit(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Unit id cannot be null");
        }

        Optional<UnitEntity> unitEntity = unitRepository.findById(id);

        if (unitEntity.isEmpty()) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Unit does not exist");
        }

        return unitEntity.get();
    }
}