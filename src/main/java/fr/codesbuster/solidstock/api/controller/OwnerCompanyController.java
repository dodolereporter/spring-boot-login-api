package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.OwnerCompanyEntity;
import fr.codesbuster.solidstock.api.payload.dto.OwnerCompanyDto;
import fr.codesbuster.solidstock.api.service.OwnerCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/owner-company")
public class OwnerCompanyController {

    @Autowired
    private OwnerCompanyService ownerCompanyService;

    @GetMapping
    public ResponseEntity<OwnerCompanyEntity> getOwnerCompany() {
        OwnerCompanyEntity ownerCompanyEntity = ownerCompanyService.getOwnerCompany();
        return ResponseEntity.ok(ownerCompanyEntity);
    }

    @PutMapping
    public ResponseEntity<OwnerCompanyEntity> updateOwnerCompany(@RequestBody OwnerCompanyDto ownerCompanyDto) {
        OwnerCompanyEntity ownerCompanyEntity = ownerCompanyService.getOwnerCompany();
        ownerCompanyEntity.setCompanyName(ownerCompanyDto.getCompanyName());
        ownerCompanyEntity.setOwnerName(ownerCompanyDto.getOwnerName());
        ownerCompanyEntity.setSiret(ownerCompanyEntity.getSiret());
        ownerCompanyEntity.setSiren(ownerCompanyEntity.getSiren());
        ownerCompanyEntity.setRcs(ownerCompanyEntity.getRcs());
        ownerCompanyEntity.setStreetNumber(ownerCompanyEntity.getStreetNumber());
        ownerCompanyEntity.setStreetName(ownerCompanyEntity.getStreetName());
        ownerCompanyEntity.setZipCode(ownerCompanyDto.getZipCode());
        ownerCompanyEntity.setCity(ownerCompanyEntity.getCity());
        ownerCompanyEntity.setCountry(ownerCompanyEntity.getCountry());
        ownerCompanyEntity.setEmail(ownerCompanyEntity.getEmail());
        ownerCompanyEntity.setPhone(ownerCompanyEntity.getPhone());
        ownerCompanyEntity.setIban(ownerCompanyEntity.getIban());
        ownerCompanyEntity = ownerCompanyService.updateOwnerCompany(ownerCompanyEntity);
        return ResponseEntity.ok(ownerCompanyEntity);
    }

    @PutMapping("/image")
    public ResponseEntity<OwnerCompanyEntity> updateImage(@RequestParam("file")MultipartFile file) throws IOException {
        OwnerCompanyEntity ownerCompanyEntity = ownerCompanyService.getOwnerCompany();
        ownerCompanyEntity.setImage(file.getBytes());
        ownerCompanyEntity = ownerCompanyService.updateOwnerCompany(ownerCompanyEntity);
        return ResponseEntity.ok(ownerCompanyEntity);
    }
}
