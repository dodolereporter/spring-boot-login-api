package fr.codesbuster.solidstock.api.service.impl;

import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormEntity;
import fr.codesbuster.solidstock.api.entity.pdf.OrderData;
import fr.codesbuster.solidstock.api.service.OrderPDFService;
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
public class OrderPdfServiceImpl implements OrderPDFService {

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public File generateOrderPDF(OrderFormEntity orderFormEntity) throws IOException, ParseException {
        Context context = new Context();
        OrderData orderData = new OrderData(orderFormEntity);
        context.setVariable("order", orderData);

        byte[] logo = orderData.getOwnerCompany().getLogo();

        String logoPath = getTempDirectory() + File.separator + "SolidStock" + File.separator + "Orders" + File.separator + "static";
        File logoDir = new File(logoPath);
        if (!logoDir.exists()) {
            logoDir.mkdirs();
        }
        logoPath += File.separator + "logo.png";

        try (FileOutputStream fos = new FileOutputStream(logoPath)) {
            fos.write(logo);
        }

        logoPath = "file:///" + logoPath.replace("\\", "/");
        context.setVariable("orderLogo", logoPath);


        String filePath = getFilePath(orderFormEntity);
        OutputStream outputStream = new FileOutputStream(filePath);
        String html = templateEngine.process("order_template", context);
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

    private String getFilePath(OrderFormEntity orderFormEntity) {
        String outputFolder = getTempDirectory() + File.separator + "SolidStock" + File.separator + "Orders";

        File folder = new File(outputFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return outputFolder + File.separator + "order_" + orderFormEntity.getId() + ".pdf";
    }
}