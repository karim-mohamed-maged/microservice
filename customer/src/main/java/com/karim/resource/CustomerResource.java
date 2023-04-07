package com.karim.resource;

import com.karim.model.Customer;
import com.karim.model.dto.CustomerRequest;
import com.karim.service.CustomerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerResource {
    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "customerService" , fallbackMethod = "customerIsFraud")
    public void createNewCustomer(@Valid @RequestBody CustomerRequest customer ){
        log.info("new customer registeration {}" , customer);
        customerService.createNewCustomer(customer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> findAllCustomers(){
        log.info("get all customers");
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer findById(@PathVariable Long id){
        log.info("get all customers");
        return customerService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCustomerData(@Valid @RequestBody Customer customer){
        log.info("update cutomer data {}" , customer);
        customerService.updateCustomerData(customer);
    }


    public void customerIsFraud(Exception e){
        System.out.println("sorry fraud service not avaliable now but your request we complete successfully!");
    }


}
