package com.rentingcar.service;

import com.rentingcar.model.Branch;
import java.util.List;
import java.util.Optional;

public interface BranchService {
    Branch saveBranch(Branch branch);
    List<Branch> getAllBranches();
    Optional<Branch> getBranchById(Integer id);
    List<Branch> findBranchesByName(String name);
    List<Branch> findBranchesByAddress(String address);
    void deleteBranch(Integer id);
}