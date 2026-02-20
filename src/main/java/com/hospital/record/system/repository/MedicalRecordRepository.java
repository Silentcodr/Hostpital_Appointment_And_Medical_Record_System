package com.hospital.record.system.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.record.system.entity.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> 
{

	List<MedicalRecord> findMedicalRecordByDoctorId(Integer doctorId);
	
	List<MedicalRecord> findMedicalRecordByPatientId(Integer patientId);
	
	List<MedicalRecord> findMedicalRecordByAppointmentId(Integer appointmentId);
	
	List<MedicalRecord> findMedicalRecordByVisitDate(LocalDate visitDate);
}
