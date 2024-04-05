package fr.codesbuster.solidstock.api.entity.pdf;

import lombok.*;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OwnerCompanyData {
    private String name;
    private String streetNumber;
    private String address;
    private String city;
    private String postalCode;
    private String country;
    private String phone;
    private String email;
    private String website;
    private String siret;
    private byte[] logo;
}
