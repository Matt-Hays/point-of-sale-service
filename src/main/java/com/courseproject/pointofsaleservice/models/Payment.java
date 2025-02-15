package com.courseproject.pointofsaleservice.models;

import com.courseproject.pointofsaleservice.models.enums.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    private Long version;

    private LocalDateTime completedAt;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentType paymentType;
}
