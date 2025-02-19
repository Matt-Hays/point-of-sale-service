package com.courseproject.pointofsaleservice.services;

import com.courseproject.pointofsaleservice.models.Customer;
import com.courseproject.pointofsaleservice.repositories.CustomerRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.lang.Long;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
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
