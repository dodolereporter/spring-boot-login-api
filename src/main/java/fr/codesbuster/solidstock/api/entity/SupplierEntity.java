package fr.codesbuster.solidstock.api.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supplier")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

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

    @Nullable
    private String fax;

    @Nullable
    private String website;

    @Nullable
    private String country;

    @Nullable
    private String siren;

    @Nullable
    private String siret;

    @Nullable
    private String rib;

    private int rcs;

    @Nullable
    private String note;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @OneToMany(mappedBy = "supplier")
    @JsonIdentityReference(alwaysAsId = true)
    private List<ProductEntity> products;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}