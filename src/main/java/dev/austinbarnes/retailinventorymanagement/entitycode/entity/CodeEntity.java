package dev.austinbarnes.retailinventorymanagement.entitycode.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "code")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "code")
    private int code;
}
