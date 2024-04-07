package fr.codesbuster.solidstock.api.service.impl;


import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.entity.RoleEntity;
import fr.codesbuster.solidstock.api.entity.UserEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.payload.dto.RegisterDto;
import fr.codesbuster.solidstock.api.repository.CustomerRepository;
import fr.codesbuster.solidstock.api.repository.RoleRepository;
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
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserEntity createUser(RegisterDto registerDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(registerDto.getUserName());
        userEntity.setName(registerDto.getName());
        userEntity.setFirstName(registerDto.getFirstName());
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setPassword(registerDto.getPassword());
        CustomerEntity customerEntity = customerRepository.findById((long) registerDto.getCustomerId()).orElse(null);
        if (customerEntity == null) {
            throw new APIException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        userEntity.setCustomer(customerEntity);

        userEntity = userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public List<UserEntity> getAllUsers() {
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
    public UserEntity updateUser(long id, RegisterDto registerDto) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "User Not found"));
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setName(registerDto.getName());
        userEntity.setFirstName(registerDto.getFirstName());
        CustomerEntity customerEntity = customerRepository.findById((long) registerDto.getCustomerId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Customer not found"));
        userEntity.setCustomer(customerEntity);
        RoleEntity roleEntity = roleRepository.findById((long) registerDto.getRoleId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Role not found"));
        userEntity.setRole(roleEntity);
        userEntity.setPassword(registerDto.getPassword());
        userEntity.setUserName(registerDto.getUserName());

        userRepository.save(userEntity);
        return userEntity;

    }

    @Override
    public void addRole(long id, RegisterDto registerDto) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(registerDto.getName());
    }

    @Override
    public RoleEntity getRole(long id) {
        return null;
    }

    @Override
    public List<RoleEntity> getAllRoles(long invoiceId) {
        return null;
    }

    @Override
    public void deleteRole(long id) {

    }

    @Override
    public UserEntity updateUserRole(long id, RegisterDto registerDto) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "User not found"));
        RoleEntity roleEntity = roleRepository.findById((long) registerDto.getRoleId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Role not found"));
        userEntity.setRole(roleEntity);

        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public UserEntity getUser(long id) {
        return userRepository.findById(id).orElseThrow(()-> new APIException(HttpStatus.NOT_FOUND, "User not found"));
    }
}