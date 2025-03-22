package com.courseproject.pointofsaleservice.services.utils;

import com.courseproject.pointofsaleservice.configuration.FeignClientConfiguration;
import com.courseproject.pointofsaleservice.models.Customer;
import com.courseproject.pointofsaleservice.models.dto.LoyaltyCustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        value = "loyalty-program-service",
        configuration = FeignClientConfiguration.class
)
public interface LoyaltyFeignClient {
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/v1/loyalty/{id}/credit/{points}",
            consumes = "application/json"
    )
    ResponseEntity<Void> creditLoyalty(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @PathVariable Double points
    );

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/v1/customer",
            consumes = "application/json"
    )
    ResponseEntity<Customer> createCustomer(
            @RequestHeader("Authorization") String token,
            @RequestBody LoyaltyCustomerDTO customer
    );
}
