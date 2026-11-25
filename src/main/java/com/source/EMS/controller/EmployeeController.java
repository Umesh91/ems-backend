package com.source.EMS.controller;

import com.source.EMS.exception.ResourceNotFoundException;
import com.source.EMS.model.Employee;
import com.source.EMS.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Employee Management", description = "Endpoints for listing, creating, updating, and deleting employees")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController
{
    @Autowired
    public EmployeeRepository employeeRepository;

    @GetMapping
    @Operation(summary = "Get all employees", description = "Returns every employee stored in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Employee.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<Employee> getAllEmployees()
    {
        return employeeRepository.findAll();
    }

    @PostMapping
    @Operation(summary = "Create employee", description = "Creates a new employee using the provided request payload.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Employee data to create", required = true,
            content = @Content(schema = @Schema(implementation = Employee.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "400", description = "Invalid employee payload"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Employee createEmployee(@RequestBody Employee employee)
    {
        return employeeRepository.save(employee);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by id", description = "Fetches a single employee by their unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found and returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid employee id"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Employee getEmployeeById(@PathVariable @Parameter(description = "Employee id", required = true, example = "1") Long id)
    {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employee", description = "Updates the details for an existing employee identified by the id.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated employee details", required = true,
            content = @Content(schema = @Schema(implementation = Employee.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "400", description = "Invalid employee payload or id"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Employee updateEmployee(@PathVariable @Parameter(description = "Employee id", required = true, example = "1") Long id,
                                  @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        return employeeRepository.save(employee);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee", description = "Deletes the employee matching the supplied identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid employee id"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void deleteEmployee(@PathVariable @Parameter(description = "Employee id", required = true, example = "1") Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

}
