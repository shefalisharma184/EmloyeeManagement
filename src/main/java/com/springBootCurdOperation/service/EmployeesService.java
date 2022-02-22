package com.springBootCurdOperation.service;

import com.springBootCurdOperation.exception.DepartmentNotFoundException;
import com.springBootCurdOperation.exception.EmployeeNotFoundException;
import com.springBootCurdOperation.model.Employee;
import com.springBootCurdOperation.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeesService {
    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }
    public Employee getEmployeeById(int id) {
        return repository.findById(id).orElseThrow(()-> new EmployeeNotFoundException(id));
    }

    public Employee addEmployeeData(Employee employeeData) {
        return repository.save(employeeData);
    }

    public Employee updateEmployee(int id, Employee employeeData) {
        repository.findById(id).orElseThrow(()-> new EmployeeNotFoundException(id));
        employeeData.setId(id);
        return repository.save(employeeData);
    }

    public void deleteEmployeeById(int id) {
        repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        repository.deleteById(id);
    }

    public Employee updateEmployeeSalaryById(int id, double salary) {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employee.setSalary(salary);
        return repository.save(employee);
    }

    public double getEmployeeSalaryById(int id) {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return employee.getSalary();
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        if (repository.getEmployeesByDepartment(department).isEmpty())
            throw new DepartmentNotFoundException(department);
        return repository.getEmployeesByDepartment(department);
    }

}
