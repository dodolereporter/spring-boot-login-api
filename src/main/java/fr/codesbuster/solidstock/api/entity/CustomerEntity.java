package fr.codesbuster.solidstock.api.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Nullable
    private String companyName;

    @Nullable
    private String firstName;

    @Nullable
    private String lastName;

    @Nullable
    private String address;

    @Nullable
    private String city;

    @Nullable
    private String zipCode;

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

    @Nullable
    private String webSite;

    @Nullable
    private String country;

    @Nullable
    private Boolean corporation;

    @Nullable
    private String siren;

    @Nullable
    private String siret;

    @Nullable
    private String rib;

    @Nullable
    private Integer rcsInteger;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}