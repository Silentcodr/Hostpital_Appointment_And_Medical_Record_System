package com.hospital.record.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hospital.record.system.dto.ResponseStructure;
import com.hospital.record.system.entity.Patient;
import com.hospital.record.system.repository.PatientRepository;
import com.hospital.record.system.service.PatientService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Patient>> registerPatient(@RequestBody Patient patient) {
		return patientService.registerPatient(patient);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<List<Patient>>> fetchAllPatient() {
		return patientService.fetchAllPatient();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Patient>> fetchPatientById(@PathVariable Integer id) {
		return patientService.fetchPatientById(id);
	}

	@GetMapping("/age/{age}")
	public ResponseEntity<ResponseStructure<List<Patient>>> fetchPatientWhereAgeIsGreaterThan(
			@PathVariable Integer age) {
		return patientService.fetchPatientWhereAgeIsGreaterThan(age);
	}

	@GetMapping("/appointment/{appointmentId}")
	public ResponseEntity<ResponseStructure<List<Patient>>> fetchPatientByAppointmentId(
			@PathVariable Integer appointmentId) {
		return patientService.fetchPatientByAppointmentId(appointmentId);
	}

	@GetMapping("/medicalRecord/{recordId}")
	public ResponseEntity<ResponseStructure<List<Patient>>> fetchPatientByMedicalRecordId(
			@PathVariable Integer recordId) {
		return patientService.fetchPatientByMedicalRecordId(recordId);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<Patient>> updatePatient(@RequestBody Patient patient) {
		return patientService.updatePatient(patient);
	}

}
