package fr.codesbuster.solidstock.api.payload.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OwnerCompanyDto {

    private String companyName;
    private String ownerName;
    private String siret;
    private String siren;
    private int rcs;
    private String streetNumber;
    private String streetName;
    private String zipCode;
    private String city;
    private String country;
    private String email;
    private String phone;
    private String iban;
}
