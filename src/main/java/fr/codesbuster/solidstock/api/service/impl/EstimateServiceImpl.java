package fr.codesbuster.solidstock.api.service.impl;

import fr.codesbuster.solidstock.api.repository.CustomerRepository;
import fr.codesbuster.solidstock.api.repository.EstimateRepository;
import fr.codesbuster.solidstock.api.repository.EstimateRowRepository;
import fr.codesbuster.solidstock.api.repository.ProductRepository;
import fr.codesbuster.solidstock.api.service.EstimateService;
import fr.codesbuster.solidstock.api.service.OwnerCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
