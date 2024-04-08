package fr.codesbuster.solidstock.api.entity.pdf;

import fr.codesbuster.solidstock.api.entity.estimate.EstimateEntity;
import lombok.*;
import org.xhtmlrenderer.newtable.RowData;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstimateData {
    private int estimateNumber;
    private Date estimateDate;
    private String description;
    private CustomerCompanyData customerCompany;
    private OwnerCompanyData ownerCompany;
    private EstimateRowData[] estimateRows;
    private String totalHT;
    private String totalTTC;
    private VATData[] vat;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public EstimateData(EstimateEntity estimateEntity) {
        df.setRoundingMode(RoundingMode.UP);

        this.estimateNumber = (int) estimateEntity.getId();
        this.description = estimateEntity.getDescription();
        this.estimateDate = Date.from(estimateEntity.getCreatedAt());
        String companyName = "";
        if (estimateEntity.getCustomer().getCompanyName() != null) {
            companyName = estimateEntity.getCustomer().getCompanyName();
        } else {
            companyName = estimateEntity.getCustomer().getFirstName() + " " + estimateEntity.getCustomer().getLastName();
        }
        this.customerCompany = new CustomerCompanyData(companyName, estimateEntity.getCustomer().getStreetNumber(), estimateEntity.getCustomer().getAddress(), estimateEntity.getCustomer().getCity(), estimateEntity.getCustomer().getZipCode(), estimateEntity.getCustomer().getCountry());
        this.ownerCompany = new OwnerCompanyData(estimateEntity.getOwnerCompany().getCompanyName(), estimateEntity.getOwnerCompany().getStreetNumber(), estimateEntity.getOwnerCompany().getStreetName(), estimateEntity.getOwnerCompany().getCity(), estimateEntity.getOwnerCompany().getZipCode(), estimateEntity.getOwnerCompany().getCountry(), estimateEntity.getOwnerCompany().getPhone(), estimateEntity.getOwnerCompany().getEmail(), "www.test.com", estimateEntity.getOwnerCompany().getSiret(), estimateEntity.getOwnerCompany().getImage());
        this.estimateRows = new EstimateRowData[estimateEntity.getEstimateRows().size()];
        for (int i = 0; i < estimateEntity.getEstimateRows().size(); i++) {
            this.estimateRows[i] = new EstimateRowData(estimateEntity.getEstimateRows().get(i));
        }

        double totalHT = 0;
        HashMap<Double, Double> vatTotals = new HashMap<>();
        for (EstimateRowData rowData : this.estimateRows) {
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
