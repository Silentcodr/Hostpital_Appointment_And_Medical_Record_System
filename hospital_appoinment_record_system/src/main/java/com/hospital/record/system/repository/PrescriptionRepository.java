package com.hospital.record.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.record.system.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

	@org.springframework.data.jpa.repository.Query("SELECT p FROM Prescription p WHERE p.medicalRecord.recordId = :medicalRecordId")
	List<Prescription> findPrescriptionByMedicalRecordId(
			@org.springframework.data.repository.query.Param("medicalRecordId") Integer medicalRecordId);

	@org.springframework.data.jpa.repository.Query("SELECT p FROM Prescription p WHERE p.medicalRecord.patient.patientId = :patientid")
	List<Prescription> findPrescriptionByPatientId(
			@org.springframework.data.repository.query.Param("patientid") Integer patientid);
}
