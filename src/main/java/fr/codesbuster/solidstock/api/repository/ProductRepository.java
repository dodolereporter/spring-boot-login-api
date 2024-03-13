package fr.codesbuster.solidstock.api.repository;

import fr.codesbuster.solidstock.api.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
