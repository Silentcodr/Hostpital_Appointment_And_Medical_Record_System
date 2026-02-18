package com.hospital.record.system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.record.system.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>
{
	Optional<Patient> findPatientByPhoneNumber(Long phoneNumber);
	
	List<Patient> findPatientAgeGreaterThan(Integer age);
	
	List<Patient> findPatientByAppointmentId(Integer id);
	
	List<Patient> findPatientByRecordId(Integer id);
}
