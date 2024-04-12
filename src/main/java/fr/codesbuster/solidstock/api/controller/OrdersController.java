package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormEntity;
import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormRowEntity;
import fr.codesbuster.solidstock.api.payload.dto.OrdersDto;
import fr.codesbuster.solidstock.api.payload.dto.OrdersRowDto;
import fr.codesbuster.solidstock.api.repository.CustomerRepository;
import fr.codesbuster.solidstock.api.repository.OrdersRepository;
import fr.codesbuster.solidstock.api.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrdersRepository orderFormRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/add")
    public ResponseEntity<OrderFormEntity> addOrder(@RequestBody OrdersDto ordersDto) {
        CustomerEntity customerEntity = customerRepository.findById(ordersDto.getCustomerId()).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "customer cannot be null"));

        OrderFormEntity orderFormEntity = new OrderFormEntity();
        orderFormEntity.setName(ordersDto.getName());
        orderFormEntity.setDescription(ordersDto.getDescription());
        orderFormEntity.setEstimateDate(LocalDate.parse(ordersDto.getEstimateDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        orderFormEntity.setCustomer(customerEntity);
        orderFormEntity.setStatus("En attente de validation");

        orderFormEntity = ordersService.createOrder(orderFormEntity);
        return ResponseEntity.ok(orderFormEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderFormEntity> getOrder(@PathVariable Long id) {
        OrderFormEntity orderFormEntity = ordersService.getOrder(id);
        return ResponseEntity.ok(orderFormEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<OrderFormEntity>> getAllOrders() {
        Iterable<OrderFormEntity> orderFormEntities = ordersService.getAllOrders();
        return ResponseEntity.ok(orderFormEntities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        OrderFormEntity orderFormEntity = ordersService.getOrder(id);
        orderFormEntity.setStatus("Annulé");
        orderFormRepository.save(orderFormEntity);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> enableOrder(@PathVariable Long id) {
        OrderFormEntity orderFormEntity = ordersService.getOrder(id);
        orderFormEntity.setStatus("En attente de validation");
        orderFormRepository.save(orderFormEntity);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderFormEntity> updateOrder(@PathVariable Long id, @RequestBody OrdersDto ordersDto) {
        CustomerEntity customerEntity = customerRepository.findById(ordersDto.getCustomerId()).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "customer cannot be null"));

        OrderFormEntity orderFormEntity = ordersService.getOrder(id);
        orderFormEntity.setName(ordersDto.getName());
        orderFormEntity.setDescription(ordersDto.getDescription());
        orderFormEntity.setEstimateDate(LocalDate.parse(ordersDto.getEstimateDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        orderFormEntity.setStatus(ordersDto.getStatus());
        orderFormEntity.setCustomer(customerEntity);
        orderFormEntity = ordersService.updateOrder(orderFormEntity);
        return ResponseEntity.ok(orderFormEntity);
    }

    @PostMapping("/{id}/row/add")
    public void addRow(@PathVariable long id, @RequestBody OrdersRowDto ordersRowDto) {
        ordersService.addRow(id, ordersRowDto);
    }

    @GetMapping("/{id}/row/{rowId}")
    public OrderFormRowEntity getRow(@PathVariable long id, @PathVariable long rowId) {
        return ordersService.getRow(rowId);
    }

    @GetMapping("/{id}/row/all")
    public List<OrderFormRowEntity> getAllRows(@PathVariable long id) {
        return ordersService.getAllRows(id);
    }

    @DeleteMapping("/{id}/row/{rowId}")
    public void deleteRow(@PathVariable long id, @PathVariable long rowId) {
        ordersService.deleteRow(rowId);
    }


    @PutMapping("/{id}/row/{rowId}")
    public void updateRow(@PathVariable long id, @PathVariable long rowId, @RequestBody OrdersRowDto ordersRowDto) {
        ordersService.updateRow(rowId, ordersRowDto);
    }


    //when get download pdf
    @GetMapping("/{id}/pdf")
    public ResponseEntity<Resource> generatePDF(@PathVariable long id) throws IOException, ParseException {
        Path path = ordersService.generatePDF(id).toPath();
        Resource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", path.getFileName().toString());
        headers.setContentType(MediaType.APPLICATION_PDF);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);

    }


}