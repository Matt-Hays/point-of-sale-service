package com.courseproject.pointofsaleservice.models;

import com.courseproject.pointofsaleservice.models.enums.EmployeePosition;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "transactions")
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String country;

    @Enumerated(EnumType.STRING)
    private EmployeePosition employeePosition;

    @OneToMany(mappedBy = "employee")
    @JsonManagedReference("employee-transactions")
    private Set<Transaction> transactions = new HashSet<>();
}
