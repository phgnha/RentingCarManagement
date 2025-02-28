package com.rentingcar.service;

import com.rentingcar.model.Person;
import java.util.List;
import java.util.Optional;

public interface PersonService {
    Person savePerson(Person person);
    List<Person> getAllPersons();
    Optional<Person> getPersonById(String id);
    Optional<Person> getPersonByEmail(String email);
    List<Person> findPersonsByName(String name);
    void deletePerson(String id);
    boolean isEmailUnique(String email);
    Optional<Person> findByPhoneNumber(String phoneNumber);
}