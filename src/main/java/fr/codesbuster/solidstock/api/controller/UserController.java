package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.entity.RoleEntity;
import fr.codesbuster.solidstock.api.entity.UserEntity;
import fr.codesbuster.solidstock.api.payload.dto.RegisterDto;
import fr.codesbuster.solidstock.api.repository.CustomerRepository;
import fr.codesbuster.solidstock.api.repository.RoleRepository;
import fr.codesbuster.solidstock.api.repository.UserRepository;
import fr.codesbuster.solidstock.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserEntity> addUser(@RequestBody RegisterDto registerDto) {

        RoleEntity roleEntity = roleRepository.findById((long) registerDto.getRoleId()).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Role cannot be null"));
        CustomerEntity customerEntity = customerRepository.findById((long) registerDto.getCustomerId()).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Customer cannot be null"));

        UserEntity userEntity = new UserEntity();
        userEntity.setName(registerDto.getName());
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setUserName(registerDto.getUsername());
        userEntity.setPassword(registerDto.getPassword());
        userEntity.setRole(roleEntity);
        userEntity.setCustomer(customerEntity);

        userEntity = userService.createUser(userEntity);
        return ResponseEntity.ok(userEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<UserEntity>> getAllCustomer() {
        Iterable<UserEntity> customerEntities = userService.getUsers();
        return ResponseEntity.ok(customerEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable Long id) {
        UserEntity userEntity = userService.getUser(id);
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        UserEntity userEntity = userService.getUser(id);
        userEntity.setDeleted(true);
        userRepository.save(userEntity);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> enableUser(@PathVariable Long id) {
        UserEntity userEntity = userService.getUser(id);
        userEntity.setDeleted(false);
        userRepository.save(userEntity);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody RegisterDto registerDto) {
        RoleEntity roleEntity = roleRepository.findById((long) registerDto.getRoleId()).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Role cannot be null"));

        UserEntity userEntity = userService.getUser(id);
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setPassword(registerDto.getPassword());
        userEntity.setRole(roleEntity);
        userEntity.setUserName(registerDto.getUsername());
        userEntity = userService.updateUser(userEntity);
        return ResponseEntity.ok(userEntity);
    }

}
