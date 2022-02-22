package com.springBootCurdOperation.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
public class ApiResponse {
    private Boolean state;
    private String message;
    private HttpStatus status;
}
