package com.rentingcar.repository;

import com.rentingcar.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByPersonId(String personId);
    Optional<Customer> findByLicenseNumber(String licenseNumber);
    List<Customer> findByLicenseExpireDateBefore(LocalDate date);
    
    @Query("SELECT c FROM Customer c JOIN c.person p WHERE p.lastName LIKE %:name% OR p.firstName LIKE %:name%")
    List<Customer> findByPersonName(String name);
    
    @Query("SELECT c FROM Customer c JOIN c.person p WHERE p.email = :email")
    Optional<Customer> findByPersonEmail(String email);
}