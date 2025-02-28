package com.rentingcar.service;

import com.rentingcar.model.Car;
import com.rentingcar.model.enums.CarCategory;
import com.rentingcar.model.enums.CarStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarService {
    Car saveCar(Car car);
    List<Car> getAllCars();
    Optional<Car> getCarById(Integer id);
    Optional<Car> getCarByLicensePlate(String licensePlate);
    List<Car> getAvailableCars();
    List<Car> getAvailableCarsByBranch(Integer branchId);
    List<Car> getCarsByCategory(CarCategory category);
    List<Car> getAvailableCarsByCategory(CarCategory category);
    List<Car> getCarsByBranch(Integer branchId);
    List<Car> getCarsByBrand(String brand);
    List<Car> getCarsByManufactureYear(Short year);
    void updateCarStatus(Integer id, CarStatus status);
    void deleteCar(Integer id);
    boolean checkCarAvailability(Integer carId, LocalDate startDate, LocalDate endDate);
}