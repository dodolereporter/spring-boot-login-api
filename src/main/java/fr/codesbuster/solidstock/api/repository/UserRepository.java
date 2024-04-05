package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserNameOrEmail(String userName, String email);

    Optional<UserEntity> findByUserName(String userName);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

}