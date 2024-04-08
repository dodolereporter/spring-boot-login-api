package fr.codesbuster.solidstock.api.service;


import fr.codesbuster.solidstock.api.entity.RoleEntity;
import fr.codesbuster.solidstock.api.entity.UserEntity;
import fr.codesbuster.solidstock.api.payload.dto.RegisterDto;

import java.util.List;

public interface UserService {
    UserEntity createUser(RegisterDto registerDto);

    UserEntity getUser(long id);

    List<UserEntity> getAllUsers();

    void deleteUser(Long id);

    UserEntity updateUser(long id, RegisterDto registerDto);

    void addRole(long id, long roleId);

    RoleEntity getRole(long id);

    List<RoleEntity> getAllRoles(long userId);

    void deleteRole(long id);

    UserEntity updateUserRole(long id, RegisterDto registerDto);

}