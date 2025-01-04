package dev.austinbarnes.retailinventorymanagement.employee.service;

import dev.austinbarnes.retailinventorymanagement.employee.repo.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PreAuthorize( "hasAnyRole('MANAGER', 'ADMIN')")
public class PermissionService {
    private final PermissionRepository permissionRepository;

    //Create
    //Update
    //GetByID
    //GetAll
    //Delete
}
