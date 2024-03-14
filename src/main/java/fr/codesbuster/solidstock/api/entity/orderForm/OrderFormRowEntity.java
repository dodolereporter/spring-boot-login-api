package fr.codesbuster.solidstock.api.entity.orderForm;


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
@Table(name = "orderForm_row")
public class OrderFormRowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int quantity;
    private double sellPrice;

    @ManyToOne
    @JoinColumn(name = "orderForm_id")
    private OrderFormEntity orderForm;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
