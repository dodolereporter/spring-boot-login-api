package fr.codesbuster.solidstock.api.service;


import fr.codesbuster.solidstock.api.entity.UnitEntity;

import java.util.List;

public interface UnitService {

    UnitEntity createUnit(UnitEntity unitEntity);

    List<UnitEntity> getUnits();

    void deleteUnit(Long id);

    UnitEntity updateUnit(UnitEntity unitEntity);

    UnitEntity getUnit(Long id);
}