package com.carserviceagency.payload.requests;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckAvailabilityRequest {

	 	private LocalDate date;
	    private String operatorId;
}
