package dev.austinbarnes.retailinventorymanagement.employee.controller;

import dev.austinbarnes.retailinventorymanagement.employee.service.EmployeeHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/employee-manager")
@RequiredArgsConstructor
public class EmployeeHierarchyController {
    private final EmployeeHierarchyService employeeHierarchyService;
}
