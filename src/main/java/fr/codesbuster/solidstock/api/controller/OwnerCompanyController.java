package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.OwnerCompanyEntity;
import fr.codesbuster.solidstock.api.payload.dto.OwnerCompanyDto;
import fr.codesbuster.solidstock.api.repository.OwnerCompanyRepository;
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
    private OwnerCompanyRepository ownerCompanyRepository;

    @Autowired
    private OwnerCompanyService ownerCompanyService;

    @GetMapping
    public ResponseEntity<OwnerCompanyEntity> getOwnerCompany() {
        OwnerCompanyEntity ownerCompanyEntity = ownerCompanyService.getOwnerCompany();
        return ResponseEntity.ok(ownerCompanyEntity);
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getOwnerCompanyImage() {
        OwnerCompanyEntity ownerCompanyEntity = ownerCompanyService.getOwnerCompany();
        return ResponseEntity.ok(ownerCompanyEntity.getImage());
    }

    @PutMapping
    public ResponseEntity<OwnerCompanyEntity> updateOwnerCompany(@RequestBody OwnerCompanyDto ownerCompanyDto) {
        log.info("{}", ownerCompanyDto);
        OwnerCompanyEntity ownerCompanyEntity = null;
        if(ownerCompanyRepository.findAll().isEmpty()) {
            ownerCompanyEntity = new OwnerCompanyEntity();
        } else {
            ownerCompanyEntity = ownerCompanyService.getOwnerCompany();
        }
        ownerCompanyEntity.setCompanyName(ownerCompanyDto.getCompanyName());
        ownerCompanyEntity.setOwnerName(ownerCompanyDto.getOwnerName());
        ownerCompanyEntity.setSiret(ownerCompanyDto.getSiret());
        ownerCompanyEntity.setSiren(ownerCompanyDto.getSiren());
        ownerCompanyEntity.setRcs(ownerCompanyDto.getRcs());
        ownerCompanyEntity.setStreetNumber(ownerCompanyDto.getStreetNumber());
        ownerCompanyEntity.setStreetName(ownerCompanyDto.getStreetName());
        ownerCompanyEntity.setZipCode(ownerCompanyDto.getZipCode());
        ownerCompanyEntity.setCity(ownerCompanyDto.getCity());
        ownerCompanyEntity.setCountry(ownerCompanyDto.getCountry());
        ownerCompanyEntity.setEmail(ownerCompanyDto.getEmail());
        ownerCompanyEntity.setPhone(ownerCompanyDto.getPhone());
        ownerCompanyEntity.setIban(ownerCompanyDto.getIban());
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
