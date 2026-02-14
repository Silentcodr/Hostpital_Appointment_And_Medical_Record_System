package com.hospital.record.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.record.system.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>{

}
