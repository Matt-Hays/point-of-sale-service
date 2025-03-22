package com.courseproject.pointofsaleservice.services.utils;

import com.courseproject.pointofsaleservice.configuration.FeignClientConfiguration;
import com.courseproject.pointofsaleservice.models.dto.CustomerDTO;
import com.courseproject.pointofsaleservice.models.dto.LoyaltyAccountDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "loyalty-program-service", configuration = FeignClientConfiguration.class)
public interface LoyaltyFeignClient {
        @RequestMapping(method = RequestMethod.POST, value = "/v1/loyalty/{id}/credit/{points}", consumes = "application/json")
        ResponseEntity<Void> creditLoyalty(
                        @RequestHeader("Authorization") String token,
                        @PathVariable Long id, @PathVariable Double points);

        @RequestMapping(method = RequestMethod.POST, value = "/v1/customer", consumes = "application/json")
        ResponseEntity<Void> createLoyaltyCustomer(
                        @RequestHeader("Authorization") String token,
                        @RequestBody CustomerDTO customer);

        @RequestMapping(method = RequestMethod.POST, value = "v1/loyalty", consumes = "application/json")
        ResponseEntity<Void> createLoyaltyAccount(
                        @RequestHeader("Authorization") String token,
                        @RequestBody LoyaltyAccountDTO loyaltyAccountDTO);
}
