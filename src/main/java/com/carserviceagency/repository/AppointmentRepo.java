package com.carserviceagency.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carserviceagency.entities.Appointment;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
	
	@Query("SELECT a FROM Appointment a WHERE a.operator.operatorId = :operatorId")
	List<Appointment> findAllAppointments(@Param("operatorId") String operatorId);
	List<Appointment> findByCustomerName(String customerName);
//	List<Appointment> findByOperatorIdAndStartDate(String operatorId, LocalDate startDate);
	 @Query("SELECT a FROM Appointment a WHERE a.operator.operatorId = :operatorId AND a.date = :startDate")
	    List<Appointment> findByOperatorIdAndStartDate(@Param("operatorId") String operatorId, @Param("startDate") LocalDate startDate);
}
