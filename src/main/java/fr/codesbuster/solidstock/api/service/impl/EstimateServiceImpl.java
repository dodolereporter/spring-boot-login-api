package fr.codesbuster.solidstock.api.service.impl;

import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.entity.estimate.EstimateEntity;
import fr.codesbuster.solidstock.api.entity.estimate.EstimateRowEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceRowEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.payload.dto.EstimateDto;
import fr.codesbuster.solidstock.api.payload.dto.EstimateRowDto;
import fr.codesbuster.solidstock.api.payload.dto.InvoiceDto;
import fr.codesbuster.solidstock.api.payload.dto.InvoiceRowDto;
import fr.codesbuster.solidstock.api.repository.CustomerRepository;
import fr.codesbuster.solidstock.api.repository.EstimateRepository;
import fr.codesbuster.solidstock.api.repository.EstimateRowRepository;
import fr.codesbuster.solidstock.api.repository.ProductRepository;
import fr.codesbuster.solidstock.api.service.EstimatePDFService;
import fr.codesbuster.solidstock.api.service.EstimateService;
import fr.codesbuster.solidstock.api.service.OwnerCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@Service
public class EstimateServiceImpl implements EstimateService {
    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private EstimateRowRepository estimateRowRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EstimatePDFService estimatePDFService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerCompanyService ownerCompanyService;

    @Override
    public EstimateEntity createEstimate(EstimateDto estimateDto) {
        EstimateEntity estimateEntity = new EstimateEntity();
        estimateEntity.setName(estimateDto.getName());
        estimateEntity.setDescription(estimateDto.getDescription());
        CustomerEntity customerEntity = customerRepository.findById(estimateDto.getCustomerId()).orElse(null);
        if (customerEntity == null) {
            throw new APIException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        estimateEntity.setCustomer(customerEntity);

        estimateEntity = estimateRepository.save(estimateEntity);
        return estimateEntity;
    }

    @Override
    public EstimateEntity getEstimate(long id) {
        return estimateRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Estimate not found"));
    }

    @Override
    public List<EstimateEntity> getAllEstimates() {
        return estimateRepository.findAll();
    }

    @Override
    public void deleteEstimate(long id) {
        estimateRepository.deleteById(id);
    }

    @Override
    public EstimateEntity updateEstimate(long id, EstimateDto estimateDto) {
        EstimateEntity estimateEntity = estimateRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Estimate not found"));
        estimateEntity.setName(estimateDto.getName());
        estimateEntity.setDescription(estimateDto.getDescription());
        CustomerEntity customerEntity = customerRepository.findById(estimateDto.getCustomerId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Customer not found"));
        estimateEntity.setCustomer(customerEntity);

        estimateRepository.save(estimateEntity);
        return estimateEntity;
    }

    @Override
    public void addRow(long id, EstimateRowDto estimateRowDto) {
        EstimateRowEntity estimateRowEntity = new EstimateRowEntity();
        estimateRowEntity.setQuantity(estimateRowDto.getQuantity());
        estimateRowEntity.setSellPrice(estimateRowDto.getSellPrice());
        EstimateEntity estimateEntity = estimateRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Estimate not found"));
        estimateRowEntity.setEstimate(estimateEntity);
        estimateRowEntity.setProduct(productRepository.findById(estimateRowDto.getProductId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Product not found")));

        estimateRowRepository.save(estimateRowEntity);
    }

    @Override
    public EstimateRowEntity getRow(long id) {
        return estimateRowRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "EstimateRow not found"));
    }

    @Override
    public List<EstimateRowEntity> getAllRows(long estimateId) {
        return estimateRowRepository.findByEstimate_Id(estimateId);
    }

    @Override
    public void deleteRow(long id) {
        estimateRowRepository.deleteById(id);
    }

    @Override
    public EstimateRowEntity updateRow(long id, EstimateRowDto estimateRowDto) {
        EstimateRowEntity estimateRowEntity = estimateRowRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "EstimateRow not found"));
        estimateRowEntity.setQuantity(estimateRowDto.getQuantity());
        estimateRowEntity.setSellPrice(estimateRowDto.getSellPrice());
        estimateRowEntity.setProduct(productRepository.findById(estimateRowDto.getProductId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Product not found")));

        estimateRowRepository.save(estimateRowEntity);
        return estimateRowEntity;
    }

    @Override
    public File generatePDF(long id) throws IOException, ParseException {
        EstimateEntity estimateEntity = estimateRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Estimate not found"));
        estimateEntity.setOwnerCompany(ownerCompanyService.getOwnerCompany());
        return estimatePDFService.generateEstimatePDF(estimateEntity);
    }
}
