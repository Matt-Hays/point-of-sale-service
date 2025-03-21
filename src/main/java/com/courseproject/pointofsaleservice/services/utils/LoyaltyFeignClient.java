package com.courseproject.pointofsaleservice.services.utils;

import com.courseproject.pointofsaleservice.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        value = "loyalty-program",
        configuration = FeignClientConfiguration.class
)
public interface LoyaltyFeignClient {
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/v1/loyalty/{id}/credit/{points}",
            consumes = "application/json"
    )
    ResponseEntity<Void> creditLoyalty(@PathVariable Long id, @PathVariable Double points);
}
