package com.rentingcar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentingcar.model.Branch;
import com.rentingcar.service.BranchService;
import com.rentingcar.repository.BranchRepository;

import jakarta.transaction.Transactional;

@Service
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    
    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository){
        this.branchRepository=branchRepository;
    }

    @Override
    @Transactional
    public Branch saveBranch(Branch branch) {
        return branchRepository.save(branch);
    }
    
    @Override
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }
    
    @Override
    public Optional<Branch> getBranchById(Integer id) {
        return branchRepository.findById(id);
    }
    
    @Override
    public List<Branch> findBranchesByName(String name) {
        return branchRepository.findByBranchNameContaining(name);
    }
    
    @Override
    public List<Branch> findBranchesByAddress(String address) {
        return branchRepository.findByAddressContaining(address);
    }
    
    @Override
    @Transactional
    public void deleteBranch(Integer id) {
        branchRepository.deleteById(id);
    }
    
}
