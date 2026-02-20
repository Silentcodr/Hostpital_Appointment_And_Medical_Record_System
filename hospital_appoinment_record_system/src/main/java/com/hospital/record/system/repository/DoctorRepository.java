package com.hospital.record.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hospital.record.system.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
	List<Doctor> findDoctorBySpecialization(String specialization);

	@Query("SELECT d FROM Doctor d WHERE d.department.departmentName = :departmentName")
	List<Doctor> findDoctorByDepartmentName(@Param("departmentName") String departmentname);

	@Query("SELECT d FROM Doctor d JOIN d.appointments a WHERE a.patient.patientName = :patientName")
	List<Doctor> findDoctorByPatientName(@Param("patientName") String patientName);

	@Query("SELECT d FROM Doctor d JOIN d.appointments a WHERE a.appointmentId = :id")
	List<Doctor> findDoctorByAppointmentId(@Param("id") Integer id);

	@Query("SELECT d FROM Doctor d JOIN d.availableDays day WHERE day = :day")
	List<Doctor> findDoctorByAvailableDays(@Param("day") String day);

}
