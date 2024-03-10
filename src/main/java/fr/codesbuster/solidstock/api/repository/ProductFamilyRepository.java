package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.ProductFamilyEntity;
import fr.codesbuster.solidstock.api.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductFamilyRepository extends JpaRepository<ProductFamilyEntity, Long> {
    Optional<ProductFamilyEntity> findByName(String name);
}