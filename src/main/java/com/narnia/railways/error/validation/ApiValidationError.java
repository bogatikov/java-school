package com.narnia.railways.error.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiValidationError {

    private HttpStatus status;
    private String message;
    private String type;
    private Map<String, String> errors;
}