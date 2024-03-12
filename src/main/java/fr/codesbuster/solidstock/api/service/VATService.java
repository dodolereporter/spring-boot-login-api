package fr.codesbuster.solidstock.api.service;


import fr.codesbuster.solidstock.api.entity.VATEntity;

import java.util.List;

public interface VATService {

    VATEntity createVAT(VATEntity vatEntity);

    List<VATEntity> getVATs();

    void deleteVAT(Long id);

    VATEntity updateVAT(VATEntity vatEntity);

    VATEntity getVAT(Long id);
}