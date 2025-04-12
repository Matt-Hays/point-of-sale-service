package com.courseproject.pointofsaleservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "transactions")
@ToString
@RedisHash("register")
public class Register {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @NotBlank
    private String location;

    @CreationTimestamp
    private LocalDateTime registrationDate;

    @OneToMany(mappedBy = "register")
    @JsonManagedReference("register-transactions")
    @ToString.Exclude
    private Set<Transaction> transactions = new HashSet<>();
}
