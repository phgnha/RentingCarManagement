package com.rentingcar.model;


import com.rentingcar.model.enums.*;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoice")
@Getter 
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "contractID")
    private Contract contract;
    
    private Long totalCost;
    
    private LocalDate releaseDate;
    
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
    
    @Enumerated(EnumType.STRING)
    private InvoiceType type;
    
    // Payment data integrated
    private LocalDate paymentDate;
    
    private Long amountPaid;
    
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
}