package com.bcure.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcure.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
