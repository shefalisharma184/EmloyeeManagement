package com.springBootCurdOperation.repository;

import com.springBootCurdOperation.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT employee FROM Employee employee WHERE employee.department = ?1 ORDER BY ID ASC")
    List<Employee> getEmployeesByDepartment(String department);
}
