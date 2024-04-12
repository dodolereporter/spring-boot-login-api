package fr.codesbuster.solidstock.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.codesbuster.solidstock.api.entity.estimate.EstimateEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormEntity;
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
    private String website;

    @Nullable
    private String country;

    @Nullable
    private String siren;

    @Nullable
    private String siret;

    @Nullable
    private String rib;

    @Nullable
    private int rcs;

    @Nullable
    private String fax;

    @Nullable
    private String note;

    @Column(columnDefinition = "boolean default false")
    private boolean isDisabled;

    @OneToMany(mappedBy = "customer")
    private List<EstimateEntity> estimates;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<InvoiceEntity> invoices;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<OrderFormEntity> orderForms;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}