package com.rentingcar.repository;

import com.rentingcar.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractDetailRepository extends JpaRepository<ContractDetail, Integer> {
    List<ContractDetail> findByContractId(Integer contractId);
    List<ContractDetail> findByCarId(Integer carId);
    Optional<ContractDetail> findByContractIdAndCarId(Integer contractId, Integer carId);
    List<ContractDetail> findByDeliveryDateBetween(LocalDate startDate, LocalDate endDate);
    List<ContractDetail> findByReturnDateBetween(LocalDate startDate, LocalDate endDate);
}