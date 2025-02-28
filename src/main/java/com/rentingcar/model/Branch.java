package com.rentingcar.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "branch")
@Getter 
@Setter
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String branchName;
    private String address;
    private String phoneNumber;
    
    @OneToMany(mappedBy = "branch")
    private List<Staff> staff;
    
    @OneToMany(mappedBy = "branch")
    private List<Car> cars;
}