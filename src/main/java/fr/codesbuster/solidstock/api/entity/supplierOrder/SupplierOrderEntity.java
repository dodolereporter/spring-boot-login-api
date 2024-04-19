package fr.codesbuster.solidstock.api.entity.supplierOrder;

import fr.codesbuster.solidstock.api.entity.SupplierEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supplier_order")
public class SupplierOrderEntity {
    @Id
    private long id;
    private String orderNumber;
    private String orderDate;
    private Date deliveryDate;
    private SupplierOrderStatus status;
    private String note;

    @OneToMany(mappedBy = "supplierOrder")
    private List<SupplierOrderRowEntity> supplierOrderRows;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;

}
