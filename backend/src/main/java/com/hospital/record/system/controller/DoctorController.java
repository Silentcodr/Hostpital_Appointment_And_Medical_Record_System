package com.hospital.record.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hospital.record.system.dao.DoctorDao;
import com.hospital.record.system.dto.ResponseStructure;
import com.hospital.record.system.entity.Doctor;
import com.hospital.record.system.service.DoctorService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Doctor>> saveDoctor(@RequestBody Doctor doctor) {
		return doctorService.saveDoctor(doctor);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<List<Doctor>>> fetchAllDoctor() {
		return doctorService.fetchAllDoctors();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Doctor>> fetchDoctorById(@PathVariable Integer id) {
		return doctorService.fetchDoctorById(id);
	}

	@GetMapping("/specialization/{specialization}")
	public ResponseEntity<ResponseStructure<List<Doctor>>> fetchDoctorBySpecialization(
			@PathVariable String specialization) {
		return doctorService.fetchDoctorBySpecialization(specialization);
	}

	@GetMapping("/department/{departmentName}")
	public ResponseEntity<ResponseStructure<List<Doctor>>> fetchDoctorByDepartmentName(
			@PathVariable String departmentName) {
		return doctorService.fetchDoctorByDepartmentName(departmentName);
	}

	@GetMapping("/patient/{patientName}")
	public ResponseEntity<ResponseStructure<List<Doctor>>> fetchDoctorByPatientName(@PathVariable String patientName) {
		return doctorService.fetchDoctorByPatientName(patientName);
	}

	@GetMapping("/appointment/{appointmentId}")
	public ResponseEntity<ResponseStructure<List<Doctor>>> fetchDoctorByAppointmentId(@PathVariable Integer id) {
		return doctorService.fetchDoctorByAppointmentId(id);
	}

	@GetMapping("/availableDays/{day}")
	public ResponseEntity<ResponseStructure<List<Doctor>>> fetchDoctorByAvailableDays(@PathVariable String day) {
		return doctorService.fetchDoctorByAvailableDays(day);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<Doctor>> updateDepartment(@RequestBody Doctor doctor) {
		return doctorService.updateDepartment(doctor);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteDoctorById(@PathVariable Integer Id) {
		return doctorService.deleteDoctorById(Id);
	}

}
