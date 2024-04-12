package fr.codesbuster.solidstock.api.entity.pdf;

import fr.codesbuster.solidstock.api.entity.invoice.InvoiceRowEntity;
import lombok.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InvoiceRowData {



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

    public InvoiceRowData(InvoiceRowEntity invoiceRowEntity) {
        df.setRoundingMode(RoundingMode.UP);
        this.productName = invoiceRowEntity.getProduct().getName();
        this.quantityType = invoiceRowEntity.getProduct().getQuantityType().getUnit();
        this.quantity = df.format(invoiceRowEntity.getQuantity());
        this.priceHT = df.format(invoiceRowEntity.getSellPrice()) + " €";
        this.vatRate = invoiceRowEntity.getProduct().getVat().getRate();
        this.vatAmount = invoiceRowEntity.getProduct().getVat().getPercentage();
        this.priceTTC = df.format(invoiceRowEntity.getSellPrice() * (1 + invoiceRowEntity.getProduct().getVat().getRate()))  + " €";
        this.totalHT =df.format(invoiceRowEntity.getSellPrice() * invoiceRowEntity.getQuantity())  + " €";
        this.totalTTC = df.format(invoiceRowEntity.getSellPrice() * invoiceRowEntity.getQuantity() * (1 + (invoiceRowEntity.getProduct().getVat().getRate() /100)))  + " €";

    }
}
