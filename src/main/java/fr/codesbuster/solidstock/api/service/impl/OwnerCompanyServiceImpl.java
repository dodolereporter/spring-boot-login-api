package fr.codesbuster.solidstock.api.service.impl;

import fr.codesbuster.solidstock.api.entity.OwnerCompanyEntity;
import fr.codesbuster.solidstock.api.repository.OwnerCompanyRepository;
import fr.codesbuster.solidstock.api.service.OwnerCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
public class OwnerCompanyServiceImpl implements OwnerCompanyService {

    @Autowired
    OwnerCompanyRepository ownerCompanyRepository;

    @Override
    public OwnerCompanyEntity updateOwnerCompany(OwnerCompanyEntity ownerCompanyEntity) {
        if (ownerCompanyEntity == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Owner company cannot be null");
        }
        if(ownerCompanyRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Owner company not found");
        }
        return ownerCompanyRepository.save(ownerCompanyEntity);
    }

    @Override
    public OwnerCompanyEntity getOwnerCompany() {
        if(ownerCompanyRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Owner company not found");
        }
        return ownerCompanyRepository.findAll().getFirst();
    }
}
