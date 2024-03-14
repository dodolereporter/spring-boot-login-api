package fr.codesbuster.solidstock.api.entity.estimate;


import fr.codesbuster.solidstock.api.entity.ProductEntity;
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
@Table(name = "estimate_row")
public class EstimateRowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int quantity;
    private double sellPrice;

    @ManyToOne
    @JoinColumn(name = "estimate_id")
    private EstimateEntity estimate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
