package com.rentingcar.service.impl;

import com.rentingcar.model.Staff;
import com.rentingcar.repository.StaffRepository;
import com.rentingcar.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    
    private final StaffRepository staffRepository;
    
    @Override
    @Transactional
    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }
    
    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }
    
    @Override
    public Optional<Staff> getStaffById(Integer id) {
        return staffRepository.findById(id);
    }
    
    @Override
    public List<Staff> getStaffByBranch(Integer branchId) {
        return staffRepository.findByBranchId(branchId);
    }
    
    @Override
    public List<Staff> getManagers() {
        return staffRepository.findByIsManagerTrue();
    }
    
    @Override
    public List<Staff> getStaffByPosition(String position) {
        return staffRepository.findByPosition(position);
    }
    
    @Override
    public List<Staff> findStaffByName(String name) {
        return staffRepository.findByPersonName(name);
    }
    
    @Override
    public Optional<Staff> findStaffByPersonId(String personId) {
        return staffRepository.findByPersonId(personId);
    }
    
    @Override
    @Transactional
    public void deleteStaff(Integer id) {
        staffRepository.deleteById(id);
    }
    
    @Override
    @Transactional
    public void promoteToManager(Integer id) {
        staffRepository.findById(id).ifPresent(staff -> {
            staff.setIsManager(true);
            staffRepository.save(staff);
        });
    }
}