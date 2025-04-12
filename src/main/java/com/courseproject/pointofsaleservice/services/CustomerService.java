package com.courseproject.pointofsaleservice.services;

import com.courseproject.pointofsaleservice.models.Customer;
import com.courseproject.pointofsaleservice.models.dto.CustomerDTO;
import com.courseproject.pointofsaleservice.models.dto.LoyaltyAccountDTO;
import com.courseproject.pointofsaleservice.repositories.CustomerRepository;
import com.courseproject.pointofsaleservice.services.utils.LoyaltyFeignClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService {
    private final LoyaltyFeignClient loyaltyFeignClient;
    private final RedisTemplate<Long, Customer> redisTemplate;
    private CustomerRepository customerRepository;

    @RateLimiter(name = "CustomerService::saveCustomer-ratelimiter")
    @Retry(name = "CustomerService::saveCustomer-retry")
    @Bulkhead(name = "CustomerService::saveCustomer-bulkhead")
    @CircuitBreaker(name = "CustomerService::saveCustomer-circuitbreaker")
    public Customer saveCustomer(String token, Customer customer) {
        Customer newCustomer = customerRepository.save(customer);
        CustomerDTO customerDTO = new CustomerDTO(newCustomer.getId());
        LoyaltyAccountDTO loyaltyAccountDTO = new LoyaltyAccountDTO(Double.valueOf(0), customerDTO);

        try {
            ResponseEntity<Void> response = loyaltyFeignClient.createLoyaltyCustomer(token, customerDTO);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to create new loyalty customer");
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Error calling loyalty service.", e);
        }

        try {
            ResponseEntity<Void> response = loyaltyFeignClient.createLoyaltyAccount(token, loyaltyAccountDTO);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to create new loyalty account");
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Error calling loyalty service.", e);
        }

        return newCustomer;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) throws EntityNotFoundException {
        Customer c = redisTemplate.opsForValue().get(id);
        if (c == null) {
            log.info("Customer {} not found in Redis", id);
            c = customerRepository.findById(id).orElseThrow(EntityExistsException::new);
            redisTemplate.opsForValue().set(id, c);
        }
        return c;
    }

    public Customer updateCustomer(Long id, Customer customer) throws EntityNotFoundException {
        Customer oldCustomer = getCustomerById(id);
        if (customer.getFirstName() != null)
            oldCustomer.setFirstName(customer.getFirstName());
        if (customer.getLastName() != null)
            oldCustomer.setLastName(customer.getLastName());
        if (customer.getPhoneNumber() != null)
            oldCustomer.setPhoneNumber(customer.getPhoneNumber());
        if (customer.getEmail() != null)
            oldCustomer.setEmail(customer.getEmail());
        if (customer.getAddress() != null)
            oldCustomer.setAddress(customer.getAddress());
        if (customer.getCity() != null)
            oldCustomer.setCity(customer.getCity());
        if (customer.getState() != null)
            oldCustomer.setState(customer.getState());
        if (customer.getCountry() != null)
            oldCustomer.setCountry(customer.getCountry());
        if (customer.getZipCode() != null)
            oldCustomer.setZipCode(customer.getZipCode());
        return customerRepository.save(oldCustomer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
