package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public interface InvoicePDFService {


    File generateInvoicePDF(InvoiceEntity invoiceEntity) throws IOException, ParseException;

    //get Invoice
    File getInvoicePDF(InvoiceEntity invoiceEntity) throws IOException, ParseException;
}

