package com.rentingcar.controller;

import com.rentingcar.model.Customer;
import com.rentingcar.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<Customer> getCustomerByPersonId(@PathVariable String personId) {
        return customerService.getCustomerByPersonId(personId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/license/{licenseNumber}")
    public ResponseEntity<Customer> getCustomerByLicenseNumber(@PathVariable String licenseNumber) {
        return customerService.getCustomerByLicenseNumber(licenseNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> getCustomersByName(@RequestParam String name) {
        return ResponseEntity.ok(customerService.getCustomersByName(name));
    }

    @GetMapping("/expired-licenses")
    public ResponseEntity<List<Customer>> findCustomersWithExpiredLicenses() {
        return ResponseEntity.ok(customerService.findCustomersWithExpiredLicenses());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> findCustomerByEmail(@PathVariable String email) {
        return customerService.findCustomerByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.saveCustomer(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @Valid @RequestBody Customer customer) {
        if (!id.equals(customer.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(customerService.saveCustomer(customer));
    }

    @GetMapping("/{id}/valid-license")
    public ResponseEntity<Boolean> isLicenseValid(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.isLicenseValid(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}