package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormEntity;
import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormRowEntity;
import fr.codesbuster.solidstock.api.payload.dto.OrdersRowDto;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface OrdersService {
    OrderFormEntity createOrder(OrderFormEntity orderFormEntity);

    OrderFormEntity getOrder(Long id);

    List<OrderFormEntity> getAllOrders();

    void deleteOrder(Long id);

    OrderFormEntity updateOrder(OrderFormEntity orderFormEntity);

    void addRow(long id, OrdersRowDto orderRowDto);

    OrderFormRowEntity getRow(long id);

    List<OrderFormRowEntity> getAllRows(long orderId);

    void deleteRow(long id);

    OrderFormRowEntity updateRow(long id, OrdersRowDto ordersRowDto);

    File generatePDF(long id) throws IOException, ParseException;
}