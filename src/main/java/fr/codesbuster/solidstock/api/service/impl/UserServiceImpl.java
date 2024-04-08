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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserEntity createUser(RegisterDto registerDto) {
        UserEntity userEntity = new UserEntity();
        if (registerDto.getUserName().isEmpty() || registerDto.getUserName().isBlank()) {
            String name = registerDto.getName().toLowerCase();
            String formattedName = name.substring(0, 1).toUpperCase() + name.substring(1);
            userEntity.setUserName(registerDto.getFirstName().toLowerCase() + formattedName);
        } else {
            userEntity.setUserName(registerDto.getUserName());
        }
        userEntity.setName(registerDto.getName());
        userEntity.setFirstName(registerDto.getFirstName());
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setPassword(registerDto.getPassword());
        CustomerEntity customerEntity = customerRepository.findById((long) registerDto.getCustomerId()).orElse(null);
        if (customerEntity == null) {
            throw new APIException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        userEntity.setCustomer(customerEntity);

        // Créer une nouvelle liste de rôles pour l'utilisateur
        List<RoleEntity> roles = new ArrayList<>();

        // Récupérer le rôle correspondant à l'ID donné
        RoleEntity roleEntity = roleRepository.findById(registerDto.getRoleId()).orElse(null);
        if (roleEntity == null) {
            throw new APIException(HttpStatus.NOT_FOUND, "Role not found");
        }
        userEntity.getRoles().add(roleEntity);

        // Définir la liste de rôles pour l'utilisateur
        userEntity.setRoles(roles);

        // Enregistrer l'utilisateur dans la base de données
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
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "User Not found"));

        userEntity.setEmail(registerDto.getEmail());
        userEntity.setName(registerDto.getName());
        userEntity.setFirstName(registerDto.getFirstName());

        CustomerEntity customerEntity = customerRepository.findById((long) registerDto.getCustomerId())
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Customer not found"));
        userEntity.setCustomer(customerEntity);

        // Créer une nouvelle liste de rôles pour l'utilisateur
        List<RoleEntity> roles = new ArrayList<>();

        // Récupérer le rôle correspondant à l'ID donné
        RoleEntity roleEntity = roleRepository.findById(registerDto.getRoleId())
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Role not found"));

        // Ajouter le rôle à la liste de rôles de l'utilisateur
        roles.add(roleEntity);

        // Définir la liste de rôles pour l'utilisateur
        userEntity.setRoles(roles);

        userEntity.setPassword(registerDto.getPassword());
        userEntity.setUserName(registerDto.getUserName());

        userRepository.save(userEntity);
        return userEntity;
    }


    @Override
    public void addRole(long id, long roleId) {
//        UserEntity userEntity = userService.getUser(id);
//        userEntity.getRoles().add(roleService.getRole(roleId));
//        userRepository.save(userEntity);

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "User not found"));

        RoleEntity roleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Role not found"));

        userEntity.getRoles().add(roleEntity);
        userRepository.save(userEntity);
    }

    @Override
    public RoleEntity getRole(long id) {
        return roleRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Role not found"));
    }

    @Override
    public List<RoleEntity> getAllRoles(long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "User not found"));
        return userEntity.getRoles();
    }

    @Override
    public void deleteRole(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public UserEntity updateUserRole(long id, RegisterDto registerDto) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "User not found"));

        // Créer une nouvelle liste de rôles pour l'utilisateur
        List<RoleEntity> roles = new ArrayList<>();

        // Récupérer le rôle correspondant à l'ID donné
        RoleEntity roleEntity = roleRepository.findById(registerDto.getRoleId())
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Role not found"));

        // Ajouter le rôle à la liste de rôles de l'utilisateur
        roles.add(roleEntity);

        // Définir la liste de rôles pour l'utilisateur
        userEntity.setRoles(roles);

        userRepository.save(userEntity);
        return userEntity;
    }


    @Override
    public UserEntity getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "User not found"));
    }
}