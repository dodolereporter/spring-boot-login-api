package fr.codesbuster.solidstock.api.service.impl;


import fr.codesbuster.solidstock.api.entity.RoleEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.RoleRepository;
import fr.codesbuster.solidstock.api.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public RoleEntity createRole(RoleEntity roleEntity) {
        if (roleEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Role cannot be null");
        }

        if (roleRepository.existsByName(roleEntity.getName())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Role already exists");
        }

        return roleRepository.save(roleEntity);
    }

    @Override
    public List<RoleEntity> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteRole(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Role id cannot be null");
        }

        if (!roleRepository.existsById(id)) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Role does not exist");
        }

        roleRepository.deleteById(id);
    }

    @Override
    public RoleEntity updateRole(RoleEntity vatEntity) {
        if (vatEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Role cannot be null");
        }

        if (!roleRepository.existsById(vatEntity.getId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Role does not exist");
        }

       return roleRepository.save(vatEntity);
    }

    @Override
    public RoleEntity getRole(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Role id cannot be null");
        }

        Optional<RoleEntity> roleEntity = roleRepository.findById(id);

        if (roleEntity.isEmpty()) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Role does not exist");
        }

        return roleEntity.get();
    }
}