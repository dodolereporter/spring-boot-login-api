package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    boolean existsByName(String name);
}