package dev.austinbarnes.retailinventorymanagement.location.dto;


import dev.austinbarnes.retailinventorymanagement.location.dto.type.LocationTypeResponseDTO;

import java.util.UUID;

public interface LocationResponseDTO {
    UUID id();

    String name();

    LocationTypeResponseDTO locationType();
}
