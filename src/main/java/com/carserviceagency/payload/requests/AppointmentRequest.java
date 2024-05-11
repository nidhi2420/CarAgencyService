package com.carserviceagency.payload.requests;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {
	private Long appointmentId;
	private String customerName;
	private LocalDate date;
	private LocalTime startTime;
	private String operatorId;
}
