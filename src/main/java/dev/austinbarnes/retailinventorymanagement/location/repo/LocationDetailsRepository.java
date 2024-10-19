package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.LocationDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationDetailsRepository extends JpaRepository<LocationDetails, UUID> {
}
