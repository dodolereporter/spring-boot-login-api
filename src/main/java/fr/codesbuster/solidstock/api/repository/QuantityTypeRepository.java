package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.ProductFamilyEntity;
import fr.codesbuster.solidstock.api.entity.QuantityTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuantityTypeRepository extends JpaRepository<QuantityTypeEntity, Long> {
    Optional<QuantityTypeEntity> findByUnit(String unit);

    boolean existsByUnit(String unit);

}