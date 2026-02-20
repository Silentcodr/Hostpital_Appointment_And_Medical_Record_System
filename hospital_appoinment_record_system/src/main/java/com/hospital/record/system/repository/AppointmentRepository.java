package com.hospital.record.system.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.record.system.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
	@org.springframework.data.jpa.repository.Query("SELECT a FROM Appointment a WHERE a.appointmentDateTime = :date")
	List<Appointment> findAppointmentByDate(
			@org.springframework.data.repository.query.Param("date") LocalDateTime date);

	@org.springframework.data.jpa.repository.Query("SELECT a FROM Appointment a WHERE a.doctor.doctorId = :doctorId")
	List<Appointment> findAppointmentByDoctor(
			@org.springframework.data.repository.query.Param("doctorId") Integer doctorId);

	@org.springframework.data.jpa.repository.Query("SELECT a FROM Appointment a WHERE a.patient.patientId = :patientId")
	List<Appointment> findAppointmentByPatient(
			@org.springframework.data.repository.query.Param("patientId") Integer patientId);

	@org.springframework.data.jpa.repository.Query("SELECT a FROM Appointment a WHERE CAST(a.status AS string) = :status")
	List<Appointment> findAppointmentByStatus(@org.springframework.data.repository.query.Param("status") String status);

}
