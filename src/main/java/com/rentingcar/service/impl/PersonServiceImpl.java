package com.rentingcar.service.impl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentingcar.model.*;
import com.rentingcar.service.PersonService;
import com.rentingcar.repository.PersonRepository;

import jakarta.transaction.Transactional;
@Service
public class PersonServiceImpl implements PersonService{
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository){
        this.personRepository= personRepository;
    }

    @Override
    @Transactional
    public Person savePerson(Person person){
        if(person.getEmail() != null && personRepository.existsByEmail(person.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }
        return personRepository.save(person);
    }
    
    @Override
    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> getPersonById(String id){
        return personRepository.findById(id);
    }

    @Override
    public Optional<Person> getPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }
    
    @Override
    public List<Person> findPersonsByName(String name) {
        return personRepository.findByLastNameContainingOrFirstNameContaining(name, name);
    }
    
    @Override
    @Transactional
    public void deletePerson(String id) {
        personRepository.deleteById(id);
    }
    
    @Override
    public boolean isEmailUnique(String email) {
        return !personRepository.existsByEmail(email);
    }

    @Override
    public Optional<Person> findByPhoneNumber(String phoneNumber) {
        return personRepository.findByPhoneNumber(phoneNumber);
    }
}
