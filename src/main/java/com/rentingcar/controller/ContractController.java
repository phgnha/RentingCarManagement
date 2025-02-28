package com.rentingcar.controller;

import com.rentingcar.model.Contract;
import com.rentingcar.model.enums.ContractStatus;
import com.rentingcar.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    public ResponseEntity<List<Contract>> getAllContracts() {
        return ResponseEntity.ok(contractService.getAllContracts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable Integer id) {
        return contractService.getContractById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Contract>> getContractsByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(contractService.getContractsByCustomer(customerId));
    }

    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<Contract>> getContractsByStaff(@PathVariable Integer staffId) {
        return ResponseEntity.ok(contractService.getContractsByStaff(staffId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Contract>> getContractsByStatus(@PathVariable ContractStatus status) {
        return ResponseEntity.ok(contractService.getContractsByStatus(status));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Contract>> getContractsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(contractService.getContractsByDateRange(startDate, endDate));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Contract>> getActiveContracts() {
        return ResponseEntity.ok(contractService.getActiveContracts());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Contract>> getOverdueContracts() {
        return ResponseEntity.ok(contractService.getOverdueContracts());
    }

    @PostMapping
    public ResponseEntity<Contract> createContract(@Valid @RequestBody Contract contract) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contractService.createContract(contract));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateContractStatus(
            @PathVariable Integer id,
            @RequestParam ContractStatus status) {
        contractService.updateContractStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/breach")
    public ResponseEntity<Void> reportBreachOfContract(
            @PathVariable Integer id,
            @RequestParam String breachDescription) {
        contractService.reportBreachOfContract(id, breachDescription);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Integer id) {
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
}