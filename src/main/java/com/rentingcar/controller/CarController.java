package com.rentingcar.controller;

import com.rentingcar.model.Car;
import com.rentingcar.model.enums.CarCategory;
import com.rentingcar.model.enums.CarStatus;
import com.rentingcar.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer id) {
        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/license-plate/{licensePlate}")
    public ResponseEntity<Car> getCarByLicensePlate(@PathVariable String licensePlate) {
        return carService.getCarByLicensePlate(licensePlate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Car>> getAvailableCars() {
        return ResponseEntity.ok(carService.getAvailableCars());
    }

    @GetMapping("/available/branch/{branchId}")
    public ResponseEntity<List<Car>> getAvailableCarsByBranch(@PathVariable Integer branchId) {
        return ResponseEntity.ok(carService.getAvailableCarsByBranch(branchId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Car>> getCarsByCategory(@PathVariable CarCategory category) {
        return ResponseEntity.ok(carService.getCarsByCategory(category));
    }

    @GetMapping("/available/category/{category}")
    public ResponseEntity<List<Car>> getAvailableCarsByCategory(@PathVariable CarCategory category) {
        return ResponseEntity.ok(carService.getAvailableCarsByCategory(category));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<Car>> getCarsByBranch(@PathVariable Integer branchId) {
        return ResponseEntity.ok(carService.getCarsByBranch(branchId));
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Car>> getCarsByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(carService.getCarsByBrand(brand));
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<Car>> getCarsByManufactureYear(@PathVariable Short year) {
        return ResponseEntity.ok(carService.getCarsByManufactureYear(year));
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@Valid @RequestBody Car car) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.saveCar(car));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Integer id, @Valid @RequestBody Car car) {
        if (!id.equals(car.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(carService.saveCar(car));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateCarStatus(@PathVariable Integer id, @RequestParam CarStatus status) {
        carService.updateCarStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-availability/{id}")
    public ResponseEntity<Boolean> checkCarAvailability(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(carService.checkCarAvailability(id, startDate, endDate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}