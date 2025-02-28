package com.rentingcar.service.impl;

import com.rentingcar.model.ContractDetail;
import com.rentingcar.repository.ContractDetailRepository;
import com.rentingcar.service.ContractDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractDetailServiceImpl implements ContractDetailService {
    
    private final ContractDetailRepository contractDetailRepository;
    
    @Override
    @Transactional
    public ContractDetail saveContractDetail(ContractDetail contractDetail) {
        // Validate dates
        if (contractDetail.getReturnDate().isBefore(contractDetail.getDeliveryDate())) {
            throw new IllegalArgumentException("Return date cannot be before delivery date");
        }
        
        return contractDetailRepository.save(contractDetail);
    }
    
    @Override
    public List<ContractDetail> getAllContractDetails() {
        return contractDetailRepository.findAll();
    }
    
    @Override
    public Optional<ContractDetail> getContractDetailById(Integer id) {
        return contractDetailRepository.findById(id);
    }
    
    @Override
    public List<ContractDetail> getContractDetailsByContractId(Integer contractId) {
        return contractDetailRepository.findByContractId(contractId);
    }
    
    @Override
    public List<ContractDetail> getContractDetailsByCarId(Integer carId) {
        return contractDetailRepository.findByCarId(carId);
    }
    
    @Override
    public Optional<ContractDetail> getContractDetailByContractAndCar(Integer contractId, Integer carId) {
        return contractDetailRepository.findByContractIdAndCarId(contractId, carId);
    }
    
    @Override
    public List<ContractDetail> getContractDetailsByDeliveryDateRange(LocalDate startDate, LocalDate endDate) {
        return contractDetailRepository.findByDeliveryDateBetween(startDate, endDate);
    }
    
    @Override
    public List<ContractDetail> getContractDetailsByReturnDateRange(LocalDate startDate, LocalDate endDate) {
        return contractDetailRepository.findByReturnDateBetween(startDate, endDate);
    }
    
    @Override
    @Transactional
    public void updateRentalDates(Integer id, LocalDate deliveryDate, LocalDate returnDate) {
        // Validate dates
        if (returnDate.isBefore(deliveryDate)) {
            throw new IllegalArgumentException("Return date cannot be before delivery date");
        }
        
        contractDetailRepository.findById(id).ifPresent(detail -> {
            detail.setDeliveryDate(deliveryDate);
            detail.setReturnDate(returnDate);
            contractDetailRepository.save(detail);
        });
    }
    
    @Override
    @Transactional
    public void deleteContractDetail(Integer id) {
        contractDetailRepository.deleteById(id);
    }
}