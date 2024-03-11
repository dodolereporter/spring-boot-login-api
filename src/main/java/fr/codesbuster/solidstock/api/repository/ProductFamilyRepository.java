package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.ProductFamilyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductFamilyRepository extends JpaRepository<ProductFamilyEntity, Long> {

    boolean existsByName(String name);
}