package fr.codesbuster.solidstock.api.service.impl;


import fr.codesbuster.solidstock.api.entity.UserEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.UserRepository;
import fr.codesbuster.solidstock.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        if (userEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "User cannot be null");
        }

        if (userRepository.existsById(userEntity.getId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "User already exists");
        }

        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "User id cannot be null");
        }

        if (!userRepository.existsById(id)) {
            throw new APIException(HttpStatus.BAD_REQUEST, "User does not exist");
        }

        userRepository.deleteById(id);
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        if (userEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "User cannot be null");
        }

        if (!userRepository.existsById(userEntity.getId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "User does not exist");
        }

        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUser(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "User id cannot be null");
        }

        Optional<UserEntity> userEntity = userRepository.findById(id);

        if (userEntity.isEmpty()) {
            throw new APIException(HttpStatus.BAD_REQUEST, "User does not exist");
        }

        return userEntity.get();
    }
}