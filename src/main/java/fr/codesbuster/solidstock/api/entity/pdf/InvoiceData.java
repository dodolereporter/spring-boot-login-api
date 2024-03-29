package fr.codesbuster.solidstock.api.entity.pdf;

import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import lombok.*;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InvoiceData {
    private int invoiceNumber;
    private Date invoiceDate;
    private String description;
    private CustomerCompanyData customerCompany;
    private OwnerCompanyData ownerCompany;
    private InvoiceRowData[] invoiceRows;
    private String totalHT;
    private String totalTTC;
    private VATData[] vat;


    private static final DecimalFormat df = new DecimalFormat("0.00");

    public InvoiceData(InvoiceEntity invoiceEntity) throws ParseException {
        df.setRoundingMode(RoundingMode.UP);

        this.invoiceNumber = (int) invoiceEntity.getId();
        this.description = invoiceEntity.getDescription();
        this.invoiceDate = Date.from(invoiceEntity.getCreatedAt());
        String companyName = "";
        if (invoiceEntity.getCustomer().getCompanyName() != null) {
            companyName = invoiceEntity.getCustomer().getCompanyName();
        } else {
            companyName = invoiceEntity.getCustomer().getFirstName() + " " + invoiceEntity.getCustomer().getLastName();
        }
        this.customerCompany = new CustomerCompanyData(companyName, invoiceEntity.getCustomer().getStreetNumber(), invoiceEntity.getCustomer().getAddress(), invoiceEntity.getCustomer().getCity(), invoiceEntity.getCustomer().getZipCode(), invoiceEntity.getCustomer().getCountry());
        this.ownerCompany = new OwnerCompanyData("NegoSud", "1", "rue de la Patrie", "Paris", "75000", "France", "0123456789", "test@test.test", "www.test.com", "12345678912345", new File("src/test/resources/images/negosud.png"));
        this.invoiceRows = new InvoiceRowData[invoiceEntity.getInvoiceRows().size()];
        for (int i = 0; i < invoiceEntity.getInvoiceRows().size(); i++) {
            this.invoiceRows[i] = new InvoiceRowData(invoiceEntity.getInvoiceRows().get(i));
        }

        this.totalHT = df.format(invoiceEntity.getInvoiceRows().stream().mapToDouble(invoiceRowEntity -> invoiceRowEntity.getSellPrice() * invoiceRowEntity.getQuantity()).sum()) + " €";
        this.totalTTC = df.format(invoiceEntity.getInvoiceRows().stream().mapToDouble(invoiceRowEntity -> invoiceRowEntity.getSellPrice() * invoiceRowEntity.getQuantity() * (1 + invoiceRowEntity.getProduct().getVat().getRate())).sum()) + " €";
        HashMap<String, Double> vatMap = new HashMap<>();

        for(InvoiceRowData rowData : this.invoiceRows) {
            if(vatMap.containsKey(rowData.getVatAmount())) {
                vatMap.put(rowData.getVatAmount(), vatMap.get(rowData.getVatAmount()) + ( Double.parseDouble(rowData.getTotalHT().replace(" €", "").replace(",", ".")) * (rowData.getVatRate())));
            } else {
                vatMap.put(rowData.getVatAmount(), Double.parseDouble(rowData.getTotalHT().replace(" €", "").replace(",", ".")) * (rowData.getVatRate()));
            }
        }

        this.vat = new VATData[vatMap.size()];
        int i = 0;
        for(String vatRate : vatMap.keySet()) {
            this.vat[i] = new VATData(vatRate, Double.parseDouble(vatRate.replace("%", "")), df.format(vatMap.get(vatRate)) +" €");
            i++;
        }




    }

}
