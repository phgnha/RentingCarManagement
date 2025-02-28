package com.rentingcar.service;

import com.rentingcar.model.Customer;
//import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Integer id);
    Optional<Customer> getCustomerByPersonId(String personId);
    Optional<Customer> getCustomerByLicenseNumber(String licenseNumber);
    List<Customer> getCustomersByName(String name);
    List<Customer> findCustomersWithExpiredLicenses();
    Optional<Customer> findCustomerByEmail(String email);
    void deleteCustomer(Integer id);
    boolean isLicenseValid(Integer customerId);
}