package com.springBootCurdOperation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException
{
    public EmployeeNotFoundException(int id){
        super(String.format("Employee Not Found For ID: %d",id));
    }
}
