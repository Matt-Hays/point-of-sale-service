package com.courseproject.pointofsaleservice.services.utils;

import com.courseproject.pointofsaleservice.configuration.FeignClientConfiguration;
import com.courseproject.pointofsaleservice.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        value = "inventory-service",
        configuration = FeignClientConfiguration.class
)
public interface InventoryFeignClient {
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/v1/product",
            consumes = "application/json"
    )
    ResponseEntity<Product> getProduct(@PathVariable Long id);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/v1/product/{id}/sale/{qty}",
            consumes = "application/json"
    )
    ResponseEntity<Product> saleProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long id,
            @PathVariable("qty") Double qty
    );
}
