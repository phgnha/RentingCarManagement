package com.rentingcar.service;

import com.rentingcar.model.Contract;
import com.rentingcar.model.enums.ContractStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ContractService {
    Contract createContract(Contract contract);
    List<Contract> getAllContracts();
    Optional<Contract> getContractById(Integer id);
    List<Contract> getContractsByCustomer(Integer customerId);
    List<Contract> getContractsByStaff(Integer staffId);
    List<Contract> getContractsByStatus(ContractStatus status);
    List<Contract> getContractsByDateRange(LocalDate startDate, LocalDate endDate);
    List<Contract> getActiveContracts();
    List<Contract> getOverdueContracts();
    void updateContractStatus(Integer id, ContractStatus status);
    void reportBreachOfContract(Integer id, String breachDescription);
    void deleteContract(Integer id);
}