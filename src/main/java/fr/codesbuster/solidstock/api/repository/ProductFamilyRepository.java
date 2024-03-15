package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.ProductFamilyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductFamilyRepository extends JpaRepository<ProductFamilyEntity, Long> {

    boolean existsByName(String name);

    Optional<ProductFamilyEntity> findByName(String name);

}