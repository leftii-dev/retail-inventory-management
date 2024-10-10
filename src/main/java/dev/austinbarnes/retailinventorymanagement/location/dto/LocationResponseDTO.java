package dev.austinbarnes.retailinventorymanagement.location.dto;


import java.util.UUID;

public interface LocationResponseDTO {
    UUID id();

    String name();

    LocationTypeDTO locationType();
}
