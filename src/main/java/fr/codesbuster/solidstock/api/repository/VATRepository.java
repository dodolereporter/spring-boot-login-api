package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.RoleEntity;
import fr.codesbuster.solidstock.api.entity.VATEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VATRepository extends JpaRepository<VATEntity, Long> {

    boolean existsByRate(double rate);

}