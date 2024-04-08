package fr.codesbuster.solidstock.api.service;


import fr.codesbuster.solidstock.api.entity.UserEntity;
import fr.codesbuster.solidstock.api.payload.dto.RegisterDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<UserEntity> createUser(RegisterDto registerDto);

    ResponseEntity<UserEntity> addRoleToUser(long id, long roleId);

    ResponseEntity<UserEntity> removeRoleFromUser(long id, long roleId);

    ResponseEntity<UserEntity> getUser(long id);

    List<UserEntity> getAllUsers();

    void deleteUser(Long id);

    ResponseEntity<UserEntity> updateUser(long id, RegisterDto registerDto);

}