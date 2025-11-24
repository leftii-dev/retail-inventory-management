package dev.austinbarnes.retailinventorymanagement.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * GlobalMapperConfig is a configuration interface for MapStruct.
 * It defines the global settings for all mappers in the application.
 * This includes the component model, unmapped target policy, unmapped source policy,
 * null value property mapping strategy, and null value check strategy.
 */
@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface GlobalMapperConfig {
    // This interface is intentionally left empty.
    // It serves as a configuration holder for MapStruct.
}
