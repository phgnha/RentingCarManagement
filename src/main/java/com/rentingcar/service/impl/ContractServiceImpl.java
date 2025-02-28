package com.rentingcar.service.impl;

import com.rentingcar.model.Contract;
import com.rentingcar.model.Car;
import com.rentingcar.model.enums.CarStatus;
import com.rentingcar.model.enums.ContractStatus;
import com.rentingcar.repository.ContractRepository;
import com.rentingcar.repository.CarRepository;
import com.rentingcar.service.ContractService;
import com.rentingcar.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {
    
    private final ContractRepository contractRepository;
    private final CarService carService;
    
    @Override
    @Transactional
    public Contract createContract(Contract contract) {
        // Validate contract dates
        if (contract.getEndDate().isBefore(contract.getBeginDate())) {
            throw new IllegalArgumentException("End date cannot be before begin date");
        }
        
        Contract savedContract = contractRepository.save(contract);
        
        // Update car status for all cars in the contract
        if (savedContract.getStatus() == ContractStatus.DANG_THUC_HIEN) {
            savedContract.getContractDetails().forEach(detail -> {
                carService.updateCarStatus(detail.getCar().getId(), CarStatus.DANG_CHO_THUE);
            });
        }
        
        return savedContract;
    }
    
    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }
    
    @Override
    public Optional<Contract> getContractById(Integer id) {
        return contractRepository.findById(id);
    }
    
    @Override
    public List<Contract> getContractsByCustomer(Integer customerId) {
        return contractRepository.findByCustomerId(customerId);
    }
    
    @Override
    public List<Contract> getContractsByStaff(Integer staffId) {
        return contractRepository.findByStaffId(staffId);
    }
    
    @Override
    public List<Contract> getContractsByStatus(ContractStatus status) {
        return contractRepository.findByStatus(status);
    }
    
    @Override
    public List<Contract> getContractsByDateRange(LocalDate startDate, LocalDate endDate) {
        return contractRepository.findByBeginDateBetween(startDate, endDate);
    }
    
    @Override
    public List<Contract> getActiveContracts() {
        return contractRepository.findActiveContractsAtDate(LocalDate.now());
    }
    
    @Override
    public List<Contract> getOverdueContracts() {
        return contractRepository.findOverdueContracts();
    }
    
    @Override
    @Transactional
    public void updateContractStatus(Integer id, ContractStatus status) {
        contractRepository.findById(id).ifPresent(contract -> {
            contract.setStatus(status);
            
            // If contract is completed, make cars available again
            if (status == ContractStatus.HOAN_THANH) {
                contract.getContractDetails().forEach(detail -> {
                    carService.updateCarStatus(detail.getCar().getId(), CarStatus.SAN_SANG);
                });
            }
            
            contractRepository.save(contract);
        });
    }
    
    @Override
    @Transactional
    public void reportBreachOfContract(Integer id, String breachDescription) {
        contractRepository.findById(id).ifPresent(contract -> {
            contract.setBreachDescription(breachDescription);
            contract.setStatus(ContractStatus.VI_PHAM);
            contractRepository.save(contract);
        });
    }
    
    @Override
    @Transactional
    public void deleteContract(Integer id) {
        contractRepository.deleteById(id);
    }
}