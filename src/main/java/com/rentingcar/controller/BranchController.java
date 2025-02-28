package com.rentingcar.controller;

import com.rentingcar.model.Branch;
import com.rentingcar.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Integer id) {
        return branchService.getBranchById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Branch>> findBranchesByName(@RequestParam String name) {
        return ResponseEntity.ok(branchService.findBranchesByName(name));
    }

    @GetMapping("/search/address")
    public ResponseEntity<List<Branch>> findBranchesByAddress(@RequestParam String address) {
        return ResponseEntity.ok(branchService.findBranchesByAddress(address));
    }

    @PostMapping
    public ResponseEntity<Branch> createBranch(@Valid @RequestBody Branch branch) {
        return ResponseEntity.status(HttpStatus.CREATED).body(branchService.saveBranch(branch));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Integer id, @Valid @RequestBody Branch branch) {
        if (!id.equals(branch.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(branchService.saveBranch(branch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Integer id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}