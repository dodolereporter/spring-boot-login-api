package fr.codesbuster.solidstock.api.service.impl;


import fr.codesbuster.solidstock.api.entity.VATEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.VATRepository;
import fr.codesbuster.solidstock.api.service.VATService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VATServiceImpl implements VATService {

    @Autowired
    VATRepository vatRepository;

    @Override
    public VATEntity createVAT(VATEntity vatEntity) {
        if (vatEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "VAT cannot be null");
        }

        if (vatRepository.existsByRate(vatEntity.getRate())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "VAT rate already exists");
        }

        return vatRepository.save(vatEntity);
    }

    @Override
    public List<VATEntity> getVATs() {
        return vatRepository.findAll();
    }

    @Override
    public void deleteVAT(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "VAT id cannot be null");
        }

        if (!vatRepository.existsById(id)) {
            throw new APIException(HttpStatus.BAD_REQUEST, "VAT does not exist");
        }

        vatRepository.deleteById(id);
    }

    @Override
    public VATEntity updateVAT(VATEntity vatEntity) {
        if (vatEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "VAT cannot be null");
        }

        if (!vatRepository.existsById(vatEntity.getId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "VAT does not exist");
        }

       return vatRepository.save(vatEntity);
    }

    @Override
    public VATEntity getVAT(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "VAT id cannot be null");
        }

        Optional<VATEntity> vatEntity = vatRepository.findById(id);

        if (vatEntity.isEmpty()) {
            throw new APIException(HttpStatus.BAD_REQUEST, "VAT does not exist");
        }

        return vatEntity.get();
    }
}