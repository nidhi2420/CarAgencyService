package com.carserviceagency.serviceImpl;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carserviceagency.entities.ServiceOperator;
import com.carserviceagency.exceptions.ResourceNotFoundException;
import com.carserviceagency.payload.requests.ServiceOperatorRequest;
import com.carserviceagency.payload.response.ApiResponse;
import com.carserviceagency.payload.response.ServiceOperatorResponse;
import com.carserviceagency.repository.ServiceOperatorRepo;
import com.carserviceagency.service.ServiceOperatorService;

@Service
public class ServiceOperatorServiceImpl implements ServiceOperatorService {

	@Autowired
	private ServiceOperatorRepo serviceOperatorRepo;
	@Autowired
	private ModelMapper modelMapper;
	private static final String OPERATOR_ID_PREFIX = "OP";
	private static final AtomicInteger idCounter = new AtomicInteger(0);

	/**
	 * Create a new service operator with the provided details.
	 *
	 * @param serviceOperatorRequest The request containing details of the service
	 *                               operator to be created.
	 * @return A {@link ServiceOperatorResponse} object representing the created
	 *         service operator.
	 */
	@Override
	public ServiceOperatorResponse createServiceOperator(ServiceOperatorRequest serviceOperatorRequest) {
		ServiceOperator serviceOperator = new ServiceOperator();
		serviceOperator.setOperatorName(serviceOperatorRequest.getOperatorName());
		serviceOperator.setOperatorId(generateOperatorId());
		this.serviceOperatorRepo.save(serviceOperator);
		ServiceOperatorResponse serviceOperatorResponse = this.modelMapper.map(serviceOperator,
				ServiceOperatorResponse.class);
		return serviceOperatorResponse;
	}

	/**
	 * Retrieve the details of a service operator with the specified operator ID.
	 *
	 * @param operatorId The ID of the service operator to retrieve.
	 * @return A {@link ServiceOperatorResponse} object representing the service
	 *         operator details.
	 * @throws ResourceNotFoundException If the service operator with the specified
	 *                                   ID is not found.
	 */
	@Override
	public ServiceOperatorResponse getOperatorById(String operatorId) throws ResourceNotFoundException {
		ServiceOperator serviceOperator = this.serviceOperatorRepo.findById(operatorId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Service operator with operatorId: " + operatorId + " cant be found."));
		return this.modelMapper.map(serviceOperator, ServiceOperatorResponse.class);
	}

	/**
	 * Retrieve details of all service operators.
	 *
	 * @return A list of {@link ServiceOperatorResponse} objects representing all
	 *         service operators.
	 */
	@Override
	public List<ServiceOperatorResponse> getAllOperators() {
		return this.serviceOperatorRepo.findAll().stream()
				.map(e -> this.modelMapper.map(e, ServiceOperatorResponse.class)).toList();
	}

	/**
	 * Update the details of a service operator with the specified operator ID.
	 *
	 * @param operatorId      The ID of the service operator to update.
	 * @param serviceOperator The updated details of the service operator.
	 * @return A {@link ServiceOperatorResponse} object representing the updated
	 *         service operator details.
	 * @throws ResourceNotFoundException If the service operator with the specified
	 *                                   ID is not found.
	 */
	@Override
	public ServiceOperatorResponse updateServiceOperatorById(String operatorId, ServiceOperator serviceOperator)
			throws ResourceNotFoundException {
		ServiceOperator serviceOperatorDB = this.serviceOperatorRepo.findById(operatorId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Service operator with operatorId: " + operatorId + " cant be found."));
		serviceOperatorDB.setOperatorName(serviceOperator.getOperatorName());
		this.serviceOperatorRepo.save(serviceOperatorDB);
		return this.modelMapper.map(serviceOperatorDB, ServiceOperatorResponse.class);
	}

	/**
	 * Delete the service operator with the specified operator ID.
	 *
	 * @param operatorId The ID of the service operator to delete.
	 * @return An {@link ApiResponse} indicating the success of the deletion
	 *         operation.
	 * @throws ResourceNotFoundException If the service operator with the specified
	 *                                   ID is not found.
	 */
	@Override
	public ApiResponse deleteServiceOperatorById(String operatorId) throws ResourceNotFoundException {
		ServiceOperator serviceOperatorDB = this.serviceOperatorRepo.findById(operatorId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Service operator with operatorId: " + operatorId + " cant be found."));
		this.serviceOperatorRepo.delete(serviceOperatorDB);
		return new ApiResponse("Operator with ID " + operatorId + " has been deleted successfully.");
	}

	/**
	 * Generate a unique operator ID.
	 *
	 * @return A unique operator ID.
	 */
	private String generateOperatorId() {
		int id = idCounter.incrementAndGet();
		return OPERATOR_ID_PREFIX + String.format("%04d", id);
	}
}
