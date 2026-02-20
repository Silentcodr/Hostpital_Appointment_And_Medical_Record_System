package com.hospital.record.system.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.record.system.entity.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {

	@org.springframework.data.jpa.repository.Query("SELECT m FROM MedicalRecord m WHERE m.doctor.doctorId = :doctorId")
	List<MedicalRecord> findMedicalRecordByDoctorId(
			@org.springframework.data.repository.query.Param("doctorId") Integer doctorId);

	@org.springframework.data.jpa.repository.Query("SELECT m FROM MedicalRecord m WHERE m.patient.patientId = :patientId")
	List<MedicalRecord> findMedicalRecordByPatientId(
			@org.springframework.data.repository.query.Param("patientId") Integer patientId);

	@org.springframework.data.jpa.repository.Query("SELECT m FROM MedicalRecord m WHERE 1=0") // Dummy query as there is
																								// no relationship to
																								// Appointment
	List<MedicalRecord> findMedicalRecordByAppointmentId(
			@org.springframework.data.repository.query.Param("appointmentId") Integer appointmentId);

	@org.springframework.data.jpa.repository.Query("SELECT m FROM MedicalRecord m WHERE m.visitDate = :visitDate")
	List<MedicalRecord> findMedicalRecordByVisitDate(
			@org.springframework.data.repository.query.Param("visitDate") LocalDate visitDate);
}
