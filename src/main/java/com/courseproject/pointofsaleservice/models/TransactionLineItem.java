package com.courseproject.pointofsaleservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TransactionLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @NotNull
    @Min(0)
    private Double quantity;

    @ManyToOne
    @JsonBackReference("product-transactionLineItems")
    private Product product;

    @ManyToOne
    @NotNull
    @JsonBackReference("transaction-transactionLineItems")
    private Transaction transaction;
}
