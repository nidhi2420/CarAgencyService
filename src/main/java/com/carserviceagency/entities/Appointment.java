package com.carserviceagency.entities;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long appointmentId;

	    private String customerName;
	    private LocalDate date;
	    private LocalTime startTime;
	    private LocalTime endTime;

	    @ManyToOne
	    @JoinColumn(name = "operatorId", referencedColumnName = "operatorId")
	    private ServiceOperator operator;

    // Constructors, getters, and setters
}
