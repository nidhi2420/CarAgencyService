package com.carserviceagency.payload.response;

import java.util.ArrayList;
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
public class ServiceOperatorResponse {
	
	private String operatorId;
	private String operatorName;

}
