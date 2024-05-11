package com.carserviceagency.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carserviceagency.entities.ServiceOperator;
import com.carserviceagency.exceptions.ResourceNotFoundException;
import com.carserviceagency.payload.requests.ServiceOperatorRequest;
import com.carserviceagency.payload.response.ApiResponse;
import com.carserviceagency.payload.response.ServiceOperatorResponse;
import com.carserviceagency.service.ServiceOperatorService;

@RestController
@RequestMapping("/service/operator/v1")
public class ServiceOperatorController {

	@Autowired
	private ServiceOperatorService serviceOperatorService;
 
	// create a service operator
	@PostMapping("/create")
	ResponseEntity<ServiceOperatorResponse> createOperator(@RequestBody ServiceOperatorRequest serviceOperatorRequest) {
		return new ResponseEntity<ServiceOperatorResponse>(this.serviceOperatorService.createServiceOperator(serviceOperatorRequest),HttpStatus.CREATED);
	}

	// get a single operator
	@GetMapping("/{operatorId}")
	ResponseEntity<ServiceOperatorResponse> getOperator(@PathVariable String operatorId) throws ResourceNotFoundException {
		return new ResponseEntity<ServiceOperatorResponse>(this.serviceOperatorService.getOperatorById(operatorId),HttpStatus.OK);
	}

	// get all the operators
	@GetMapping("/")
	ResponseEntity<List<ServiceOperatorResponse>> getAllOperators() {
		return new  ResponseEntity<List<ServiceOperatorResponse>>(this.serviceOperatorService.getAllOperators(),HttpStatus.OK);
	}

	// update the operator
	@PutMapping("/{operatorId}")
	ResponseEntity<ServiceOperatorResponse> updateServiceOperator(@PathVariable String operatorId,@RequestBody ServiceOperator serviceOperator) throws ResourceNotFoundException {
		return new  ResponseEntity<ServiceOperatorResponse>(this.serviceOperatorService.updateServiceOperatorById(operatorId, serviceOperator),HttpStatus.OK);
	}

	// delete a service operator
	@DeleteMapping("/{operatorId}")
	public ResponseEntity<ApiResponse> deleteServiceOperator(@PathVariable String operatorId)
			throws ResourceNotFoundException {
		ApiResponse apiResponse = this.serviceOperatorService.deleteServiceOperatorById(operatorId);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
}
