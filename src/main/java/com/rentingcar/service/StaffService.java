package com.rentingcar.service;

import com.rentingcar.model.Staff;
import java.util.List;
import java.util.Optional;

public interface StaffService {
    Staff saveStaff(Staff staff);
    List<Staff> getAllStaff();
    Optional<Staff> getStaffById(Integer id);
    List<Staff> getStaffByBranch(Integer branchId);
    List<Staff> getManagers();
    List<Staff> getStaffByPosition(String position);
    List<Staff> findStaffByName(String name);
    Optional<Staff> findStaffByPersonId(String personId);
    void deleteStaff(Integer id);
    void promoteToManager(Integer id);
}