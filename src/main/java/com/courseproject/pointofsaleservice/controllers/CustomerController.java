package com.courseproject.pointofsaleservice.controllers;

import com.courseproject.pointofsaleservice.models.Customer;
import com.courseproject.pointofsaleservice.services.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.lang.Long;

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
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody @Valid Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PatchMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody @Valid Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
