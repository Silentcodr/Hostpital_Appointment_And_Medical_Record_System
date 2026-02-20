package com.hospital.record.system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.record.system.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
	@org.springframework.data.jpa.repository.Query("SELECT p FROM Patient p WHERE p.phone = :phoneNumber")
	Optional<Patient> findPatientByPhoneNumber(
			@org.springframework.data.repository.query.Param("phoneNumber") Long phoneNumber);

	@org.springframework.data.jpa.repository.Query("SELECT p FROM Patient p WHERE p.age > :age")
	List<Patient> findPatientAgeGreaterThan(@org.springframework.data.repository.query.Param("age") Integer age);

	@org.springframework.data.jpa.repository.Query("SELECT p FROM Patient p JOIN p.appointments a WHERE a.appointmentId = :id")
	List<Patient> findPatientByAppointmentId(@org.springframework.data.repository.query.Param("id") Integer id);

	@org.springframework.data.jpa.repository.Query("SELECT p FROM Patient p JOIN p.medicalRecords m WHERE m.recordId = :id")
	List<Patient> findPatientByRecordId(@org.springframework.data.repository.query.Param("id") Integer id);
}
