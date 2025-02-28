package com.rentingcar.controller;

import com.rentingcar.model.Invoice;
import com.rentingcar.model.enums.InvoiceStatus;
import com.rentingcar.model.enums.InvoiceType;
import com.rentingcar.model.enums.PaymentMethod;
import com.rentingcar.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Integer id) {
        return invoiceService.getInvoiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<List<Invoice>> getInvoicesByContractId(@PathVariable Integer contractId) {
        return ResponseEntity.ok(invoiceService.getInvoicesByContractId(contractId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Invoice>> getInvoicesByStatus(@PathVariable InvoiceStatus status) {
        return ResponseEntity.ok(invoiceService.getInvoicesByStatus(status));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Invoice>> getInvoicesByType(@PathVariable InvoiceType type) {
        return ResponseEntity.ok(invoiceService.getInvoicesByType(type));
    }

    @GetMapping("/release-date-range")
    public ResponseEntity<List<Invoice>> getInvoicesByReleaseDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(invoiceService.getInvoicesByReleaseDateRange(startDate, endDate));
    }

    @GetMapping("/payment-date-range")
    public ResponseEntity<List<Invoice>> getInvoicesByPaymentDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(invoiceService.getInvoicesByPaymentDateRange(startDate, endDate));
    }

    @GetMapping("/payment-method/{method}")
    public ResponseEntity<List<Invoice>> getInvoicesByPaymentMethod(@PathVariable PaymentMethod method) {
        return ResponseEntity.ok(invoiceService.getInvoicesByPaymentMethod(method));
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.createInvoice(invoice));
    }

    @PatchMapping("/{id}/payment")
    public ResponseEntity<Void> processPayment(
            @PathVariable Integer id,
            @RequestParam Long amount,
            @RequestParam PaymentMethod method) {
        invoiceService.processPayment(id, amount, method);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateInvoiceStatus(
            @PathVariable Integer id,
            @RequestParam InvoiceStatus status) {
        invoiceService.updateInvoiceStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/revenue")
    public ResponseEntity<Long> calculateTotalRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(invoiceService.calculateTotalRevenue(startDate, endDate));
    }

    @GetMapping("/revenue/type/{type}")
    public ResponseEntity<Long> calculateRevenueByType(
            @PathVariable InvoiceType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(invoiceService.calculateRevenueByType(type, startDate, endDate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Integer id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}