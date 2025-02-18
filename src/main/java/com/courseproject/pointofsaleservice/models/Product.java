package com.courseproject.pointofsaleservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Product {
    @Id
    @NotNull
    @Column(unique = true, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @NotBlank
    private String sku;

    @NotBlank
    private String description;

    @OneToMany(mappedBy = "product")
    private Set<TransactionLineItem> transactionLineItems = new HashSet<>();
}
