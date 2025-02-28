package com.rentingcar.repository;

import com.rentingcar.model.*;
import com.rentingcar.model.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByContractId(Integer contractId);
    List<Invoice> findByStatus(InvoiceStatus status);
    List<Invoice> findByType(InvoiceType type);
    List<Invoice> findByPaymentMethod(PaymentMethod paymentMethod);
    List<Invoice> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate);
    List<Invoice> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);
    List<Invoice> findByStatusAndContractId(InvoiceStatus status, Integer contractId);
    
    @Query("SELECT SUM(i.amountPaid) FROM Invoice i WHERE i.paymentDate BETWEEN :startDate AND :endDate")
    Long calculateTotalRevenueBetweenDates(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT SUM(i.amountPaid) FROM Invoice i WHERE i.paymentDate BETWEEN :startDate AND :endDate AND i.type = :type")
    Long calculateTotalRevenueByTypeBetweenDates(LocalDate startDate, LocalDate endDate, InvoiceType type);
}