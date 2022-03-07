package com.springBootCurdOperation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBootCurdOperation.model.Employee;
import com.springBootCurdOperation.request.ApiResponse;
import com.springBootCurdOperation.request.EmpDTO;
import com.springBootCurdOperation.service.EmployeesService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)

class EmployeeControllerTest {
    EmpDTO empDTO;
    Employee employee;
    List<Employee> employeeList = new ArrayList<>();
    String url = "https://localhost:8080/api/v1/";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeesService service;

    @BeforeEach
    void setUp() {
        employeeList.add(new Employee(1, "Shefali",  "Design",30000.00));
        employeeList.add(new Employee(2, "Ayushi", "Development", 40000.5));
        employeeList.add(new Employee(3, "Sneha", "Testing", 30330.00));

        empDTO = new EmpDTO(1, "Shefali",  "Design",30000.00);
        employee = new Employee(1, "Shefali",  "Design",30000.00);
    }

    @AfterEach
    void tearDown() {
        employeeList.clear();
    }

    @Test
    void getWelcomeMessage() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertEquals("Welcome to Employee Management System", response);
    }

    @Test
    void getEmployeeById() throws Exception {
        String requestUrl = this.url += "employee/2";

        when(service.getEmployeeById(anyInt())).thenReturn(employee);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(requestUrl)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.mapToJson(employee);
        String responseJson = result.getResponse().getContentAsString();

        assertThat(responseJson).isEqualTo(expectedJson);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }

    @Test
    void getAllEmployees() throws  Exception{
        String requestUrl = this.url += "employees";

        when(service.getAllEmployees()).thenReturn(employeeList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(requestUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = this.mapToJson(employeeList);
        String responseJson = result.getResponse().getContentAsString();

        assertThat(responseJson).isEqualTo(expectedJson);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void addEmployeeData() throws Exception {
        String requestUrl = this.url += "employee";
        String requestJson = this.mapToJson(empDTO);

        when(service.addEmployeeData(Mockito.any(Employee.class))).thenReturn(employee);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputJson = response.getContentAsString();

        assertThat(outputJson).isEqualTo(requestJson);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void updateEmployeeById() throws Exception {
        String requestUrl = this.url += "employee/2";
        String requestJson = this.mapToJson(empDTO);

        when(service.updateEmployee(anyInt(), Mockito.any(Employee.class))).thenReturn(employee);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(requestUrl)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String responseJson = result.getResponse().getContentAsString();

        assertThat(responseJson).isEqualTo(requestJson);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void deleteEmployeeById() throws Exception{
        String requestUrl = this.url += "employee/2";
        ApiResponse apiResponse = new ApiResponse(true, "Employee Deleted Successfully", HttpStatus.OK);

        doNothing().when(service).deleteEmployeeById(anyInt());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(requestUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String responseJson = result.getResponse().getContentAsString();
        assertThat(responseJson).isEqualTo(this.mapToJson(apiResponse));
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void updateEmployeeSalaryById() throws  Exception{
        String requestUrl = this.url += "employee/2/4500.0";
        String expectedJson = this.mapToJson(empDTO);
        when(service.updateEmployeeSalaryById(anyInt(), Mockito.anyDouble())).thenReturn(employee);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(requestUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String responseJson = result.getResponse().getContentAsString();

        assertThat(responseJson).isEqualTo(expectedJson);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void getEmployeeSalaryById() throws  Exception{
        String requestUrl = this.url += "getSalary/2";

        when(service.getEmployeeSalaryById(anyInt())).thenReturn(25000.0);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(requestUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String responseJson = result.getResponse().getContentAsString();
        assertThat(responseJson).isEqualTo("25000.0");
    }

    @Test
    void getAllEmployeesByDepartment() throws Exception{
        String requestUri = this.url += "employees/Design";

        when(service.getEmployeesByDepartment(Mockito.anyString())).thenReturn(employeeList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(requestUri).accept(MediaType.APPLICATION_JSON)
                .content(this.mapToJson(employeeList)).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(this.mapToJson(employeeList));
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}