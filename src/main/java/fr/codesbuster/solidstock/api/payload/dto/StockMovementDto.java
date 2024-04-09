package fr.codesbuster.solidstock.api.payload.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementDto {

    private long id;

    private String type;

    private double quantity;

    private String expiredDate;

    private String batchNumber;

    private String note;

    private long productId;

    private long locationId;

}
