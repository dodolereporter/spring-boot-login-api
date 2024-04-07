package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.RoleEntity;
import fr.codesbuster.solidstock.api.entity.UserEntity;
import fr.codesbuster.solidstock.api.payload.dto.RegisterDto;
import fr.codesbuster.solidstock.api.repository.RoleRepository;
import fr.codesbuster.solidstock.api.repository.UserRepository;
import fr.codesbuster.solidstock.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public void createUser(@RequestBody RegisterDto registerDto) {
        log.info("Creating user");
        userService.createUser(registerDto);
    }

    @PostMapping("/{id}/role/add")
    public void addRole(@PathVariable long id, @RequestBody RegisterDto registerDto) {
        userService.addRole(id, registerDto);
    }

    @PutMapping("/{id}/role/{roleId}")
    public void updateUserRole(@PathVariable long id, @PathVariable long roleId, @RequestBody RegisterDto registerDto) {
        userService.updateUserRole(roleId, registerDto);
    }

    @GetMapping("/{id}/role/{roleId}")
    public RoleEntity getRole(@PathVariable long id, @PathVariable long roleId) {
        return userService.getRole(roleId);
    }

    @GetMapping("/{id}/role/all")
    public List<RoleEntity> getAllRows(@PathVariable long id) {
        return userService.getAllRoles(id);
    }

    @DeleteMapping("/{id}/role/{roleId}")
    public void deleteRole(@PathVariable long id, @PathVariable long roleId) {
        userService.deleteRole(roleId);
    }

    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/all")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> enableUser(@PathVariable Long id) {
        UserEntity userEntity = userService.getUser(id);
        userEntity.setDeleted(false);
        userRepository.save(userEntity);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable long id, @RequestBody RegisterDto registerDto) {
        userService.updateUser(id, registerDto);
    }

}
