package com.rentingcar.controller;

import com.rentingcar.model.ContractDetail;
import com.rentingcar.service.ContractDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contract-details")
@RequiredArgsConstructor
public class ContractDetailController {

    private final ContractDetailService contractDetailService;

    @GetMapping
    public ResponseEntity<List<ContractDetail>> getAllContractDetails() {
        return ResponseEntity.ok(contractDetailService.getAllContractDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDetail> getContractDetailById(@PathVariable Integer id) {
        return contractDetailService.getContractDetailById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<List<ContractDetail>> getContractDetailsByContractId(@PathVariable Integer contractId) {
        return ResponseEntity.ok(contractDetailService.getContractDetailsByContractId(contractId));
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<ContractDetail>> getContractDetailsByCarId(@PathVariable Integer carId) {
        return ResponseEntity.ok(contractDetailService.getContractDetailsByCarId(carId));
    }

    @GetMapping("/contract/{contractId}/car/{carId}")
    public ResponseEntity<ContractDetail> getContractDetailByContractAndCar(
            @PathVariable Integer contractId,
            @PathVariable Integer carId) {
        return contractDetailService.getContractDetailByContractAndCar(contractId, carId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/delivery-date-range")
    public ResponseEntity<List<ContractDetail>> getContractDetailsByDeliveryDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(contractDetailService.getContractDetailsByDeliveryDateRange(startDate, endDate));
    }

    @GetMapping("/return-date-range")
    public ResponseEntity<List<ContractDetail>> getContractDetailsByReturnDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(contractDetailService.getContractDetailsByReturnDateRange(startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<ContractDetail> createContractDetail(@Valid @RequestBody ContractDetail contractDetail) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contractDetailService.saveContractDetail(contractDetail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractDetail> updateContractDetail(
            @PathVariable Integer id, 
            @Valid @RequestBody ContractDetail contractDetail) {
        if (!id.equals(contractDetail.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(contractDetailService.saveContractDetail(contractDetail));
    }

    @PatchMapping("/{id}/dates")
    public ResponseEntity<Void> updateRentalDates(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deliveryDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate) {
        contractDetailService.updateRentalDates(id, deliveryDate, returnDate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContractDetail(@PathVariable Integer id) {
        contractDetailService.deleteContractDetail(id);
        return ResponseEntity.noContent().build();
    }
}