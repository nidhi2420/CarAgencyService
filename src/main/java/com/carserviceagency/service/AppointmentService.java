package com.carserviceagency.service;

import java.util.List;

import com.carserviceagency.exceptions.AppointmentException;
import com.carserviceagency.payload.requests.AppointmentRequest;
import com.carserviceagency.payload.response.ApiResponse;
import com.carserviceagency.payload.response.AppointmentResponse;
import com.carserviceagency.payload.response.AppointmentsOfOperatorResponse;

public interface AppointmentService {

	AppointmentResponse scheduleAppointment(AppointmentRequest appointmentRequest) throws AppointmentException;
	AppointmentResponse rescheduleAppointment(Long appointmentId,AppointmentRequest appointmentRequest) throws AppointmentException;
	ApiResponse cancelAppointment(Long appointmentId);
	AppointmentResponse getAppointmentById(Long appointmentId ) throws AppointmentException;
	List<AppointmentResponse> getAllAppointentsOfCustomer(String customerName);
	List<AppointmentsOfOperatorResponse> getAllAppointmentsOfOperators();

}
