package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

    boolean existsByUnit(String unit);
}