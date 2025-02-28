package com.rentingcar.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "staff")
@Getter 
@Setter
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne
    @JoinColumn(name = "personID")
    private Person person;
    
    private LocalDate hireDate;
    
    @ManyToOne
    @JoinColumn(name = "branchID")
    private Branch branch;
    
    private String position;
    
    @Column(name = "isManager", nullable = false)
    private Boolean isManager = false; 
    
    @OneToMany(mappedBy = "staff")
    private List<Contract> contracts;
}