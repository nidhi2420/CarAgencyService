package com.carserviceagency.exceptions;

public class AppointmentException extends RuntimeException {

    public AppointmentException(String message) {
        super(message, null, false, false); // Disable the stack trace
    }
}

