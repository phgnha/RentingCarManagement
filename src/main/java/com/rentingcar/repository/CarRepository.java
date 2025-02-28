package com.rentingcar.repository;


import com.rentingcar.model.*;
import com.rentingcar.model.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    Optional<Car> findByLicensePlateNumber(String licensePlateNumber);
    List<Car> findByStatus(CarStatus status);
    List<Car> findByBranchId(Integer branchId);
    List<Car> findByCategory(CarCategory category);
    List<Car> findByBrandContaining(String brand);
    List<Car> findByStatusAndBranchId(CarStatus status, Integer branchId);
    List<Car> findByCategoryAndStatus(CarCategory category, CarStatus status);
    List<Car> findByManufactureYearGreaterThanEqual(Short year);
}