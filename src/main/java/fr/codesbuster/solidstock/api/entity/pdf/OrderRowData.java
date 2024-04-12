package fr.codesbuster.solidstock.api.entity.pdf;

import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormRowEntity;
import lombok.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderRowData {


    private static final DecimalFormat df = new DecimalFormat("0.00");
    private String productName;
    private String quantity;
    private String quantityType;

    public OrderRowData(OrderFormRowEntity orderFormRowEntity) {
        df.setRoundingMode(RoundingMode.UP);
        this.productName = orderFormRowEntity.getProduct().getName();
        this.quantityType = orderFormRowEntity.getProduct().getQuantityType().getUnit();
        this.quantity = df.format(orderFormRowEntity.getQuantity());
    }
}