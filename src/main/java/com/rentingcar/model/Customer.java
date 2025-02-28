package com.rentingcar.model;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name="customer")
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "personID",nullable = false)
    private Person person;

    @Column(length = 20)
    private String licenseNumber;

    private LocalDate licenseExpireDate;

    @OneToMany(mappedBy = "customer")
    private List<Contract> contracts;
}
