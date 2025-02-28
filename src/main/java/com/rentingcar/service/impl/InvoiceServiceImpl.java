package com.rentingcar.service.impl;

import com.rentingcar.model.Invoice;
import com.rentingcar.model.enums.InvoiceStatus;
import com.rentingcar.model.enums.InvoiceType;
import com.rentingcar.model.enums.PaymentMethod;
import com.rentingcar.repository.InvoiceRepository;
import com.rentingcar.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    
    private final InvoiceRepository invoiceRepository;
    
    @Override
    @Transactional
    public Invoice createInvoice(Invoice invoice) {
        if (invoice.getReleaseDate() == null) {
            invoice.setReleaseDate(LocalDate.now());
        }
        
        return invoiceRepository.save(invoice);
    }
    
    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }
    
    @Override
    public Optional<Invoice> getInvoiceById(Integer id) {
        return invoiceRepository.findById(id);
    }
    
    @Override
    public List<Invoice> getInvoicesByContractId(Integer contractId) {
        return invoiceRepository.findByContractId(contractId);
    }
    
    @Override
    public List<Invoice> getInvoicesByStatus(InvoiceStatus status) {
        return invoiceRepository.findByStatus(status);
    }
    
    @Override
    public List<Invoice> getInvoicesByType(InvoiceType type) {
        return invoiceRepository.findByType(type);
    }
    
    @Override
    public List<Invoice> getInvoicesByReleaseDateRange(LocalDate startDate, LocalDate endDate) {
        return invoiceRepository.findByReleaseDateBetween(startDate, endDate);
    }
    
    @Override
    public List<Invoice> getInvoicesByPaymentDateRange(LocalDate startDate, LocalDate endDate) {
        return invoiceRepository.findByPaymentDateBetween(startDate, endDate);
    }
    
    @Override
    public List<Invoice> getInvoicesByPaymentMethod(PaymentMethod paymentMethod) {
        return invoiceRepository.findByPaymentMethod(paymentMethod);
    }
    
    @Override
    @Transactional
    public void processPayment(Integer id, Long amount, PaymentMethod method) {
        invoiceRepository.findById(id).ifPresent(invoice -> {
            if (amount >= invoice.getTotalCost()) {
                invoice.setStatus(InvoiceStatus.DA_THANH_TOAN);
            } else {
                invoice.setStatus(InvoiceStatus.CHO_THANH_TOAN);
            }
            
            invoice.setPaymentDate(LocalDate.now());
            invoice.setAmountPaid(amount);
            invoice.setPaymentMethod(method);
            
            invoiceRepository.save(invoice);
        });
    }
    
    @Override
    @Transactional
    public void updateInvoiceStatus(Integer id, InvoiceStatus status) {
        invoiceRepository.findById(id).ifPresent(invoice -> {
            invoice.setStatus(status);
            invoiceRepository.save(invoice);
        });
    }
    
    @Override
    public Long calculateTotalRevenue(LocalDate startDate, LocalDate endDate) {
        Long revenue = invoiceRepository.calculateTotalRevenueBetweenDates(startDate, endDate);
        return revenue != null ? revenue : 0L;
    }
    
    @Override
    public Long calculateRevenueByType(InvoiceType type, LocalDate startDate, LocalDate endDate) {
        Long revenue = invoiceRepository.calculateTotalRevenueByTypeBetweenDates(startDate, endDate, type);
        return revenue != null ? revenue : 0L;
    }
    
    @Override
    @Transactional
    public void deleteInvoice(Integer id) {
        invoiceRepository.deleteById(id);
    }
}