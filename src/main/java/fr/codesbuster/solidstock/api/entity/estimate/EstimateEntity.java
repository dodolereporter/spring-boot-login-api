package fr.codesbuster.solidstock.api.entity.estimate;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.entity.OwnerCompanyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estimate")
public class EstimateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIdentityReference(alwaysAsId = true)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "estimate", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstimateRowEntity> estimateRows;

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
        for (EstimateRowEntity estimateRow : estimateRows) {
            totalHt += estimateRow.getSellPrice() * estimateRow.getQuantity();
            totalTtc += estimateRow.getSellPrice() * estimateRow.getQuantity() * (1 + estimateRow.getProduct().getVat().getRate() / 100);
        }
    }

    @ManyToOne
    @JoinColumn(name = "owner_company_id")
    private OwnerCompanyEntity ownerCompany;
}
