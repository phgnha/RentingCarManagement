package com.rentingcar.model;

import com.rentingcar.model.enums.*;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "car")
@Getter 
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = 20)
    private String licensePlateNumber;
    
    @Column(length = 20)
    private String model;
    
    private Short manufactureYear;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarCategory category;
    
    @Column(length = 20)
    private String brand;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarColor color;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GearboxType gearboxType;
    
    private Integer distanceTravel;
    
    @Enumerated(EnumType.STRING)
    private CarStatus status;
    
    @ManyToOne
    @JoinColumn(name = "branchID")
    private Branch branch;
    
    @OneToMany(mappedBy = "car")
    private List<ContractDetail> contractDetails;
}