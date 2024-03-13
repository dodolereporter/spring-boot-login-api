package fr.codesbuster.solidstock.api.entity;

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
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String barcode;
    private double buyPrice;
    private double sellPrice;
    private double minimumStockQuantity;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;

    @ManyToOne
    @JoinColumn(name = "vat_id")
    private VATEntity vat;

    @ManyToOne
    @JoinColumn(name = "quantity_type_id")
    private QuantityTypeEntity quantityType;

    @ManyToOne
    @JoinColumn(name = "product_family_id")
    private ProductFamilyEntity productFamily;


}
