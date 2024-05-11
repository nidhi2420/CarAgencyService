package com.carserviceagency.exceptions;

import java.util.function.Supplier;

public class ResourceNotFoundException extends Exception {
	
	 public ResourceNotFoundException(String message) {
	        super(message);
	    }
}
