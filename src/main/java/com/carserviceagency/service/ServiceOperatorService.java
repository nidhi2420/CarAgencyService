package com.carserviceagency.service;

import java.util.List;

import com.carserviceagency.entities.ServiceOperator;
import com.carserviceagency.exceptions.ResourceNotFoundException;
import com.carserviceagency.payload.requests.ServiceOperatorRequest;
import com.carserviceagency.payload.response.ApiResponse;
import com.carserviceagency.payload.response.ServiceOperatorResponse;

public interface ServiceOperatorService {

	ServiceOperatorResponse createServiceOperator(ServiceOperatorRequest serviceOperatorRequest);
	ServiceOperatorResponse getOperatorById(String operatorId) throws ResourceNotFoundException;
	List<ServiceOperatorResponse> getAllOperators();
	ServiceOperatorResponse updateServiceOperatorById(String operatorId,ServiceOperator serviceOperator) throws ResourceNotFoundException;
	ApiResponse deleteServiceOperatorById(String operatorId) throws ResourceNotFoundException;

}
