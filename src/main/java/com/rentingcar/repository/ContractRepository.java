package com.rentingcar.repository;

import com.rentingcar.model.*;
import com.rentingcar.model.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    List<Contract> findByCustomerId(Integer customerId);
    List<Contract> findByStaffId(Integer staffId);
    List<Contract> findByStatus(ContractStatus status);
    List<Contract> findByBeginDateBetween(LocalDate startDate, LocalDate endDate);
    List<Contract> findByEndDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT c FROM Contract c WHERE c.beginDate <= :date AND c.endDate >= :date")
    List<Contract> findActiveContractsAtDate(LocalDate date);
    
    @Query("SELECT c FROM Contract c WHERE c.status = 'DANG_THUC_HIEN' AND c.endDate < CURRENT_DATE")
    List<Contract> findOverdueContracts();
    
    List<Contract> findByBreachDescriptionIsNotNull();
}