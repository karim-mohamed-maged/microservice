package com.karim.service;

import com.karim.model.Customer;
import com.karim.model.dto.CustomerRequest;

import java.util.List;

public interface CustomerService {
    void createNewCustomer(CustomerRequest request);

    Customer createCustomerObject(CustomerRequest request);

    Customer findByEmail(String email);

    Boolean isValidEmail(String email);

    void updateCustomerData(Customer customer);

    List<Customer> findAll();

    Customer findById(Long id);
}
