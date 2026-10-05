package com.source.EMS.controller;


import com.source.EMS.model.Employee;
import com.source.EMS.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController
{
    @Autowired
    public EmployeeRepository employeeRepository;

    // get all employees
    @GetMapping
    public List<Employee> getAllEmployees()
    {
        return employeeRepository.findAll();
    }

    // create employee
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee)
    {
        return employeeRepository.save(employee);
    }

    // get employee by id
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id)
    {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    // update employee
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        return employeeRepository.save(employee);
    }

    // delete employee
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

}
