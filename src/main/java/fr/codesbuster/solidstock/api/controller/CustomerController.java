package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.CustomerEntity;
import fr.codesbuster.solidstock.api.payload.dto.CustomerDto;
import fr.codesbuster.solidstock.api.repository.CustomerRepository;
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
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<CustomerEntity> addCustomer(@RequestBody CustomerDto customerDto) {

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
        customerEntity.setWebsite(customerDto.getWebsite());
        customerEntity.setCountry(customerDto.getCountry());
        customerEntity.setSiren(customerDto.getSiren());
        customerEntity.setSiret(customerDto.getSiret());
        customerEntity.setRib(customerDto.getRib());
        customerEntity.setRcs(customerDto.getRcs());
        customerEntity.setFax(customerDto.getFax());
        customerEntity.setNote(customerDto.getNote());


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
        CustomerEntity customerEntity = customerService.getCustomer(id);
        customerEntity.setDisabled(false);
        customerRepository.save(customerEntity);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> enableCustomer(@PathVariable Long id) {
        CustomerEntity customerEntity = customerService.getCustomer(id);
        customerEntity.setDisabled(false);
        customerRepository.save(customerEntity);
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
        customerEntity.setWebsite(customerDto.getWebsite());
        customerEntity.setCountry(customerDto.getCountry());
        customerEntity.setSiren(customerDto.getSiren());
        customerEntity.setSiret(customerDto.getSiret());
        customerEntity.setRib(customerDto.getRib());
        customerEntity.setRcs(customerDto.getRcs());
        customerEntity.setFax(customerDto.getFax());
        customerEntity.setNote(customerDto.getNote());
        customerEntity = customerService.updateCustomer(customerEntity);
        return ResponseEntity.ok(customerEntity);
    }

}
