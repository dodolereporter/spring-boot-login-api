package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.OwnerCompanyEntity;

public interface OwnerCompanyService {

    OwnerCompanyEntity updateOwnerCompany(OwnerCompanyEntity ownerCompanyEntity);

    OwnerCompanyEntity getOwnerCompany();
}
