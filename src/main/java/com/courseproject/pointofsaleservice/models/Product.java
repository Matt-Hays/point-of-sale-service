package com.courseproject.pointofsaleservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.redis.core.RedisHash;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "transactionLineItems")
@ToString
@RedisHash("product")
public class Product {
    @Id
    @NotNull
    @Column(unique = true, nullable = false)
    private Long id;

    @Version
    private Long version;

    private String sku;

    @NotBlank
    private String description;

    @NotNull
    private Double price;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference("product-transactionLineItems")
    @ToString.Exclude
    private Set<TransactionLineItem> transactionLineItems = new HashSet<>();
}
