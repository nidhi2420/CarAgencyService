package com.carserviceagency.payload.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
	private Long appointmentId;
	private String customerName;
	private String operatorId;
	private String operatorName;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;	
}
