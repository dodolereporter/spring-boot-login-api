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
            String logoPath = getTempDirectory() + File.separator + "SolidStock" + File.separator + "Invoices" + File.separator + "static";
            File logoDir = new File(logoPath);
            if (!logoDir.exists()) {
                logoDir.mkdirs();
            }
            logoPath += File.separator + "logo.png";
            copyFile(logo, new File(logoPath));

            logoPath = "file:///" + logoPath.replace("\\", "/");
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

    private String getTempDirectory() {
        return System.getProperty("java.io.tmpdir");
    }

    private void copyFile(File source, File dest) throws IOException {
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }

    private String getFilePath(InvoiceEntity invoiceEntity) {
        String outputFolder = getTempDirectory() + File.separator + "SolidStock" + File.separator + "Invoices";

        File folder = new File(outputFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return outputFolder + File.separator + "invoice_" + invoiceEntity.getId() + ".pdf";
    }

    @Override
    public File getInvoicePDF(InvoiceEntity invoiceEntity) throws IOException, ParseException {
        String filePath = getFilePath(invoiceEntity);
        File invoiceFile = new File(filePath);
        if (invoiceFile.exists()) {
            return invoiceFile;
        }
        return generateInvoicePDF(invoiceEntity);
    }
}
