package fr.codesbuster.solidstock.api.service.impl;

import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormEntity;
import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormRowEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.payload.dto.OrdersRowDto;
import fr.codesbuster.solidstock.api.repository.CustomerRepository;
import fr.codesbuster.solidstock.api.repository.OrdersRepository;
import fr.codesbuster.solidstock.api.repository.OrdersRowRepository;
import fr.codesbuster.solidstock.api.repository.ProductRepository;
import fr.codesbuster.solidstock.api.service.OrderPDFService;
import fr.codesbuster.solidstock.api.service.OrdersService;
import fr.codesbuster.solidstock.api.service.OwnerCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrdersRowRepository orderRowRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderPDFService orderPDFService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OwnerCompanyService ownerCompanyService;

    @Override
    public OrderFormEntity createOrder(OrderFormEntity orderFormEntity) {
        CustomerEntity customerEntity = customerRepository.findById(orderFormEntity.getCustomer().getId()).orElse(null);
        if (customerEntity == null) {
            throw new APIException(HttpStatus.NOT_FOUND, "Customer not found");
        }

        return ordersRepository.save(orderFormEntity);
    }

    @Override
    public OrderFormEntity getOrder(Long id) {
        if (id == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Order is cannot be null");
        }

        Optional<OrderFormEntity> orderFormEntity = ordersRepository.findById(id);

        if (orderFormEntity.isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "Order does not exist");
        }

        return orderFormEntity.get();
    }

    @Override
    public List<OrderFormEntity> getAllOrders() {
        return ordersRepository.findAll();
    }

    @Override
    public void deleteOrder(Long id) {
        if (id == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Order id cannot be null");
        }

        if (!ordersRepository.existsById(id)) {
            throw new ResponseStatusException(BAD_REQUEST, "Order does not exist");
        }

        ordersRepository.deleteById(id);
    }

    @Override
    public OrderFormEntity updateOrder(OrderFormEntity orderFormEntity) {
        if (orderFormEntity == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Order cannot be null");
        }

        if (!ordersRepository.existsById(orderFormEntity.getId())) {
            throw new ResponseStatusException(BAD_REQUEST, "Order does not exist");
        }

        return ordersRepository.save(orderFormEntity);
    }

    @Override
    public void addRow(long id, OrdersRowDto ordersRowDto) {
        OrderFormRowEntity orderFormRowEntity = new OrderFormRowEntity();
        orderFormRowEntity.setQuantity(ordersRowDto.getQuantity());
        OrderFormEntity orderFormEntity = ordersRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Order not found"));
        orderFormRowEntity.setOrder(orderFormEntity);
        orderFormRowEntity.setProduct(productRepository.findById(ordersRowDto.getProductId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Product not found")));

        orderRowRepository.save(orderFormRowEntity);
    }

    @Override
    public OrderFormRowEntity getRow(long id) {
        return orderRowRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "OrderRow not found"));
    }

    @Override
    public List<OrderFormRowEntity> getAllRows(long orderId) {
        return orderRowRepository.findByOrder_Id(orderId);
    }

    @Override
    public void deleteRow(long id) {
        orderRowRepository.deleteById(id);
    }

    @Override
    public OrderFormRowEntity updateRow(long id, OrdersRowDto ordersRowDto) {
        OrderFormRowEntity orderFormRowEntity = orderRowRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "InvoiceRow not found"));
        orderFormRowEntity.setQuantity(ordersRowDto.getQuantity());
        orderFormRowEntity.setProduct(productRepository.findById(ordersRowDto.getProductId()).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Product not found")));

        orderRowRepository.save(orderFormRowEntity);
        return orderFormRowEntity;
    }

    @Override
    public File generatePDF(long id) throws IOException, ParseException {
        OrderFormEntity orderFormEntity = ordersRepository.findById(id).orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Invoice not found"));
        orderFormEntity.setOwnerCompany(ownerCompanyService.getOwnerCompany());
        return orderPDFService.generateOrderPDF(orderFormEntity);
    }
}