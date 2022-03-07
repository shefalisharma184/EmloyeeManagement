package com.springBootCurdOperation.service;

import com.springBootCurdOperation.exception.DepartmentNotFoundException;
import com.springBootCurdOperation.model.Employee;
import com.springBootCurdOperation.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeesServiceTest {

    private final List<Employee> employeeList = new ArrayList<>();

    @MockBean
    private Employee employee;

    @MockBean
    EmployeeRepository repository;

    @Autowired
    EmployeesService service;

    @BeforeEach
    void setUp() {
        employeeList.add(new Employee(1, "Shefali",  "Design",30000.00));
        employeeList.add(new Employee(2, "Ayushi", "Development", 40000.5));
        employeeList.add(new Employee(3, "Sneha", "Development", 30330.00));
        employeeList.add(new Employee(4, "Sonali", "Testing", 80330.00));

        employee = new Employee(2, "Ayushi", "Development", 40000.5);
    }

    @AfterEach
    void tearDown() {
        employeeList.clear();
    }

    @Test
    void getAllEmployees() {
        when(repository.findAll()).thenReturn(employeeList);
        assertEquals(4, service.getAllEmployees().size());
    }

    @Test
    void getEmployeeById() {
        when(repository.findById(3)).thenReturn(Optional.ofNullable(employee));
        assertEquals(employee, service.getEmployeeById(3));
    }

    @Test
    void addEmployeeData() {
        when(repository.save(employee)).thenReturn(employee);
        assertEquals(employee, service.addEmployeeData(employee));
    }

    @Test
    void updateEmployee() {
        when(repository.findById(4)).thenReturn(Optional.ofNullable(employee));
        when(repository.save(employee)).thenReturn(employee);
        assertEquals(employee, service.updateEmployee(4, employee));
    }

    @Test
    void deleteEmployeeById() {
        when(repository.findById(4)).thenReturn(Optional.ofNullable(employee));
        service.deleteEmployeeById(4);
        verify(repository, times(1)).deleteById(4);
    }

    @Test
    void updateEmployeeSalaryById() {
        when(repository.findById(3)).thenReturn(Optional.ofNullable(employee));
        when(repository.save(employee)).thenReturn(employee);
        assertEquals(employee, service.updateEmployeeSalaryById(3, 50000.04));
    }

    @Test
    void getEmployeeSalaryById() {
        when(repository.findById(2)).thenReturn(Optional.ofNullable(employee));
        assertEquals(40000.5, service.getEmployeeSalaryById(2));
    }

    @Test
    void getEmployeesByDepartment() {
        try {
            when(repository.getEmployeesByDepartment("Test").isEmpty()).thenThrow(new DepartmentNotFoundException("Test"));
            service.getEmployeesByDepartment("Test");
        }catch (Exception exception) {
            assertTrue(exception instanceof DepartmentNotFoundException);
        }
    }
}