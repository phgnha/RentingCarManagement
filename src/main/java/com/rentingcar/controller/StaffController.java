package com.rentingcar.controller;

import com.rentingcar.model.Staff;
import com.rentingcar.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Integer id) {
        return staffService.getStaffById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<Staff>> getStaffByBranch(@PathVariable Integer branchId) {
        return ResponseEntity.ok(staffService.getStaffByBranch(branchId));
    }

    @GetMapping("/managers")
    public ResponseEntity<List<Staff>> getManagers() {
        return ResponseEntity.ok(staffService.getManagers());
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<List<Staff>> getStaffByPosition(@PathVariable String position) {
        return ResponseEntity.ok(staffService.getStaffByPosition(position));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Staff>> findStaffByName(@RequestParam String name) {
        return ResponseEntity.ok(staffService.findStaffByName(name));
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<Staff> findStaffByPersonId(@PathVariable String personId) {
        return staffService.findStaffByPersonId(personId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Staff> createStaff(@Valid @RequestBody Staff staff) {
        return ResponseEntity.status(HttpStatus.CREATED).body(staffService.saveStaff(staff));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Integer id, @Valid @RequestBody Staff staff) {
        if (!id.equals(staff.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(staffService.saveStaff(staff));
    }

    @PatchMapping("/{id}/promote")
    public ResponseEntity<Void> promoteToManager(@PathVariable Integer id) {
        staffService.promoteToManager(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Integer id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}