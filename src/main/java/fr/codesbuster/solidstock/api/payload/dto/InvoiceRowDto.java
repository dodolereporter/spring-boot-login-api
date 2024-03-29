package fr.codesbuster.solidstock.api.payload.dto;

import fr.codesbuster.solidstock.api.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRowDto {
    private double quantity;
    private double sellPrice;
    private long productId;
}
