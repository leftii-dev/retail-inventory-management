package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Transfer;
import dev.austinbarnes.retailinventorymanagement.location.service.LocationService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * TransferMapper is an interface that defines the mapping between Transfer entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert TransferRequestDTO to Transfer entity and
 * to convert Transfer entity to different types of TransferResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class, uses = LocationService.class)
public interface TransferMapper {

    /**
     * Converts TransferRequestDTO to Transfer entity.
     *
     * @param transferRequestDTO the TransferRequestDTO to convert
     * @return the converted Transfer entity
     */
    @Mapping(target = "locationTo", expression = "java(locationService.getLocationEntityById(transferRequestDTO.locationTo()))")
    @Mapping(target = "locationFrom", expression = "java(locationService.getLocationEntityById(transferRequestDTO.locationFrom()))")
    Transfer toEntity(TransferRequestDTO transferRequestDTO);

    /**
     * Converts Transfer entity to TransferResponseBasicDTO.
     *
     * @param transfer the Transfer entity to convert
     * @return the converted TransferResponseBasicDTO
     */
    @Mapping(target = "locationToID", source = "locationTo.id")
    @Mapping(target = "locationFromID", source = "locationFrom.id")
    @Named("basicTransfer")
    TransferResponseBasicDTO toBasicDTO(Transfer transfer);

    /**
     * Converts Transfer entity to TransferResponseDetailDTO.
     *
     * @param transfer the Transfer entity to convert
     * @return the converted TransferResponseDetailDTO
     */
    @Mapping(target = "locationToID", source = "locationTo.id")
    @Mapping(target = "locationFromID", source = "locationFrom.id")
    @Named("detailTransfer")
    TransferResponseDetailDTO toDetailDTO(Transfer transfer);

    /**
     * Updates an existing Transfer entity with the values from the TransferRequestDTO.
     *
     * @param transferRequestDTO the TransferRequestDTO containing the new values
     * @param transfer           the Transfer entity to update
     */
    void updateEntityFromRequest(TransferRequestDTO transferRequestDTO, @MappingTarget Transfer transfer);
}
