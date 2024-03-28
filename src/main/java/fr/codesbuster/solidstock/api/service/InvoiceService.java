package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceRowEntity;
import fr.codesbuster.solidstock.api.payload.dto.InvoiceDto;
import fr.codesbuster.solidstock.api.payload.dto.InvoiceRowDto;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface InvoiceService {
    void createInvoice(InvoiceDto invoiceDto);

    InvoiceEntity getInvoice(long id);

    List<InvoiceEntity> getAllInvoices();

    void deleteInvoice(long id);

    InvoiceEntity updateInvoice(long id, InvoiceDto invoiceDto);

    void addRow(long id, InvoiceRowDto invoiceRowDto);

    InvoiceRowEntity getRow(long id);

    List<InvoiceRowEntity> getAllRows(long invoiceId);

    void deleteRow(long id);

    InvoiceRowEntity updateRow(long id, InvoiceRowDto invoiceRowDto);

    File generatePDF(long id) throws IOException, ParseException;
}
