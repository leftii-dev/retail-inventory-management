package dev.austinbarnes.retailinventorymanagement.location.dto.hours;

import dev.austinbarnes.retailinventorymanagement.location.dto.LocationResponseDTO;

import java.time.LocalTime;
import java.util.UUID;

public interface LocationHoursResponseDTO {
    UUID id();

    short dayOfWeek();

    LocalTime openTime();

    LocalTime closeTime();

    LocationResponseDTO location();
}
