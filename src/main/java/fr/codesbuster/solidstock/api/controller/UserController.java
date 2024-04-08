package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.UserEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.payload.dto.RegisterDto;
import fr.codesbuster.solidstock.api.repository.UserRepository;
import fr.codesbuster.solidstock.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserEntity> createUser(@RequestBody RegisterDto registerDto) {
        log.info("Creating user : {}", registerDto);
        return userService.createUser(registerDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Role not found"));
        userEntity.setDeleted(true);
        userEntity.getCustomer().setDisabled(true);
        userRepository.save(userEntity);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserEntity> enableUser(@PathVariable Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "User not found"));
        userEntity.setDeleted(false);
        userEntity.getCustomer().setDisabled(false);
        userRepository.save(userEntity);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable long id, @RequestBody RegisterDto registerDto) {
        return userService.updateUser(id, registerDto);
    }

    @PostMapping("/{id}/role/{role}")
    public ResponseEntity<UserEntity> addRole(@PathVariable long id, @PathVariable long role) {
        return userService.addRoleToUser(id, role);
    }

    @DeleteMapping("/{id}/role/{role}")
    public ResponseEntity<UserEntity> removeRole(@PathVariable long id, @PathVariable long role) {
        return userService.removeRoleFromUser(id, role);
    }

}