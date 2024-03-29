package fr.codesbuster.solidstock.api.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fr.codesbuster.solidstock.api.entity.delivery.DeliveryRowEntity;
import fr.codesbuster.solidstock.api.entity.estimate.EstimateRowEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceRowEntity;
import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormRowEntity;
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
@Table(name = "product")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String barCode;
    private double buyPrice;
    private double sellPrice;
    private double minimumStockQuantity;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;

    @ManyToOne
    @JoinColumn(name = "vat_id")
    private  VATEntity vat;

    @ManyToOne
    @JoinColumn(name = "quantity_type_id")
    private QuantityTypeEntity quantityType;

    @ManyToOne
    @JoinColumn(name = "product_family_id")
    private ProductFamilyEntity productFamily;

    @OneToMany(mappedBy = "product")
    private List<StockMovementEntity> stockMovements;

    @OneToMany(mappedBy = "product")
    private List<StockEntity> stocks;

    @OneToMany(mappedBy = "product")
    private List<EstimateRowEntity> estimateRows;

    @OneToMany(mappedBy = "product")
    private List<InvoiceRowEntity> invoiceRows;

    @OneToMany(mappedBy = "product")
    private List<OrderFormRowEntity> orderFormRows;

    @OneToMany(mappedBy = "product")
    private List<DeliveryRowEntity> deliveryRows;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}