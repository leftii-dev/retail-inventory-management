package dev.austinbarnes.retailinventorymanagement.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

    @MapperConfig(
            componentModel = "spring",
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            unmappedSourcePolicy = ReportingPolicy.IGNORE,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
public interface GlobalMapperConfig {

}
