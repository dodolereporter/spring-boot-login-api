package fr.codesbuster.solidstock.api.entity.pdf;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerCompanyData {
    private String name;
    private String streetNumber;
    private String address;
    private String city;
    private String postalCode;
    private String country;
}
