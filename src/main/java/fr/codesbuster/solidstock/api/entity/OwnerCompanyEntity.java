package fr.codesbuster.solidstock.api.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ownerCompany")
public class OwnerCompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Nullable
    private String companyName;

    @Nullable
    private String ownerName;

    @Nullable
    private String siret;

    @Nullable
    private String siren;

    @Nullable
    private int rcs;

    @Nullable
    private String streetNumber;
    @Nullable
    private String streetName;
    @Nullable
    private String zipCode;
    @Nullable
    private String city;
    @Nullable
    private String country;
    @Nullable
    private String email;
    @Nullable
    private String phone;
    @Nullable
    private String iban;
    @Lob
    private byte[] image;
}
