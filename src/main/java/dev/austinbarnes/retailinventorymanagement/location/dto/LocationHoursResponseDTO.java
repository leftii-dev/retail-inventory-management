package dev.austinbarnes.retailinventorymanagement.location.dto;

import java.time.LocalTime;
import java.util.UUID;

public interface LocationHoursResponseDTO {
    UUID id();

    short dayOfWeek();

    LocalTime openTime();

    LocalTime closeTime();

    LocationResponseDTO location();
}
