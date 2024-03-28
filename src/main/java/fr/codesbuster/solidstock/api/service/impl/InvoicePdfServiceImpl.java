package fr.codesbuster.solidstock.api.service.impl;

import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import fr.codesbuster.solidstock.api.entity.pdf.InvoiceData;
import fr.codesbuster.solidstock.api.service.InvoicePDFService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.text.ParseException;

@Slf4j
@Service
public class InvoicePdfServiceImpl implements InvoicePDFService {

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public File generateInvoicePDF(InvoiceEntity invoiceEntity) throws IOException, ParseException {
        Context context = new Context();
        InvoiceData invoiceData = new InvoiceData(invoiceEntity);
        context.setVariable("invoice", invoiceData);

        File logo = invoiceData.getOwnerCompany().getLogo();
        if (logo != null) {
            String logoPath = System.getProperty("user.home").replace("\\", "/") + "/AppData/Local/Temp/SolidStock/Invoices/static/";
            if (!new File(logoPath).exists()) {
                new File(logoPath).mkdirs();
            }
            logoPath += "logo.png";
            InputStream in = new FileInputStream(logo);
            OutputStream out = new FileOutputStream(logoPath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();

            logoPath = "file:///" + logoPath;

            context.setVariable("invoiceLogo", logoPath);
        }


        String filePath = getFilePath(invoiceEntity);
        OutputStream outputStream = new FileOutputStream(filePath);
        String html = templateEngine.process("invoice_template", context);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();

        return new File(filePath);

    }

    private String getFilePath(InvoiceEntity invoiceEntity) {
        String outputFolder = System.getProperty("user.home") + "/AppData/Local/Temp/SolidStock/Invoices/";

        if (!new File(outputFolder).exists()) {
            new File(outputFolder).mkdirs();
        }

        String filePath = outputFolder + "invoice_" + invoiceEntity.getId() + ".pdf";

        return filePath;
    }


    //get Invoice
    @Override
    public File getInvoicePDF(InvoiceEntity invoiceEntity) throws IOException, ParseException {
        String filePath = getFilePath(invoiceEntity);
        if (new File(filePath).exists()) {
            return new File(filePath);
        }
        return generateInvoicePDF(invoiceEntity);
    }

}
