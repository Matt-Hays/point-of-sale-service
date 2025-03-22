package com.courseproject.pointofsaleservice.models.dto;

public record LoyaltyAccountDTO(
        Double balance,
        CustomerDTO customerDTO) {
}
