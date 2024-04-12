package fr.codesbuster.solidstock.api.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto {
    private String name;
    private String description;
    private String estimateDate;
    private String status;
    private long customerId;
}