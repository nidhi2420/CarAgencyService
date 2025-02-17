package com.carserviceagency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carserviceagency.entities.ServiceOperator;
@Repository
public interface ServiceOperatorRepo extends JpaRepository<ServiceOperator, String> {

	ServiceOperator findByOperatorId(String operatorId);
}
