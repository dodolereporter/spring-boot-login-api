package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.payload.dto.CustomerDto;
import fr.codesbuster.solidstock.api.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<CustomerEntity> addCustomer(@RequestBody CustomerDto customerDto) {

        if (customerDto == null) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getCompanyName() == null || customerDto.getCompanyName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getFirstName() == null || customerDto.getFirstName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getLastName() == null || customerDto.getLastName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getCity() == null || customerDto.getCity().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getZipCode() == null || customerDto.getZipCode().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getAddress() == null || customerDto.getAddress().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getStreetNumber() == null || customerDto.getStreetNumber().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getEmail() == null || !customerDto.getEmail().contains("@")) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getMobilePhone() == null || customerDto.getMobilePhone().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getCountry() == null || customerDto.getCountry().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getCorporation() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getSiret() == null || customerDto.getSiret().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getSiren() == null || customerDto.getSiren().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getRib() == null || customerDto.getRib().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (customerDto.getRcs() == 0) {
            return ResponseEntity.badRequest().build();
        }

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCompanyName(customerDto.getCompanyName());
        customerEntity.setFirstName(customerDto.getFirstName());
        customerEntity.setLastName(customerDto.getLastName());
        customerEntity.setCity(customerDto.getCity());
        customerEntity.setZipCode(customerDto.getZipCode());
        customerEntity.setAddress(customerDto.getAddress());
        customerEntity.setStreetNumber(customerDto.getStreetNumber());
        customerEntity.setEmail(customerDto.getEmail());
        customerEntity.setMobilePhone(customerDto.getMobilePhone());
        customerEntity.setHomePhone(customerDto.getHomePhone());
        customerEntity.setWorkPhone(customerDto.getWorkPhone());
        customerEntity.setWebSite(customerDto.getWebsite());
        customerEntity.setCountry(customerDto.getCountry());
        customerEntity.setCorporation(customerDto.getCorporation());
        customerEntity.setSiren(customerDto.getSiren());
        customerEntity.setSiret(customerDto.getSiret());
        customerEntity.setRib(customerDto.getRib());
        customerEntity.setRcs(customerDto.getRcs());


        customerEntity = customerService.createCustomer(customerEntity);

        return ResponseEntity.ok(customerEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<CustomerEntity>> getAllCustomer() {
        Iterable<CustomerEntity> customerEntities = customerService.getCustomers();
        return ResponseEntity.ok(customerEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerEntity> getCustomer(@PathVariable Long id) {
        CustomerEntity customerEntity = customerService.getCustomer(id);
        return ResponseEntity.ok(customerEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        CustomerEntity customerEntity = customerService.getCustomer(id);
        customerEntity.setCompanyName(customerDto.getCompanyName());
        customerEntity.setFirstName(customerDto.getFirstName());
        customerEntity.setLastName(customerDto.getLastName());
        customerEntity.setCity(customerDto.getCity());
        customerEntity.setZipCode(customerDto.getZipCode());
        customerEntity.setAddress(customerDto.getAddress());
        customerEntity.setStreetNumber(customerDto.getStreetNumber());
        customerEntity.setEmail(customerDto.getEmail());
        customerEntity.setMobilePhone(customerDto.getMobilePhone());
        customerEntity.setHomePhone(customerDto.getHomePhone());
        customerEntity.setWorkPhone(customerDto.getWorkPhone());
        customerEntity.setWebSite(customerDto.getWebsite());
        customerEntity.setCountry(customerDto.getCountry());
        customerEntity.setCorporation(customerDto.getCorporation());
        customerEntity.setSiren(customerDto.getSiren());
        customerEntity.setSiret(customerDto.getSiret());
        customerEntity.setRib(customerDto.getRib());
        customerEntity.setRcs(customerDto.getRcs());
        customerEntity = customerService.updateCustomer(customerEntity);
        return ResponseEntity.ok(customerEntity);
    }

}