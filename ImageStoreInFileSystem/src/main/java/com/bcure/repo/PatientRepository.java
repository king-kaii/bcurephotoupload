package com.bcure.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcure.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
	
//	@Query("SELECT s FROM Patient s WHERE s.relativePath = :relativePath")
//	InputStream getImageByRelativePath(@Param(value = "relativePath") String relativePath);
	
}
