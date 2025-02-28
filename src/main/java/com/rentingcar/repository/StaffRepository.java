package com.rentingcar.repository;

import com.rentingcar.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    List<Staff> findByBranchId(Integer branchId);
    List<Staff> findByIsManagerTrue();
    List<Staff> findByPosition(String position);
    
    @Query("SELECT s FROM Staff s JOIN s.person p WHERE p.lastName LIKE %:name% OR p.firstName LIKE %:name%")
    List<Staff> findByPersonName(String name);
    
    Optional<Staff> findByPersonId(String personId);
}