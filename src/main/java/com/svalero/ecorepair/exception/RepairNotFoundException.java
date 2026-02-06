package com.reparaciones.api.exception;

public class RepairNotFoundException extends RuntimeException {

    public RepairNotFoundException(String message) {
        super(message);
    }
}
