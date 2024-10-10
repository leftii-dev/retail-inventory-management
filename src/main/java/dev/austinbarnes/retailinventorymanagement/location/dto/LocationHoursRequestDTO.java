package dev.austinbarnes.retailinventorymanagement.location.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalTime;
import java.util.UUID;

public record LocationHoursRequestDTO (
        @Min(value = 0, message = "Day of week value cannot be negative (0-6)") @Max(value = 7, message = "Day of week value cannot be over 6 (0-6)") short dayOfWeek,
        LocalTime openTime,
        LocalTime closeTime,
        UUID locationID
){
}
