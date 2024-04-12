package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Boolean existsByEmail(String email);

    boolean existsByCustomer_Id(long id);


}