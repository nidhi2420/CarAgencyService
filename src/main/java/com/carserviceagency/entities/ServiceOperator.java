package com.carserviceagency.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOperator {

	@Id
	private String operatorId;
	private String operatorName;
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	private int numberOfAppointments;
}
