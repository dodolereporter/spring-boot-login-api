package fr.codesbuster.solidstock.api.entity.pdf;

import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import lombok.*;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

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

        double totalHT = 0;
        HashMap<Double, Double> vatTotals = new HashMap<>();
        for (InvoiceRowData rowData : this.invoiceRows) {
            totalHT += Double.parseDouble(rowData.getPriceHT().replace(",", ".").replace("€", "")) * Double.parseDouble(rowData.getQuantity().replace(",", "."));
            double vatAmount = (Double.parseDouble(rowData.getPriceHT().replace(",", ".").replace("€", "")) * Double.parseDouble(rowData.getQuantity().replace(",", "."))) * (rowData.getVatRate() / 100);
            vatTotals.put(rowData.getVatRate(), vatTotals.getOrDefault(rowData.getVatRate(), 0.0) + vatAmount);
        }
        this.totalHT = df.format(totalHT) + " €";
        double totalTTC = totalHT;
        for (Double vatTotal : vatTotals.values()) {
            totalTTC += vatTotal;
        }
        this.totalTTC = df.format(totalTTC) + " €";

        List<VATData> vatDataList = new ArrayList<>();
        for (Map.Entry<Double, Double> entry : vatTotals.entrySet()) {
            VATData vatData = new VATData();
            vatData.setVatNumber(entry.getKey().toString() + "%");
            vatData.setVatRate(entry.getKey());
            vatData.setTotalVAT(df.format(entry.getValue()) + " €");
            vatDataList.add(vatData);
        }
        this.vat = vatDataList.toArray(new VATData[0]);




    }

}
