package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<OrderFormEntity, Long> {
}