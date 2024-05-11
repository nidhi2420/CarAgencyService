package com.carserviceagency.payload.response;

import java.util.List;

import com.carserviceagency.entities.Appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentsOfOperatorResponse {

	private String operatorId;
	private int noOfAppointments;
	private String operatorName;
	private List<Appointment> appointments;

}
