package com.springBootCurdOperation.controller;

import com.springBootCurdOperation.model.Employee;
import com.springBootCurdOperation.request.ApiResponse;
import com.springBootCurdOperation.request.EmpDTO;
import com.springBootCurdOperation.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    EmployeesService employeesService;

    @GetMapping("/")
    public String getWelcomeMessage() {
        return "Welcome to Employee Management System";
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmpDTO> getEmployeeById(@PathVariable("id") int id) {
        Employee employee = employeesService.getEmployeeById(id);
        EmpDTO employeeResponse = modelMapper.map(employee, EmpDTO.class);
        return ResponseEntity.ok(employeeResponse);
    }

    @GetMapping("/employees")
    public List<EmpDTO> getAllEmployees() {
        return employeesService.getAllEmployees()
                .stream()
                .map(employee -> modelMapper.map(employee, EmpDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmpDTO> addEmployeeData(@RequestBody EmpDTO empDTO) {
        Employee empRequest = modelMapper.map(empDTO, Employee.class);
        Employee employee = employeesService.addEmployeeData(empRequest);
        EmpDTO employeeResponse = modelMapper.map(employee, EmpDTO.class);
        return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<EmpDTO> updateEmployeeById(@RequestBody EmpDTO empDTO, @PathVariable int id) {
        Employee empRequest = modelMapper.map(empDTO, Employee.class);
        Employee employee = employeesService.updateEmployee(id, empRequest);
        EmpDTO employeeResponse = modelMapper.map(employee, EmpDTO.class);
        return ResponseEntity.ok().body(employeeResponse);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<ApiResponse> deleteEmployeeById(@PathVariable int id) {
        employeesService.deleteEmployeeById(id);
        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Employee Deleted Successfully", HttpStatus.OK);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/employee/{id}/{salary}")
    public ResponseEntity<EmpDTO> updateEmployeeSalaryById(@PathVariable(name = "id")int id,
                                                           @PathVariable(name = "salary") double salary) {
        Employee employee = employeesService.updateEmployeeSalaryById(id, salary);
        EmpDTO employeeResponse = modelMapper.map(employee, EmpDTO.class);
        return ResponseEntity.ok().body(employeeResponse);
    }

    @GetMapping("/getSalary/{id}")
    public ResponseEntity<Double> getEmployeeSalaryById(@PathVariable int id) {
        double salary = employeesService.getEmployeeSalaryById(id);

        return ResponseEntity.ok().body(salary);
    }

    @GetMapping("/employees/{department}")
    public List<EmpDTO> getAllEmployeesByDepartment(@PathVariable String department) {
        List<Employee> employeesByDepartment = employeesService.getEmployeesByDepartment(department);
        return employeesByDepartment.stream()
                .map(employee -> modelMapper.map(employee, EmpDTO.class))
                .sorted(Comparator.comparing(EmpDTO::getId))
                .collect(Collectors.toList());
    }
}
