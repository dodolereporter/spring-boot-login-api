package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.RoleEntity;
import fr.codesbuster.solidstock.api.payload.dto.RoleDto;
import fr.codesbuster.solidstock.api.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<RoleEntity> addRole(@RequestBody RoleDto roleDto) {

        if (roleDto == null) {
            return ResponseEntity.badRequest().build();
        }


        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(roleDto.getName());

        roleEntity = roleService.createRole(roleEntity);

        return ResponseEntity.ok(roleEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<RoleEntity>> getAllRole() {
        Iterable<RoleEntity> roleEntities = roleService.getRoles();
        return ResponseEntity.ok(roleEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleEntity> getRole(@PathVariable Long id) {
        RoleEntity roleEntity = roleService.getRole(id);
        return ResponseEntity.ok(roleEntity);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleEntity> updateRole(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        RoleEntity roleEntity = roleService.getRole(id);
        roleEntity.setName(roleDto.getName());
        roleEntity = roleService.updateRole(roleEntity);
        return ResponseEntity.ok(roleEntity);
    }

}