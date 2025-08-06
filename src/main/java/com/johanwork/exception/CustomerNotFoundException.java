package com.johanwork.exception;

public class CustomerNotFoundException extends RuntimeException{

    private static final String MESSAGE = "Customer [id=%d] not found";

    public CustomerNotFoundException(Long id) {
        super(MESSAGE.formatted(id));
    }

}
