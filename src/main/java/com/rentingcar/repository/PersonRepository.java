package com.rentingcar.repository;

import com.rentingcar.model.*;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PersonRepository extends JpaRepository<Person,String> {
    Optional<Person> findByEmail(String email);
    List<Person> findByLastNameContainingOrFirstNameContaining(String lastName, String firstName);
    boolean existsByEmail(String email);
    Optional<Person> findByPhoneNumber(String phoneNumber);

}
