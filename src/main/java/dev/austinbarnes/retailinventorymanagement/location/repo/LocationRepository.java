package dev.austinbarnes.retailinventorymanagement.location.repo;

import dev.austinbarnes.retailinventorymanagement.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
}
