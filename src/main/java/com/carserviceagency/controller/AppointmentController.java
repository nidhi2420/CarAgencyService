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

import com.carserviceagency.exceptions.AppointmentException;
import com.carserviceagency.payload.requests.AppointmentRequest;
import com.carserviceagency.payload.requests.CheckAvailabilityRequest;
import com.carserviceagency.payload.response.ApiResponse;
import com.carserviceagency.payload.response.AppointmentResponse;
import com.carserviceagency.payload.response.AppointmentsOfOperatorResponse;
import com.carserviceagency.payload.response.CheckAvailabilityResponse;
import com.carserviceagency.service.AppointmentService;

@RestController
@RequestMapping("/appointment/v1")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	
	
	// Endpoint for booking an appointment
	@PostMapping("/book")
	public ResponseEntity<AppointmentResponse> bookAppointment(@RequestBody AppointmentRequest appointmentRequest)
			throws AppointmentException {

		return new ResponseEntity<AppointmentResponse>(this.appointmentService.scheduleAppointment(appointmentRequest),
				HttpStatus.OK);
	}

	
	
	// Endpoint for rescheduling an appointment
	@PutMapping("/{appointmentId}")
	public ResponseEntity<AppointmentResponse> rescheduleTheAppointment(@PathVariable Long appointmentId,
			@RequestBody AppointmentRequest appointmentRequest) throws AppointmentException {
		return new ResponseEntity<AppointmentResponse>(
				this.appointmentService.rescheduleAppointment(appointmentId, appointmentRequest), HttpStatus.OK);
	}

	
	
	// Endpoint for canceling an appointment
	@DeleteMapping("/{appointmentId}")
	public ResponseEntity<ApiResponse> cancelAppointment(@PathVariable Long appointmentId) {

		String successMessage = "Appointment with ID " + appointmentId + " has been deleted successfully.";

		return new ResponseEntity<ApiResponse>(this.appointmentService.cancelAppointment(appointmentId), HttpStatus.OK);
	}

	
	
	// Endpoint for retrieving an appointment
	@GetMapping("/{appointmentId}")
	public ResponseEntity<AppointmentResponse> getAppointment(@PathVariable Long appointmentId)
			throws AppointmentException {

		return new ResponseEntity<AppointmentResponse>(this.appointmentService.getAppointmentById(appointmentId),
				HttpStatus.OK);
	}

	
	
	// Endpoint for retrieving all appointments of a customer
	@GetMapping("/all/{name}")
	public ResponseEntity<List<AppointmentResponse>> getAllAppointmentsOfCustomerr(@PathVariable String name) {
		return new ResponseEntity<List<AppointmentResponse>>(this.appointmentService.getAllAppointentsOfCustomer(name),
				HttpStatus.OK);
	}

	
	
	// Endpoint for retrieving appointments for a specific service operator
	@GetMapping("/operators")
	public ResponseEntity<List<AppointmentsOfOperatorResponse>> getAppointmentsOfOperators() {

		return new ResponseEntity<List<AppointmentsOfOperatorResponse>>(
				this.appointmentService.getAllAppointmentsOfOperators(), HttpStatus.OK);
	}

}
