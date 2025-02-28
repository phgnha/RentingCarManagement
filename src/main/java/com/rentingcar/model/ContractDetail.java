package com.rentingcar.model;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "contractDetail")
@Getter @Setter
public class ContractDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "contractID")
    private Contract contract;
    
    @ManyToOne
    @JoinColumn(name = "carID")
    private Car car;
    
    private LocalDate deliveryDate;
    
    private LocalDate returnDate;
    
    private Long rent;
}