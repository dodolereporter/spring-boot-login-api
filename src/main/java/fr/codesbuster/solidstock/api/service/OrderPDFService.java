package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormEntity;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public interface OrderPDFService {
    
    File generateOrderPDF(OrderFormEntity orderFormEntity) throws IOException, ParseException;
}