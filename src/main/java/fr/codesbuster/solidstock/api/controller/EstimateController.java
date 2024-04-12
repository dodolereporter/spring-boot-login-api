package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.estimate.EstimateEntity;
import fr.codesbuster.solidstock.api.entity.estimate.EstimateRowEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceRowEntity;
import fr.codesbuster.solidstock.api.payload.dto.EstimateDto;
import fr.codesbuster.solidstock.api.payload.dto.EstimateRowDto;
import fr.codesbuster.solidstock.api.payload.dto.InvoiceRowDto;
import fr.codesbuster.solidstock.api.service.EstimateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/estimate")
public class EstimateController {

    @Autowired
    private EstimateService estimateService;

    @PostMapping("/add")
    public void createEstimate(@RequestBody EstimateDto estimateDto) {
        estimateService.createEstimate(estimateDto);
    }

    @GetMapping("/{id}")
    public EstimateEntity getEstimate(@PathVariable long id) {
        EstimateEntity estimate = estimateService.getEstimate(id);
        estimate.calculateTotal();
        return estimate;
    }

    @GetMapping("/all")
    public List<EstimateEntity> getAllEstimates() {
        List<EstimateEntity> estimates = estimateService.getAllEstimates();
        estimates.forEach(EstimateEntity::calculateTotal);
        Collections.reverse(estimates);
        return estimates;
    }

    @DeleteMapping("/{id}")
    public void deleteEstimate(@PathVariable long id) {
        estimateService.deleteEstimate(id);
    }

    @PutMapping("/{id}")
    public void updateEstimate(@PathVariable long id, @RequestBody EstimateDto estimateDto) {
        estimateService.updateEstimate(id, estimateDto);
    }

    @PostMapping("/{id}/row/add")
    public void addRow(@PathVariable long id, @RequestBody EstimateRowDto estimateRowDto) {
        estimateService.addRow(id, estimateRowDto);
    }

    @GetMapping("/{id}/row/{rowId}")
    public EstimateRowEntity getRow(@PathVariable long id, @PathVariable long rowId) {
        return estimateService.getRow(rowId);
    }

    @GetMapping("/{id}/row/all")
    public List<EstimateRowEntity> getAllRows(@PathVariable long id) {
        return estimateService.getAllRows(id);
    }

    @DeleteMapping("/{id}/row/{rowId}")
    public void deleteRow(@PathVariable long id, @PathVariable long rowId) {
        estimateService.deleteRow(rowId);
    }

    @PutMapping("/{id}/row/{rowId}")
    public void updateRow(@PathVariable long id, @PathVariable long rowId, @RequestBody EstimateRowDto estimateRowDto) {
        estimateService.updateRow(rowId, estimateRowDto);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<Resource> generatePDF(@PathVariable long id) throws IOException, ParseException {
        Path path = estimateService.generatePDF(id).toPath();
        Resource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", path.getFileName().toString());
        headers.setContentType(MediaType.APPLICATION_PDF);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);

    }
}
