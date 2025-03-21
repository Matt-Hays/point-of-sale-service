package com.courseproject.pointofsaleservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "transactionLineItems")
@ToString
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    @ManyToOne
    @JsonBackReference("customer-transactions")
    private Customer customer;

    @ManyToOne
    @JsonBackReference("register-transactions")
    private Register register;

    @ManyToOne
    @JsonBackReference("employee-transactions")
    private Employee employee;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    @JsonManagedReference("transaction-transactionLineItems")
    @ToString.Exclude
    private Set<TransactionLineItem> transactionLineItems = new HashSet<>();
}
