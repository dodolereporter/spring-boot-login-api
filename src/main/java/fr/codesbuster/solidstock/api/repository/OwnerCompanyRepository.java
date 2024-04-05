package fr.codesbuster.solidstock.api.repository;

import fr.codesbuster.solidstock.api.entity.OwnerCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OwnerCompanyRepository extends JpaRepository<OwnerCompanyEntity, Long> {
}
