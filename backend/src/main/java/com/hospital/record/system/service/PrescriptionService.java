package com.hospital.record.system.service;

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
import com.hospital.record.system.entity.MedicalRecord;
import com.hospital.record.system.entity.Prescription;
import com.hospital.record.system.exception.IdNotFoundException;
import com.hospital.record.system.exception.RecordNotFoundException;
import com.hospital.record.system.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Prescription>> savePrescription(@RequestBody Prescription prescription)
	{
		ResponseStructure<Prescription> response = new ResponseStructure<Prescription>();
		response.setData(prescriptionRepository.save(prescription));
		response.setMessage("The Department Saved Successfully");
		response.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<Prescription>>(response,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Prescription>>> fetchAllPrescription()
	{
		ResponseStructure<List<Prescription>> response = new ResponseStructure<List<Prescription>>();
		response.setData(prescriptionRepository.findAll());
		response.setMessage("The Medical Record Fetched Successfully");
		response.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Prescription>>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Prescription>> fetchPrescriptionById(@PathVariable Integer id)
	{
		ResponseStructure<Prescription> response = new ResponseStructure<Prescription>();
		Optional<Prescription> opt = prescriptionRepository.findById(id);
		if(opt.isPresent())
		{
			response.setData(opt.get());
			response.setMessage("The Prescription Fetched Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Prescription>>(response,HttpStatus.OK);
		}
		else
		{
			throw new IdNotFoundException("The Id is not found in the Database");
		}
	}
	
	@GetMapping("/medicalRecordId/{medicalRecordId}")
	public ResponseEntity<ResponseStructure<List<Prescription>>> fetchPrecriptionByMedicalRecordId(@PathVariable Integer medicalRecordId) 
	{
		ResponseStructure<List<Prescription>> response = new ResponseStructure<List<Prescription>>();
		List<Prescription> medicalRecords = prescriptionRepository.findPrescriptionByMedicalRecordId(medicalRecordId);
		if(!medicalRecords.isEmpty())
		{
			response.setData(medicalRecords);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Prescription Fetched Successfully");
			return new ResponseEntity<ResponseStructure<List<Prescription>>>(response,HttpStatus.OK);
		}
		else
		{
			throw new RecordNotFoundException("The Record not Available in the Database");
		}
	}
	
	@GetMapping("/patientId/{patientId}")
	public ResponseEntity<ResponseStructure<List<Prescription>>> fetchPrecriptionByPatientId(@PathVariable Integer patientId) 
	{
		ResponseStructure<List<Prescription>> response = new ResponseStructure<List<Prescription>>();
		List<Prescription> medicalRecords = prescriptionRepository.findPrescriptionByPatientId(patientId);
		if(!medicalRecords.isEmpty())
		{
			response.setData(medicalRecords);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Prescription Fetched Successfully");
			return new ResponseEntity<ResponseStructure<List<Prescription>>>(response,HttpStatus.OK);
		}
		else
		{
			throw new RecordNotFoundException("The Record not Available in the Database");
		}
	}
}
