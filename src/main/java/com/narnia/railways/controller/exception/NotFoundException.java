package com.narnia.railways.controller.exception;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {
    private String entityName;

    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }
}
