package fr.codesbuster.solidstock.api.entity.pdf;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VATData {
    private String vatNumber;
    private double vatRate;
    private String totalVAT;
}
