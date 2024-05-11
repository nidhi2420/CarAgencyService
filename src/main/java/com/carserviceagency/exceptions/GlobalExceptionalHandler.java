package com.carserviceagency.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.carserviceagency.payload.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionalHandler {

	@ExceptionHandler(AppointmentException.class)
	public ResponseEntity<ApiResponse> exceptionalHandlerAppointment(Exception ex){
		return new ResponseEntity<ApiResponse>(new ApiResponse(ex.getMessage()),HttpStatus.OK);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> exceptionalHandlerResource(Exception ex){
		return new ResponseEntity<ApiResponse>(new ApiResponse(ex.getMessage()),HttpStatus.OK);
	}
	
}
