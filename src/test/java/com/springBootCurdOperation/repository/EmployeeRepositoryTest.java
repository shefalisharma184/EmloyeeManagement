package com.springBootCurdOperation.repository;

import com.springBootCurdOperation.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmployeeRepositoryTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private final List<Employee> employeeList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        employeeList.add(new Employee(1, "Shefali",  "Design",30000.00));
        employeeList.add(new Employee(2, "Ayushi", "Development", 40000.5));
        employeeList.add(new Employee(3, "Sneha", "Development", 30330.00));
    }

    @AfterEach
    void tearDown() {
        employeeList.clear();
    }

    @Test
    void getEmployeesByDepartment() {
        Mockito.when(employeeRepository.getEmployeesByDepartment("Development")).thenReturn(employeeList);
        List<Employee> employees = employeeRepository.getEmployeesByDepartment("Development");
        Assertions.assertEquals(employeeList,employees);
    }
}