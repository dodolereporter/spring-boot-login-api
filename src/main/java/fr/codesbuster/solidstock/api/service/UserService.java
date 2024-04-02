package fr.codesbuster.solidstock.api.service;


import fr.codesbuster.solidstock.api.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);

    List<UserEntity> getUsers();

    void deleteUser(Long id);

    UserEntity updateUser(UserEntity userEntity);

    UserEntity getUser(Long id);
}