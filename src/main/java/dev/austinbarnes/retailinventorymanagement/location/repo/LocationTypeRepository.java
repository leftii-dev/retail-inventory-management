package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationTypeRepository extends JpaRepository<LocationType, UUID> {
}
