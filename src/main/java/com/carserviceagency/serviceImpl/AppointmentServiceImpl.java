/**
 * Service implementation class for managing appointments.
 * This class provides methods to schedule, reschedule, cancel, and retrieve appointments,
 * as well as fetch appointment details for customers and operators.
 * Implements the {@link AppointmentService} interface.
 * 
 * @author Nidhi Sahani
 * @version 1.0
 * @since 10-05-2024
 */
package com.carserviceagency.serviceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.carserviceagency.entities.Appointment;
import com.carserviceagency.entities.ServiceOperator;
import com.carserviceagency.exceptions.AppointmentException;
import com.carserviceagency.payload.requests.AppointmentRequest;
import com.carserviceagency.payload.response.ApiResponse;
import com.carserviceagency.payload.response.AppointmentResponse;
import com.carserviceagency.payload.response.AppointmentsOfOperatorResponse;
import com.carserviceagency.repository.AppointmentRepo;
import com.carserviceagency.repository.ServiceOperatorRepo;
import com.carserviceagency.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AppointmentRepo appointmentRepo;

	@Autowired
	private ServiceOperatorRepo serviceOperatorRepo;

	
	
	
	
	/**
	 * Schedule a new appointment for the specified operator. Checks for existing
	 * appointments at the selected date and time. Throws an
	 * {@link AppointmentException} if the operator does not exist or if there is a
	 * scheduling conflict.
	 *
	 * @param appointmentRequest The appointment request containing details of the
	 *                           appointment to be scheduled.
	 * @return An {@link AppointmentResponse} object representing the scheduled
	 *         appointment.
	 * @throws AppointmentException If the operator does not exist or if there is a
	 *                              scheduling conflict.
	 */
	@CacheEvict(value = "appointmentsOfOperators")
	@Override
	public AppointmentResponse scheduleAppointment(AppointmentRequest appointmentRequest) throws AppointmentException {
		List<Appointment> appointments = this.appointmentRepo.findAllAppointments(appointmentRequest.getOperatorId());

		// Check if operator exists
		ServiceOperator operator = this.serviceOperatorRepo.findByOperatorId(appointmentRequest.getOperatorId());
		if (operator == null) {
			throw new AppointmentException("No operator found with ID: " + appointmentRequest.getOperatorId());
		}

		// Check if there are existing appointments for the operator
		boolean appointmentExists = appointments.stream().anyMatch(e -> e.getDate().equals(appointmentRequest.getDate())
				&& e.getStartTime().equals(appointmentRequest.getStartTime()));

		if (appointmentExists) {
			throw new AppointmentException(
					"An appointment already exists at the selected date and time. Please select another slot.");
		}

		Appointment newAppointment = this.modelMapper.map(appointmentRequest, Appointment.class);
		newAppointment.setEndTime(newAppointment.getStartTime().plusHours(1));
		newAppointment.setOperator(operator);
		appointmentRepo.save(newAppointment);

		AppointmentResponse appointmentResponse = this.modelMapper.map(newAppointment, AppointmentResponse.class);
		appointmentResponse.setOperatorName(operator.getOperatorName());
		appointmentResponse.setOperatorId(operator.getOperatorId());

		return appointmentResponse;
	}

	/**
	 * Reschedule an existing appointment with the specified appointment ID. Checks
	 * for scheduling conflicts with other appointments for the same operator and
	 * date. Throws an {@link AppointmentException} if the appointment is not found
	 * or if there is a scheduling conflict.
	 *
	 * @param appointmentId      The ID of the appointment to be rescheduled.
	 * @param appointmentRequest The appointment request containing the new date and
	 *                           time for the appointment.
	 * @return An {@link AppointmentResponse} object representing the updated
	 *         appointment details.
	 * @throws AppointmentException If the appointment is not found or if there is a
	 *                              scheduling conflict.
	 */
	
	
	@CacheEvict(value = "appointmentsOfOperators")
	@Override
	public AppointmentResponse rescheduleAppointment(Long appointmentId, AppointmentRequest appointmentRequest) {
		try {
			Appointment appointment = appointmentRepo.findById(appointmentId)
					.orElseThrow(() -> new AppointmentException("Appointment not found with ID: " + appointmentId));

			LocalDate newDate = appointmentRequest.getDate();
			LocalTime newStartTime = appointmentRequest.getStartTime();

			// Check if the new slot is available for rescheduling
			boolean slotAvailable = isSlotAvailableForRescheduling(appointment.getOperator().getOperatorId(), newDate,
					newStartTime, appointmentId);
			if (!slotAvailable) {
				throw new AppointmentException("Slot is already booked.");
			}

			// Update appointment details
			appointment.setCustomerName(appointmentRequest.getCustomerName());
			appointment.setDate(newDate);
			appointment.setStartTime(newStartTime);
			appointment.setEndTime(newStartTime.plusHours(1));

			// Save the updated appointment
			appointmentRepo.save(appointment);

			AppointmentResponse appointmentResponse = modelMapper.map(appointment, AppointmentResponse.class);
			appointmentResponse.setOperatorId(appointment.getOperator().getOperatorId());
			appointmentResponse.setOperatorName(appointment.getOperator().getOperatorName());
			return appointmentResponse;
		} catch (AppointmentException e) {

			e.printStackTrace();
			return null;
		}
	}

	private boolean isSlotAvailableForRescheduling(String operatorId, LocalDate date, LocalTime startTime,
			Long appointmentId) {
		List<Appointment> existingAppointments = appointmentRepo.findByOperatorIdAndStartDate(operatorId, date);
		for (Appointment existingAppointment : existingAppointments) {
			if (!existingAppointment.getAppointmentId().equals(appointmentId)
					&& existingAppointment.getStartTime().equals(startTime)) {
				return false;
			}
		}
		return true;
	}
	
	
	
	

	/**
	 * Cancel an existing appointment with the specified appointment ID. Deletes the
	 * appointment from the database.
	 *
	 * @param appointmentId The ID of the appointment to be canceled.
	 * @return An {@link ApiResponse} indicating the success of the cancellation
	 *         operation.
	 */
	@CacheEvict(value = "appointments", key = "#appointmentId")
	@Override
	public ApiResponse cancelAppointment(Long appointmentId) {
	    Optional<Appointment> appointmentOptional = appointmentRepo.findById(appointmentId);
	    if (appointmentOptional.isPresent()) {
	        appointmentRepo.deleteById(appointmentId);
	        return new ApiResponse("Appointment with ID " + appointmentId + " has been deleted successfully.");
	    } else {
	        throw new AppointmentException("Appointment not found with ID: " + appointmentId);
	    }
	}


	
	
	
	/**
	 * Retrieve the details of an appointment with the specified appointment ID.
	 * Throws an {@link AppointmentException} if the appointment is not found.
	 *
	 * @param appointmentId The ID of the appointment to retrieve.
	 * @return An {@link AppointmentResponse} object representing the appointment
	 *         details.
	 * @throws AppointmentException If the appointment is not found.
	 */
	 @Cacheable(value = "appointments", key = "#appointmentId")
	@Override
	public AppointmentResponse getAppointmentById(Long appointmentId) throws AppointmentException {
		Appointment appointment = this.appointmentRepo.findById(appointmentId)
				.orElseThrow(() -> new AppointmentException("cant fint the appointment with the appointment id"));
		AppointmentResponse appointmentResponse = this.modelMapper.map(appointment, AppointmentResponse.class);
		appointmentResponse.setOperatorId(appointment.getOperator().getOperatorId());
		appointmentResponse.setOperatorName(appointment.getOperator().getOperatorName());
		return appointmentResponse;
	}

	
	 
	 
	 
	 /**
	 * Retrieve all appointments associated with a specific customer.
	 *
	 * @param customerName The name of the customer.
	 * @return A list of {@link AppointmentResponse} objects representing the
	 *         appointments of the customer.
	 */
	@Cacheable(value = "appointmentsOfCustomer", key = "#customerName")
	@Override
	public List<AppointmentResponse> getAllAppointentsOfCustomer(String customerName) {
		List<Appointment> appointments = appointmentRepo.findByCustomerName(customerName);
		List<AppointmentResponse> appointmentResponses = new ArrayList<>();
		for (Appointment appointment : appointments) {
			AppointmentResponse appointmentResponse = modelMapper.map(appointment, AppointmentResponse.class);
			appointmentResponse.setOperatorId(appointment.getOperator().getOperatorId());
			appointmentResponse.setOperatorName(appointment.getOperator().getOperatorName());
			appointmentResponses.add(appointmentResponse);
		}
		return appointmentResponses;
	}

	
	
	
	
	/**
	 * Retrieve the number of appointments for each service operator.
	 *
	 * @return A list of {@link AppointmentsOfOperatorResponse} objects representing
	 *         the number of appointments for each operator.
	 */
	@Cacheable(value = "appointmentsOfOperators")
	@Override
	public List<AppointmentsOfOperatorResponse> getAllAppointmentsOfOperators() {
	    List<ServiceOperator> operators = serviceOperatorRepo.findAll();
	    List<AppointmentsOfOperatorResponse> operatorAppointments = new ArrayList<>();
	    
	    for (ServiceOperator operator : operators) {
	        List<Appointment> appointments = appointmentRepo.findAllAppointments(operator.getOperatorId());
	        int noOfAppointments = appointments.size();
	        
	        // Create a response object for each operator and add it to the list
	        AppointmentsOfOperatorResponse operatorResponse = new AppointmentsOfOperatorResponse();
	        operatorResponse.setOperatorId(operator.getOperatorId());
	        operatorResponse.setOperatorName(operator.getOperatorName());
	        operatorResponse.setAppointments(appointments); // Set the list of appointments
	        operatorResponse.setNoOfAppointments(noOfAppointments);
	        
	        operatorAppointments.add(operatorResponse);
	    }
	    
	    return operatorAppointments;
	}


}
