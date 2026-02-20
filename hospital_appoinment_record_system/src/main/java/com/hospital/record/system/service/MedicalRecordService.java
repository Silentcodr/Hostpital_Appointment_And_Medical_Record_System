package com.hospital.record.system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hospital.record.system.dto.ResponseStructure;
import com.hospital.record.system.entity.Appointment;
import com.hospital.record.system.entity.Department;
import com.hospital.record.system.entity.MedicalRecord;
import com.hospital.record.system.exception.IdNotFoundException;
import com.hospital.record.system.exception.RecordNotFoundException;
import com.hospital.record.system.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService 
{
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<MedicalRecord>> saveMedicalRecord(@RequestBody MedicalRecord medicalRecord)
	{
		ResponseStructure<MedicalRecord> response = new ResponseStructure<MedicalRecord>();
		response.setData(medicalRecordRepository.save(medicalRecord));
		response.setMessage("The Department Saved Successfully");
		response.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<MedicalRecord>>(response,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<MedicalRecord>>> fetchAllMedicalRecord()
	{
		ResponseStructure<List<MedicalRecord>> response = new ResponseStructure<List<MedicalRecord>>();
		response.setData(medicalRecordRepository.findAll());
		response.setMessage("The Medical Record Fetched Successfully");
		response.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<MedicalRecord>>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<MedicalRecord>> fetchMedicalRecordById(@PathVariable Integer id)
	{
		ResponseStructure<MedicalRecord> response = new ResponseStructure<MedicalRecord>();
		Optional<MedicalRecord> opt = medicalRecordRepository.findById(id);
		if(opt.isPresent())
		{
			response.setData(opt.get());
			response.setMessage("The Medical Record Fetched Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<MedicalRecord>>(response,HttpStatus.OK);
		}
		else
		{
			throw new IdNotFoundException("The Id is not found in the Database");
		}
	}
	
	@GetMapping("/doctor/{doctorId}")
	public ResponseEntity<ResponseStructure<List<MedicalRecord>>> fetchMedicalRecordByDoctorId(@PathVariable Integer doctorId) 
	{
		ResponseStructure<List<MedicalRecord>> response = new ResponseStructure<List<MedicalRecord>>();
		List<MedicalRecord> medicalRecords = medicalRecordRepository.findMedicalRecordByDoctorId(doctorId);
		if(!medicalRecords.isEmpty())
		{
			response.setData(medicalRecords);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Appointment Fetched Successfully");
			return new ResponseEntity<ResponseStructure<List<MedicalRecord>>>(response,HttpStatus.OK);
		}
		else
		{
			throw new RecordNotFoundException("The Record not Available in the Database");
		}
	}
	
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<ResponseStructure<List<MedicalRecord>>> fetchMedicalRecordByPatientId(@PathVariable Integer patientId)
	{
		ResponseStructure<List<MedicalRecord>> response = new ResponseStructure<List<MedicalRecord>>();
		List<MedicalRecord> medicalRecords = medicalRecordRepository.findMedicalRecordByPatientId(patientId);
		if(!medicalRecords.isEmpty())
		{
			response.setData(medicalRecords);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Appointment Fetched Successfully");
			return new ResponseEntity<ResponseStructure<List<MedicalRecord>>>(response,HttpStatus.OK);
		}
		else
		{
			throw new RecordNotFoundException("The Record not Available in the Database");
		}
	}
	
	@GetMapping("/appointmentId/{appointmentId}")
	public ResponseEntity<ResponseStructure<List<MedicalRecord>>> fetchMedicalRecordByAppointmentId(@PathVariable Integer appointmentId)
	{
		ResponseStructure<List<MedicalRecord>> response = new ResponseStructure<List<MedicalRecord>>();
		List<MedicalRecord> medicalRecords = medicalRecordRepository.findMedicalRecordByAppointmentId(appointmentId);
		if(!medicalRecords.isEmpty())
		{
			response.setData(medicalRecords);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Appointment Fetched Successfully");
			return new ResponseEntity<ResponseStructure<List<MedicalRecord>>>(response,HttpStatus.OK);
		}
		else
		{
			throw new RecordNotFoundException("The Record not Available in the Database");
		}
	}
	
	@GetMapping("/visitDate/{visitDate}")
	public ResponseEntity<ResponseStructure<List<MedicalRecord>>> fetchMedicalRecordByVisitDate(@PathVariable LocalDate visitDate)
	{
		ResponseStructure<List<MedicalRecord>> response = new ResponseStructure<List<MedicalRecord>>();
		List<MedicalRecord> medicalRecords = medicalRecordRepository.findMedicalRecordByVisitDate(visitDate);
		if(!medicalRecords.isEmpty())
		{
			response.setData(medicalRecords);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Appointment Fetched Successfully");
			return new ResponseEntity<ResponseStructure<List<MedicalRecord>>>(response,HttpStatus.OK);
		}
		else
		{
			throw new RecordNotFoundException("The Record not Available in the Database");
		}
	}
	
}
