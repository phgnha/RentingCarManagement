package com.rentingcar.model;

import com.rentingcar.model.enums.ContractStatus;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "contract")
@Getter 
@Setter
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "staffID")
    private Staff staff;
    
    @ManyToOne
    @JoinColumn(name = "customerID")
    private Customer customer;
    
    private LocalDate beginDate;
    
    private LocalDate endDate;
    
    private Long totalCost;
    
    @Column(length = 200)
    private String additionalServices; // Simplified from PlugInService
    
    @Column(length = 200)
    private String breachDescription; // Simplified from BreachOfContract
    
    @Enumerated(EnumType.STRING)
    private ContractStatus status;
    
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private List<ContractDetail> contractDetails;
    
    @OneToMany(mappedBy = "contract")
    private List<Invoice> invoices;
}
