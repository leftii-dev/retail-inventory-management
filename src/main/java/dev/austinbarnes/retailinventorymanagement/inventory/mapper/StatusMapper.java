package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.status.StatusResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Status;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * StatusMapper is an interface that defines the mapping between Status entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert StatusRequestDTO to Status entity and
 * to convert Status entity to different types of StatusResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class)
public interface StatusMapper {
    /**
     * Converts StatusRequestDTO to Status entity.
     *
     * @param statusRequestDTO the StatusRequestDTO to convert
     * @return the converted Status entity
     */
    Status toEntity(StatusRequestDTO statusRequestDTO);

    /**
     * Converts Status entity to StatusResponseBasicDTO.
     *
     * @param status the Status entity to convert
     * @return the converted StatusResponseBasicDTO
     */
    @Named("basicStatus")
    StatusResponseBasicDTO toBasicDTO(Status status);

    /**
     * Converts Status entity to StatusResponseDetailDTO.
     *
     * @param status the Status entity to convert
     * @return the converted StatusResponseDetailDTO
     */
    @Named("detailStatus")
    StatusResponseDetailDTO toDetailDTO(Status status);

    /**
     * Updates an existing Status entity with the values from the StatusRequestDTO.
     *
     * @param statusRequestDTO the StatusRequestDTO containing the new values
     * @param status           the Status entity to update
     */
    void updateEntityFromRequest(StatusRequestDTO statusRequestDTO, @MappingTarget Status status);
}
