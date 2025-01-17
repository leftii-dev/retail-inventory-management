package dev.austinbarnes.retailinventorymanagement.entitycode.entity;

import dev.austinbarnes.retailinventorymanagement.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "code")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class CodeEntity extends BaseEntity {
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "code")
    private int code;
}
