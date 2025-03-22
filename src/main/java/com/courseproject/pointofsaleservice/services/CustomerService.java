package com.courseproject.pointofsaleservice.services;

import com.courseproject.pointofsaleservice.models.Customer;
import com.courseproject.pointofsaleservice.models.dto.LoyaltyCustomerDTO;
import com.courseproject.pointofsaleservice.repositories.CustomerRepository;
import com.courseproject.pointofsaleservice.services.utils.LoyaltyFeignClient;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.lang.Long;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final LoyaltyFeignClient loyaltyFeignClient;

    public Customer saveCustomer(String token, Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        try {
            ResponseEntity<Customer> response = loyaltyFeignClient.createCustomer(token, new LoyaltyCustomerDTO(savedCustomer.getId()));
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to save customer " + customer.getId());
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Error calling loyalty service.", e);
        }
        return savedCustomer;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) throws EntityNotFoundException {
        return customerRepository.findById(id).orElseThrow(EntityExistsException::new);
    }

    public Customer updateCustomer(Long id, Customer customer) throws EntityNotFoundException {
        Customer oldCustomer = getCustomerById(id);
        if (customer.getFirstName() != null) oldCustomer.setFirstName(customer.getFirstName());
        if (customer.getLastName() != null) oldCustomer.setLastName(customer.getLastName());
        if (customer.getPhoneNumber() != null) oldCustomer.setPhoneNumber(customer.getPhoneNumber());
        if (customer.getEmail() != null) oldCustomer.setEmail(customer.getEmail());
        if (customer.getAddress() != null) oldCustomer.setAddress(customer.getAddress());
        if (customer.getCity() != null) oldCustomer.setCity(customer.getCity());
        if (customer.getState() != null) oldCustomer.setState(customer.getState());
        if (customer.getCountry() != null) oldCustomer.setCountry(customer.getCountry());
        if (customer.getZipCode() != null) oldCustomer.setZipCode(customer.getZipCode());
        return customerRepository.save(oldCustomer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
