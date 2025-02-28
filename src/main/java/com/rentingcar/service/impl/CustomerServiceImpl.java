package com.rentingcar.service.impl;

import com.rentingcar.model.Customer;
import com.rentingcar.repository.CustomerRepository;
import com.rentingcar.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    
    private final CustomerRepository customerRepository;
    
    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    @Override
    public Optional<Customer> getCustomerById(Integer id) {
        return customerRepository.findById(id);
    }
    
    @Override
    public Optional<Customer> getCustomerByPersonId(String personId) {
        return customerRepository.findByPersonId(personId);
    }
    
    @Override
    public Optional<Customer> getCustomerByLicenseNumber(String licenseNumber) {
        return customerRepository.findByLicenseNumber(licenseNumber);
    }
    
    @Override
    public List<Customer> getCustomersByName(String name) {
        return customerRepository.findByPersonName(name);
    }
    
    @Override
    public List<Customer> findCustomersWithExpiredLicenses() {
        return customerRepository.findByLicenseExpireDateBefore(LocalDate.now());
    }
    
    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findByPersonEmail(email);
    }
    
    @Override
    @Transactional
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
    
    @Override
    public boolean isLicenseValid(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.map(c -> c.getLicenseExpireDate().isAfter(LocalDate.now()))
                .orElse(false);
    }
}