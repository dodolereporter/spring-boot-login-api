package fr.codesbuster.solidstock.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class ProductsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    private String barCode;
    @Column(nullable = false)
    private Long productFamilyId;
    @Column(nullable = false)
    private Long quantityTypeId;
    @Column(nullable = false)
    private Long supplierId;
    @Column(nullable = false)
    private float sellPrice;
    @Column(nullable = false)
    private float byPrice;
    @Column(nullable = false)
    private Long vatId;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "products_productFamily",
            joinColumns = @JoinColumn(name = "products_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "productFamily_id", referencedColumnName = "productFamilyId")
    )
    private Set<ProductFamilyEntity> productFamily;
}
