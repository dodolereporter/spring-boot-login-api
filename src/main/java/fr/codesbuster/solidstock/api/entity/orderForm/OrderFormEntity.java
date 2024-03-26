package fr.codesbuster.solidstock.api.entity.orderForm;

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
@Table(name = "orderForm")
public class OrderFormEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    private DateTime estimateDate;

    private double total;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIdentityReference(alwaysAsId = true)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "orderForm")
    private List<OrderFormRowEntity> orderFormRows;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
