package dev.austinbarnes.retailinventorymanagement.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "activation_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false, name = "user_id")
    private UUID userId;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "expire_at")
    private Instant expireAt;

    @PrePersist
    private void setTimestamps(){
        Instant now = Instant.now();
        this.createdAt = now;
        this.expireAt = now.plus(24, ChronoUnit.HOURS);
    }
}
