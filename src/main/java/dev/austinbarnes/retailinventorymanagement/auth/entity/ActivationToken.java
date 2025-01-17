package dev.austinbarnes.retailinventorymanagement.auth.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
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
@ToString(callSuper = true)
public class ActivationToken extends BaseEntity {

    @Column(nullable = false, updatable = false, name = "user_id")
    private UUID userId;

    @Column(name = "expire_at")
    private Instant expireAt;

    @PrePersist
    private void setExpiration(){
        this.expireAt = Instant.now().plus(24, ChronoUnit.HOURS);
    }
}
