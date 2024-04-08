package fr.codesbuster.solidstock.api.service.impl;

import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.entity.estimate.EstimateEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import fr.codesbuster.solidstock.api.entity.pdf.EstimateData;
import fr.codesbuster.solidstock.api.payload.dto.EstimateDto;
import fr.codesbuster.solidstock.api.repository.CustomerRepository;
import fr.codesbuster.solidstock.api.repository.EstimateRepository;
import fr.codesbuster.solidstock.api.repository.EstimateRowRepository;
import fr.codesbuster.solidstock.api.repository.ProductRepository;
import fr.codesbuster.solidstock.api.service.EstimatePDFService;
import fr.codesbuster.solidstock.api.service.OwnerCompanyService;
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
public class EstimatePDFServiceImpl implements EstimatePDFService {
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public File generateEstimatePDF(EstimateEntity estimateEntity)  throws IOException, ParseException {
        Context context = new Context();
        EstimateData estimateData = new EstimateData(estimateEntity);
        context.setVariable("estimate", estimateData);

        byte[] logo = estimateData.getOwnerCompany().getLogo();

        String logoPath = getTempDirectory() + File.separator + "SolidStock" + File.separator + "Estimates" + File.separator + "static";

        File logoDir = new File(logoPath);
        if (!logoDir.exists()) {
            logoDir.mkdirs();
        }
        logoPath += File.separator + "logo.png";

        try (FileOutputStream fos = new FileOutputStream(logoPath)) {
            fos.write(logo);
        }

        logoPath = "file:///" + logoPath.replace("\\", "/");
        context.setVariable("estimateLogo", logoPath);

        String filePath = getFilePath(estimateEntity);
        OutputStream outputStream = new FileOutputStream(filePath);
        String html = templateEngine.process("estimate_template", context);
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

    private String getFilePath(EstimateEntity estimateEntity) {
        String outputFolder = getTempDirectory() + File.separator + "SolidStock" + File.separator + "Estimates";

        File folder = new File(outputFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return outputFolder + File.separator + "estimate_" + estimateEntity.getId() + ".pdf";
    }

    @Override
    public File getEstimatePDF(EstimateEntity estimateEntity) throws IOException, ParseException {
        String filePath = getFilePath(estimateEntity);
        File estimateFile = new File(filePath);
        if (estimateFile.exists()) {
            return estimateFile;
        }
        return generateEstimatePDF(estimateEntity);
    }
}
