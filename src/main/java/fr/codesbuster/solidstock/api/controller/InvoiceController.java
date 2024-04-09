package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceRowEntity;
import fr.codesbuster.solidstock.api.payload.dto.InvoiceDto;
import fr.codesbuster.solidstock.api.payload.dto.InvoiceRowDto;
import fr.codesbuster.solidstock.api.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.util.FileUtil;
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
@RequestMapping("/api/v1/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/add")
    public void createInvoice(@RequestBody InvoiceDto invoiceDto) {
        log.info("Creating invoice");
        invoiceService.createInvoice(invoiceDto);
    }

    @GetMapping("/{id}")
    public InvoiceEntity getInvoice(@PathVariable long id) {
        InvoiceEntity invoice = invoiceService.getInvoice(id);
        invoice.calculateTotal();
        return invoice;
    }

    @GetMapping("/all")
    public List<InvoiceEntity> getAllInvoices() {
        List<InvoiceEntity> invoices = invoiceService.getAllInvoices();
        invoices.forEach(InvoiceEntity::calculateTotal);
        Collections.reverse(invoices);
       return invoices;
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable long id) {
        invoiceService.deleteInvoice(id);
    }

    @PutMapping("/{id}")
    public void updateInvoice(@PathVariable long id, @RequestBody InvoiceDto invoiceDto) {
        invoiceService.updateInvoice(id, invoiceDto);
    }

    @PostMapping("/{id}/row/add")
    public void addRow(@PathVariable long id, @RequestBody InvoiceRowDto invoiceRowDto) {
        invoiceService.addRow(id, invoiceRowDto);
    }

    @GetMapping("/{id}/row/{rowId}")
    public InvoiceRowEntity getRow(@PathVariable long id, @PathVariable long rowId) {
        return invoiceService.getRow(rowId);
    }

    @GetMapping("/{id}/row/all")
    public List<InvoiceRowEntity> getAllRows(@PathVariable long id) {
        return invoiceService.getAllRows(id);
    }

    @DeleteMapping("/{id}/row/{rowId}")
    public void deleteRow(@PathVariable long id, @PathVariable long rowId) {
        invoiceService.deleteRow(rowId);
    }

    @PutMapping("/{id}/row/{rowId}")
    public void updateRow(@PathVariable long id, @PathVariable long rowId, @RequestBody InvoiceRowDto invoiceRowDto) {
        invoiceService.updateRow(rowId, invoiceRowDto);
    }


    //when get download pdf
    @GetMapping("/{id}/pdf")
    public ResponseEntity<Resource> generatePDF(@PathVariable long id) throws IOException, ParseException {
        Path path = invoiceService.generatePDF(id).toPath();
        Resource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", path.getFileName().toString());
        headers.setContentType(MediaType.APPLICATION_PDF);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);

    }





}
