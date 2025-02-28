package com.rentingcar.service;

import com.rentingcar.model.ContractDetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ContractDetailService {
    ContractDetail saveContractDetail(ContractDetail contractDetail);
    List<ContractDetail> getAllContractDetails();
    Optional<ContractDetail> getContractDetailById(Integer id);
    List<ContractDetail> getContractDetailsByContractId(Integer contractId);
    List<ContractDetail> getContractDetailsByCarId(Integer carId);
    Optional<ContractDetail> getContractDetailByContractAndCar(Integer contractId, Integer carId);
    List<ContractDetail> getContractDetailsByDeliveryDateRange(LocalDate startDate, LocalDate endDate);
    List<ContractDetail> getContractDetailsByReturnDateRange(LocalDate startDate, LocalDate endDate);
    void updateRentalDates(Integer id, LocalDate deliveryDate, LocalDate returnDate);
    void deleteContractDetail(Integer id);
}