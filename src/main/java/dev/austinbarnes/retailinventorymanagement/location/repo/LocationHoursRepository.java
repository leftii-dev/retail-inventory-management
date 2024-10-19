package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.LocationHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationHoursRepository extends JpaRepository<LocationHours, UUID> {
}
