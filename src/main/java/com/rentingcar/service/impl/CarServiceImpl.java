package com.rentingcar.service.impl;

import com.rentingcar.model.Car;
import com.rentingcar.model.ContractDetail;
import com.rentingcar.model.enums.*;
import com.rentingcar.repository.CarRepository;
import com.rentingcar.repository.ContractDetailRepository;
import com.rentingcar.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    
    private final CarRepository carRepository;
    private final ContractDetailRepository contractDetailRepository;
    
    @Override
    @Transactional
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }
    
    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
    
    @Override
    public Optional<Car> getCarById(Integer id) {
        return carRepository.findById(id);
    }
    
    @Override
    public Optional<Car> getCarByLicensePlate(String licensePlate) {
        return carRepository.findByLicensePlateNumber(licensePlate);
    }
    
    @Override
    public List<Car> getAvailableCars() {
        return carRepository.findByStatus(CarStatus.SAN_SANG);
    }
    
    @Override
    public List<Car> getAvailableCarsByBranch(Integer branchId) {
        return carRepository.findByStatusAndBranchId(CarStatus.SAN_SANG, branchId);
    }
    
    @Override
    public List<Car> getCarsByCategory(CarCategory category) {
        return carRepository.findByCategory(category);
    }
    
    @Override
    public List<Car> getAvailableCarsByCategory(CarCategory category) {
        return carRepository.findByCategoryAndStatus(category, CarStatus.SAN_SANG);
    }
    
    @Override
    public List<Car> getCarsByBranch(Integer branchId) {
        return carRepository.findByBranchId(branchId);
    }
    
    @Override
    public List<Car> getCarsByBrand(String brand) {
        return carRepository.findByBrandContaining(brand);
    }
    
    @Override
    public List<Car> getCarsByManufactureYear(Short year) {
        return carRepository.findByManufactureYearGreaterThanEqual(year);
    }
    
    @Override
    @Transactional
    public void updateCarStatus(Integer id, CarStatus status) {
        carRepository.findById(id).ifPresent(car -> {
            car.setStatus(status);
            carRepository.save(car);
        });
    }
    
    @Override
    @Transactional
    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }
    
    @Override
    public boolean checkCarAvailability(Integer carId, LocalDate startDate, LocalDate endDate) {
        // First check if car is available at all
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isEmpty() || carOpt.get().getStatus() != CarStatus.SAN_SANG) {
            return false;
        }
        
        // Then check if car is booked for the requested period
        List<ContractDetail> bookings = contractDetailRepository.findByCarId(carId);
        
        return bookings.stream().noneMatch(booking -> 
            (startDate.isBefore(booking.getReturnDate()) || startDate.isEqual(booking.getReturnDate())) &&
            (endDate.isAfter(booking.getDeliveryDate()) || endDate.isEqual(booking.getDeliveryDate())));
    }
}