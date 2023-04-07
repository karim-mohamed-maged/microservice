package com.karim.service.impl;

import com.karim.config.Constants;
import com.karim.model.Customer;
import com.karim.model.dto.CustomerRequest;
import com.karim.repostiory.CustomerRepository;
import com.karim.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    @Override
    public void createNewCustomer(CustomerRequest request) {
        Customer customer = this.findByEmail(request.getEmail());
        if (!this.isValidEmail(request.getEmail()))
            throw new RuntimeException(Constants.EMAIL_NOT_VALID);
        if (Objects.nonNull(customer))
            throw new RuntimeException(Constants.EMAIL_EXIST_MSG);
        customer = this.createCustomerObject(request);
        customer = customerRepository.saveAndFlush(customer);
        Boolean isFraud = restTemplate.getForEntity(Constants.FRAUD_URL+customer.getId(), Boolean.class).getBody();
        if (isFraud)
            throw new IllegalStateException("fraudster");
    }
    @Override
    public void updateCustomerData(Customer customer) {
        Customer customerById = this.findById(customer.getId());
        if (Objects.equals(customerById.getEmail() , customer.getEmail())) {
            customerRepository.saveAndFlush(customer);
        }else{
            if (!this.isValidEmail(customer.getEmail()))
                throw new RuntimeException(Constants.EMAIL_NOT_VALID);
            Customer findByEmail = this.findByEmail(customer.getEmail());
            if (Objects.nonNull(findByEmail))
                throw new RuntimeException(Constants.EMAIL_EXIST_MSG);
            customerRepository.saveAndFlush(customer);
        }
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Constants.CUSTOMER_NOT_FOUND));
    }

    @Override
    public Boolean isValidEmail(String email) {
        String regex = Constants.EMAIL_REGEX;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email).orElse(null);
    }
    @Override
    public Customer createCustomerObject(CustomerRequest request) {
        return Customer
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
    }
}
