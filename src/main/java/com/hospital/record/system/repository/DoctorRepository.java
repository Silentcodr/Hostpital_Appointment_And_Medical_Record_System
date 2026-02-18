package com.hospital.record.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hospital.record.system.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor,Integer>{
	List<Doctor> findDoctorBySpecialization(String specialization);
	
	List<Doctor> findDoctorByDepartmentName(String departmentname);
	
	List<Doctor> findDoctorByPatientName(String patientName);
	
	List<Doctor> findDoctorByAppointmentId(Integer id);
	
	@Query("SELECT d FROM Doctor d JOIN d.availableDays day WHERE day = :day")
	List<Doctor> findDoctorByAvailableDays(@Param("day") String day);

}
