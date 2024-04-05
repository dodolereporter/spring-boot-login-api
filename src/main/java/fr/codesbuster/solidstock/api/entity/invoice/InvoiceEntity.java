package fr.codesbuster.solidstock.api.entity.invoice;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.time.DateTime;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIdentityReference(alwaysAsId = true)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceRowEntity> invoiceRows;

    @Transient
    private double totalHt;

    @Transient
    private double totalTtc;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public void calculateTotal() {
        totalHt = 0;
        totalTtc = 0;
        for (InvoiceRowEntity invoiceRow : invoiceRows) {
            totalHt += invoiceRow.getSellPrice() * invoiceRow.getQuantity();
            totalTtc += invoiceRow.getSellPrice() * invoiceRow.getQuantity() * (1 + invoiceRow.getProduct().getVat().getRate() / 100);
        }
    }

    @ManyToOne
    @JoinColumn(name = "owner_company_id")
    private OwnerCompanyEntity ownerCompany;
}
