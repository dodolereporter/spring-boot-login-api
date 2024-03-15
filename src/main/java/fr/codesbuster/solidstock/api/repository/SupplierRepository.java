package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    Optional<SupplierEntity> findByCompanyName(String name);
}