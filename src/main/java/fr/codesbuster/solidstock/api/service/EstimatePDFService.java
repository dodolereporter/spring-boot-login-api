package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.estimate.EstimateEntity;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public interface EstimatePDFService {

    File generateEstimatePDF(EstimateEntity estimateEntity) throws IOException, ParseException;

    File getEstimatePDF(EstimateEntity estimateEntity) throws IOException, ParseException;
}
