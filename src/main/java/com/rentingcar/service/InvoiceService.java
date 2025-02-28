package com.rentingcar.service;

import com.rentingcar.model.Invoice;
import com.rentingcar.model.enums.InvoiceStatus;
import com.rentingcar.model.enums.InvoiceType;
import com.rentingcar.model.enums.PaymentMethod;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    Invoice createInvoice(Invoice invoice);
    List<Invoice> getAllInvoices();
    Optional<Invoice> getInvoiceById(Integer id);
    List<Invoice> getInvoicesByContractId(Integer contractId);
    List<Invoice> getInvoicesByStatus(InvoiceStatus status);
    List<Invoice> getInvoicesByType(InvoiceType type);
    List<Invoice> getInvoicesByReleaseDateRange(LocalDate startDate, LocalDate endDate);
    List<Invoice> getInvoicesByPaymentDateRange(LocalDate startDate, LocalDate endDate);
    List<Invoice> getInvoicesByPaymentMethod(PaymentMethod paymentMethod);
    void processPayment(Integer id, Long amount, PaymentMethod method);
    void updateInvoiceStatus(Integer id, InvoiceStatus status);
    Long calculateTotalRevenue(LocalDate startDate, LocalDate endDate);
    Long calculateRevenueByType(InvoiceType type, LocalDate startDate, LocalDate endDate);
    void deleteInvoice(Integer id);
}