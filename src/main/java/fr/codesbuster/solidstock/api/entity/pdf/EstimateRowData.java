package fr.codesbuster.solidstock.api.entity.pdf;

import fr.codesbuster.solidstock.api.entity.estimate.EstimateRowEntity;
import lombok.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstimateRowData {

    private String productName;
    private String quantity;
    private String quantityType;
    private String priceHT;
    private String priceTTC;
    private String totalHT;
    private String totalTTC;
    private double vatRate;
    private String vatAmount;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public EstimateRowData(EstimateRowEntity estimateRowEntity) {
        df.setRoundingMode(RoundingMode.UP);
        this.productName = estimateRowEntity.getProduct().getName();
        this.quantityType = estimateRowEntity.getProduct().getQuantityType().getUnit();
        this.quantity = df.format(estimateRowEntity.getQuantity());
        this.priceHT = df.format(estimateRowEntity.getSellPrice() + " €");
        this.vatRate = estimateRowEntity.getProduct().getVat().getRate();
        this.vatAmount = estimateRowEntity.getProduct().getVat().getPercentage();
        this.priceTTC = df.format(estimateRowEntity.getSellPrice() * (1 + estimateRowEntity.getProduct().getVat().getRate())) + " €";
        this.totalHT = df.format(estimateRowEntity.getSellPrice() * estimateRowEntity.getQuantity()) + " €";
        this.totalTTC = df.format(estimateRowEntity.getSellPrice() * estimateRowEntity.getQuantity() * (1 + (estimateRowEntity.getProduct().getVat().getRate() / 100))) + " €";
    }
}
