package com.rentingcar.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name="person")
@Getter
@Setter
public class Person{
    @Id
    @Column(length = 20)
    private String id;

    private String lastName;
    private String firstName;
    private LocalDate bornDate;

    @Column(length = 30, unique = true)
    private String email;
    
    private String address;

    private String phoneNumber;

}
