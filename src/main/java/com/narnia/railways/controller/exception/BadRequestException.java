package com.narnia.railways.controller.exception;

import lombok.Data;

@Data
public class BadRequestException extends RuntimeException {
    private final String entityName;

    public BadRequestException(String entityName) {
        this.entityName = entityName;
    }

    public BadRequestException(String entityName, String message) {
        super(message);
        this.entityName = entityName;
    }
}
