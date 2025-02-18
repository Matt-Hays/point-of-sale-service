package com.courseproject.pointofsaleservice.controllers;

import com.courseproject.pointofsaleservice.models.Customer;
import com.courseproject.pointofsaleservice.services.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable UUID id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody @Valid Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);
        return restTemplate.postForObject("http://localhost:8081/customer", savedCustomer, Customer.class);
    }

    @PatchMapping("/{id}")
    public Customer updateCustomer(@PathVariable UUID id, @RequestBody @Valid Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
    }
}
