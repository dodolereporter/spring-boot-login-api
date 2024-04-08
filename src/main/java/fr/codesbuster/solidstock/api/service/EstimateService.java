package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.estimate.EstimateEntity;
import fr.codesbuster.solidstock.api.entity.estimate.EstimateRowEntity;
import fr.codesbuster.solidstock.api.payload.dto.EstimateDto;
import fr.codesbuster.solidstock.api.payload.dto.EstimateRowDto;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface EstimateService {

    EstimateEntity createEstimate(EstimateDto estimateDto);

    EstimateEntity getEstimate(long id);

    List<EstimateEntity> getAllEstimates();

    void deleteEstimate(long id);

    EstimateEntity updateEstimate(long id, EstimateDto estimateDto);

    void addRow(long id, EstimateRowDto estimateRowDto);

    EstimateRowEntity getRow(long id);

    List<EstimateRowEntity> getAllRows(long id);

    void deleteRow(long id);

    EstimateRowEntity updateRow(long id, EstimateRowDto estimateRowDto);

    File generatePDF(long id) throws IOException, ParseException;
}
