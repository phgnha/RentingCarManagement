package com.rentingcar.repository;

import com.rentingcar.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
    List<Branch> findByBranchNameContaining(String branchName);
    List<Branch> findByAddressContaining(String address);
}