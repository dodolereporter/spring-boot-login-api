package fr.codesbuster.solidstock.api.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "third_party")
public class ThirdPartyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Nullable
    private String companyName;

    @Nullable
    private String firstName;

    @Nullable
    private String lastName;

    @Nullable
    private String city;

    @Nullable
    private String zipCode;

    @Nullable
    private String address;

    @Nullable
    private String streetNumber;

    @Nullable
    private String email;

    @Nullable
    private String mobilePhone;

    @Nullable
    private String homePhone;

    @Nullable
    private String workPhone;
}