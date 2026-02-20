package com.hospital.record.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.record.system.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer>{

	List<Prescription> findPrescriptionByMedicalRecordId(Integer medicalRecordId);
	
	List<Prescription> findPrescriptionByPatientId(Integer patientid);
}
