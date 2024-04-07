package fr.codesbuster.solidstock.api.service;


import fr.codesbuster.solidstock.api.entity.RoleEntity;

import java.util.List;

public interface RoleService {

    RoleEntity createRole(RoleEntity roleEntity);

    List<RoleEntity> getRoles();

    void deleteRole(Long id);

    RoleEntity updateRole(RoleEntity roleEntity);

    RoleEntity getRole(Long id);
}