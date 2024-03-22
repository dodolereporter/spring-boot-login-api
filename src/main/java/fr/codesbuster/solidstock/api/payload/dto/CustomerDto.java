package fr.codesbuster.solidstock.api.payload.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private String companyName;
    private String firstName;
    private String lastName;
    private String city;
    private String zipCode;
    private String address;
    private String streetNumber;
    private String email;
    private String mobilePhone;
    private String homePhone;
    private String workPhone;
    private String website;
    private String country;
    private String siret;
    private String siren;
    private String rib;
    private int rcs;
    private String fax;
}
