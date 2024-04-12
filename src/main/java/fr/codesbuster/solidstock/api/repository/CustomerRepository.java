package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByCompanyName(String name);
}