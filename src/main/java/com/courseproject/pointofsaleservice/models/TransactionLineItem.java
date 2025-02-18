package com.courseproject.pointofsaleservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TransactionLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    private Long version;

    @NotNull
    @Min(0)
    private Double quantity;

    @ManyToOne
    private Product product;

    @ManyToOne
    @NotNull
    private Transaction transaction;
}
