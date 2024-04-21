package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormRowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRowRepository extends JpaRepository<OrderFormRowEntity, Long> {
    List<OrderFormRowEntity> findByOrder_Id(long id);
}