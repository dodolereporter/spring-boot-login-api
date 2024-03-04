package fr.codesbuster.solidstock.api.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VATDto {

    private double rate;
    private String description;
    private String percentage;

}