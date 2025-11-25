package dev.austinbarnes.retailinventorymanagement.inventory.mapper;

import dev.austinbarnes.retailinventorymanagement.config.GlobalMapperConfig;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferRequestDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferResponseBasicDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.dto.transfer.TransferResponseDetailDTO;
import dev.austinbarnes.retailinventorymanagement.inventory.entity.Transfer;
import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import dev.austinbarnes.retailinventorymanagement.location.repo.LocationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * TransferMapper is an interface that defines the mapping between Transfer entity and its DTOs.
 * <p>
 * It uses MapStruct to generate the implementation of the mapping methods.
 * <p>
 * The interface includes methods to convert TransferRequestDTO to Transfer entity and
 * to convert Transfer entity to different types of TransferResponseDTOs.
 */
@Mapper(config = GlobalMapperConfig.class)
public abstract class TransferMapper {
    @Autowired
    protected LocationRepository locationRepository;
    /**
     * Converts TransferRequestDTO to Transfer entity.
     *
     * @param transferRequestDTO the TransferRequestDTO to convert
     * @return the converted Transfer entity
     */
    @Mapping(target = "locationTo", source = "locationToID")
    @Mapping(target = "locationFrom", source = "locationFromID")
    public abstract Transfer toEntity(TransferRequestDTO transferRequestDTO);

    /**
     * Converts Transfer entity to TransferResponseBasicDTO.
     *
     * @param transfer the Transfer entity to convert
     * @return the converted TransferResponseBasicDTO
     */
    @Mapping(target = "locationToID", source = "locationTo.id")
    @Mapping(target = "locationFromID", source = "locationFrom.id")
    @Named("basicTransfer")
    public abstract TransferResponseBasicDTO toBasicDTO(Transfer transfer);

    /**
     * Converts Transfer entity to TransferResponseDetailDTO.
     *
     * @param transfer the Transfer entity to convert
     * @return the converted TransferResponseDetailDTO
     */
    @Mapping(target = "locationToID", source = "locationTo.id")
    @Mapping(target = "locationFromID", source = "locationFrom.id")
    @Named("detailTransfer")
    public abstract TransferResponseDetailDTO toDetailDTO(Transfer transfer);

    /**
     * Updates an existing Transfer entity with the values from the TransferRequestDTO.
     *
     * @param transferRequestDTO the TransferRequestDTO containing the new values
     * @param transfer           the Transfer entity to update
     */
    @Mapping(target = "locationTo", source = "locationToID")
    @Mapping(target = "locationFrom", source = "locationFromID")
    public abstract void updateEntityFromRequest(TransferRequestDTO transferRequestDTO, @MappingTarget Transfer transfer);

    /**
     * Custom Resolvers
     */
    protected Location resolveLocation(UUID id) {
        return id == null ? null : locationRepository.findById(id).orElse(null);
    }
}
