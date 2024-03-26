package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.entity.ProductEntity;
import fr.codesbuster.solidstock.api.entity.QuantityTypeEntity;
import fr.codesbuster.solidstock.api.entity.VATEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceRowEntity;
import fr.codesbuster.solidstock.api.entity.pdf.InvoiceData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
public class InvoiceServiceTest {


    @Autowired
    private InvoiceService invoiceService;

    @Test
    public void testGenerateInvoice() throws IOException, ParseException {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setId(999999999);
        invoiceEntity.setName("Invoice 1");
        invoiceEntity.setDescription("Invoice 1 description");
        invoiceEntity.setCreatedAt(new Date().toInstant());

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCompanyName("Company 1");
        customerEntity.setStreetNumber("1");
        customerEntity.setAddress("rue de la Paix");
        customerEntity.setCity("Chambéry");
        customerEntity.setCountry("France");
        customerEntity.setZipCode("73000");

        QuantityTypeEntity quantityTypeEntity1 = new QuantityTypeEntity();
        quantityTypeEntity1.setName("Piece");
        quantityTypeEntity1.setUnit("pc");

        QuantityTypeEntity quantityTypeEntity2 = new QuantityTypeEntity();
        quantityTypeEntity2.setName("Kilogram");
        quantityTypeEntity2.setUnit("kg");

        VATEntity vatEntity1 = new VATEntity();
        vatEntity1.setRate(0.2);
        vatEntity1.setPercentage("20%");

        VATEntity vatEntity2 = new VATEntity();
        vatEntity2.setRate(0.05);
        vatEntity2.setPercentage("5.5%");




        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setName("Vin rouge de Bordeaux");
        productEntity1.setSellPrice(10);
        productEntity1.setQuantityType(quantityTypeEntity1);
        productEntity1.setVat(vatEntity1);

        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setName("Pommes");
        productEntity2.setSellPrice(20);
        productEntity2.setQuantityType(quantityTypeEntity2);
        productEntity2.setVat(vatEntity2);

        ProductEntity productEntity3 = new ProductEntity();
        productEntity3.setName("Jambon blanc");
        productEntity3.setSellPrice(25.50);
        productEntity3.setQuantityType(quantityTypeEntity2);
        productEntity3.setVat(vatEntity1);




        InvoiceRowEntity invoiceRowEntity1 = new InvoiceRowEntity();
        invoiceRowEntity1.setQuantity(2);
        invoiceRowEntity1.setSellPrice(10);
        invoiceRowEntity1.setProduct(productEntity1);
        invoiceRowEntity1.setInvoice(invoiceEntity);

        InvoiceRowEntity invoiceRowEntity2 = new InvoiceRowEntity();
        invoiceRowEntity2.setQuantity(1);
        invoiceRowEntity2.setSellPrice(20);
        invoiceRowEntity2.setProduct(productEntity2);
        invoiceRowEntity2.setInvoice(invoiceEntity);

        InvoiceRowEntity invoiceRowEntity3 = new InvoiceRowEntity();
        invoiceRowEntity3.setQuantity(4.2);
        invoiceRowEntity3.setSellPrice(25.50);
        invoiceRowEntity3.setProduct(productEntity3);
        invoiceRowEntity3.setInvoice(invoiceEntity);


        List<InvoiceRowEntity> invoiceRowEntities = List.of(invoiceRowEntity1, invoiceRowEntity2,invoiceRowEntity3);

        invoiceEntity.setInvoiceRows(invoiceRowEntities);
        invoiceEntity.setCustomer(customerEntity);

        InvoiceData invoiceData = new InvoiceData(invoiceEntity);

        log.info(invoiceData.toString());


        try {
            assertTrue(invoiceService.generateInvoicePDF(invoiceEntity).exists());
        } finally {
            if (new File(System.getProperty("user.home") + "/AppData/Local/Temp/SolidStock/Invoices/invoice_" + invoiceEntity.getId() +".pdf").exists()) {
                new File(System.getProperty("user.home") + "/AppData/Local/Temp/SolidStock/Invoices/invoice_" + invoiceEntity.getId() +".pdf").delete();
            }
        }
    }

    @Test
    public void testGetInvoice() throws IOException, ParseException {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setId(999999999);
        invoiceEntity.setName("Invoice 1");
        invoiceEntity.setDescription("Invoice 1 description");
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCompanyName("Company 1");
        invoiceEntity.setCustomer(customerEntity);

        try {
            if (!new File(System.getProperty("user.home") + "/AppData/Local/Temp/SolidStock/Invoices/invoice_" + invoiceEntity.getId() +".pdf").exists()) {
                assertTrue(invoiceService.getInvoicePDF(invoiceEntity).exists());
            }
        }finally {
            if (new File(System.getProperty("user.home") + "/AppData/Local/Temp/SolidStock/Invoices/invoice_" + invoiceEntity.getId() +".pdf").exists()) {
                new File(System.getProperty("user.home") + "/AppData/Local/Temp/SolidStock/Invoices/invoice_" + invoiceEntity.getId() +".pdf").delete();
            }
        }

    }
}
