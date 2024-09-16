package com.product_project.machines_catalog.exception;

public class MachineNotFoundException extends RuntimeException {

    public MachineNotFoundException(String message) {
        super(message);
    }
}
