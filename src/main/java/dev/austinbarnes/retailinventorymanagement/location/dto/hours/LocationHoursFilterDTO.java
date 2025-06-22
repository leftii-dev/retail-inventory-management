package dev.austinbarnes.retailinventorymanagement.location.dto.hours;

import dev.austinbarnes.retailinventorymanagement.common.BaseFilterDTO;
import dev.austinbarnes.retailinventorymanagement.common.FilterDTO;

import java.util.UUID;

public record LocationHoursFilterDTO(
        BaseFilterDTO baseFilterDTO,
        UUID location
) implements FilterDTO {}