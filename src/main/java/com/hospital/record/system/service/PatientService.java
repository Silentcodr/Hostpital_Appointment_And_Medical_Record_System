package com.hospital.record.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hospital.record.system.dto.ResponseStructure;
import com.hospital.record.system.entity.Patient;
import com.hospital.record.system.exception.IdNotFoundException;
import com.hospital.record.system.exception.RecordNotFoundException;
import com.hospital.record.system.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Patient>> registerPatient(@RequestBody Patient patient)
	{
		ResponseStructure<Patient> response = new ResponseStructure<Patient>();
		response.setData(patientRepository.save(patient));
		response.setMessage("The Patient Record registered Successfully");
		response.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<Patient>>(response,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Patient>>> fetchAllPatient()
	{
		ResponseStructure<List<Patient>> response = new ResponseStructure<List<Patient>>();
		response.setData(patientRepository.findAll());
		response.setMessage("All The Records fetched Successfully");
		response.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Patient>>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Patient>> fetchPatientById(@PathVariable Integer id)
	{
		ResponseStructure<Patient> response = new ResponseStructure<Patient>();
		Optional<Patient> opt = patientRepository.findById(id);
		if(opt.isPresent())
		{
			response.setData(opt.get());
			response.setMessage("The patient Record Fetched Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Patient>>(response,HttpStatus.OK);
		}
		else
		{
			throw new IdNotFoundException("The Id is not found in the Database");
		}
	}
	
	@GetMapping("/{phoneNumber}")
	public ResponseEntity<ResponseStructure<Patient>> fetchPatientByPhoneNumber(@PathVariable Long phoneNumber)
	{
		ResponseStructure<Patient> response = new ResponseStructure<Patient>();
		Optional<Patient> opt = patientRepository.findPatientByPhoneNumber(phoneNumber);
		if(opt.isPresent())
		{
			response.setData(opt.get());
			response.setMessage("The patient Record Fetched Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Patient>>(response,HttpStatus.OK);
		}
		else
		{
			throw new RecordNotFoundException("The Record is not found in the Database");
		}
	}
	
	@GetMapping("/age/{age}")
	public ResponseEntity<ResponseStructure<List<Patient>>> fetchPatientWhereAgeIsGreaterThan(@PathVariable Integer age)
	{
		ResponseStructure<List<Patient>> response = new ResponseStructure<List<Patient>>();
		List<Patient> patients = patientRepository.findPatientAgeGreaterThan(age);
		if(!(patients.isEmpty()))
		{
			response.setData(patients);
			response.setMessage("The Patient Record Fetched Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Patient>>>(response,HttpStatus.OK);
		}
		else
		{
			throw new RecordNotFoundException("The Record is not found in the Database");
		}
	}
	
	@GetMapping("/appointment/{appointmentId}")
	public ResponseEntity<ResponseStructure<List<Patient>>> fetchPatientByAppointmentId(@PathVariable Integer id)
	{
		ResponseStructure<List<Patient>> response = new ResponseStructure<List<Patient>>();
		List<Patient> patients = patientRepository.findPatientByAppointmentId(id);
		if(!(patients.isEmpty()))
		{
			response.setData(patients);
			response.setMessage("The Patient Record Fetched Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Patient>>>(response,HttpStatus.OK);
		}
		else
		{
			throw new RecordNotFoundException("The Record is not found in the Database");
		}
	}
	
	@GetMapping("/medicalRecord/{recordId}")
	public ResponseEntity<ResponseStructure<List<Patient>>> fetchPatientByMedicalRecordId(@PathVariable Integer Id)
	{
		ResponseStructure<List<Patient>> response = new ResponseStructure<List<Patient>>();
		List<Patient> patients = patientRepository.findPatientByRecordId(Id);
		if(!(patients.isEmpty()))
		{
			response.setData(patients);
			response.setMessage("The Patient Record Fetched Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Patient>>>(response,HttpStatus.OK);
		}
		else
		{
			throw new RecordNotFoundException("The Record is not found in the Database");
		}
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<Patient>> updatePatient(@RequestBody Patient patient)
	{
		ResponseStructure<Patient> response = new ResponseStructure<Patient>();
		if(patient.getPatientId()==null)
		{
			response.setMessage("Id is Required to Update the Record");
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<ResponseStructure<Patient>>(response,HttpStatus.NOT_FOUND);
		}
		Optional<Patient> opt = patientRepository.findById(patient.getPatientId());
		if(opt.isPresent())
		{
			response.setData(patientRepository.save(opt.get()));
			response.setMessage("The Patient Record Updated Successfully");
			response.setStatusCode(HttpStatus.CREATED.value());
			return new ResponseEntity<ResponseStructure<Patient>>(response,HttpStatus.CREATED);
		}
		else
		{
			throw new RecordNotFoundException("The Record is not found in the Database");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deletePatient(@PathVariable Integer id)
	{
		ResponseStructure<String> response = new ResponseStructure<String>();
		Optional<Patient>opt = patientRepository.findById(id);
		if(opt.isPresent())
		{
			patientRepository.delete(opt.get());
			response.setMessage("The patient Record Deleted Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.OK);
		}
		else
		{
			throw new IdNotFoundException("Id is Required to Delete the Record");
		}
	}
		
	
}
