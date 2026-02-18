package com.hospital.record.system.service;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PutExchange;

import com.hospital.record.system.dto.ResponseStructure;
import com.hospital.record.system.entity.Department;
import com.hospital.record.system.entity.Doctor;
import com.hospital.record.system.exception.IdNotFoundException;
import com.hospital.record.system.exception.RecordNotFoundException;
import com.hospital.record.system.repository.DoctorRepository;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Doctor>> saveDoctor(@RequestBody Doctor doctor)
	{
		ResponseStructure<Doctor> response = new ResponseStructure<Doctor>();
		response.setData(doctorRepository.save(doctor));
		response.setMessage("Doctor record Saved Successfully");
		response.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<Doctor>>(response,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Doctor>>> fetchAllDoctors()
	{
		ResponseStructure<List<Doctor>> response = new ResponseStructure<List<Doctor>>();
		response.setData(doctorRepository.findAll());
		response.setMessage("Doctor record fetched successfully");
		response.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Doctor>>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Doctor>> fetchDoctorById(@PathVariable Integer id)
	{
		ResponseStructure<Doctor> response = new ResponseStructure<Doctor>();
		Optional<Doctor> opt = doctorRepository.findById(id);
		if(opt.isPresent())
		{
			response.setData(opt.get());
			response.setMessage("The Doctor record Fetched successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Doctor>>(response,HttpStatus.OK);
		}
		else
		{
			throw new IdNotFoundException("The Id is not found in the Database");
		}
	}
	
	@GetMapping("/specialization/{specialization}")
	public ResponseEntity<ResponseStructure<List<Doctor>>> fetchDoctorBySpecialization(@PathVariable String specialization)
	{
		ResponseStructure<List<Doctor>> response = new ResponseStructure<List<Doctor>>();
		List<Doctor> doctors = doctorRepository.findDoctorBySpecialization(specialization);
		if(!doctors.isEmpty())
		{
			response.setData(doctors);
			response.setMessage("The Records fetched Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Doctor>>>(response,HttpStatus.OK);	
		}
		else
		{
			throw new RecordNotFoundException("The Record not found in the Datatbase");
		}
	}
	
	@GetMapping("/department/{departmentName}")
	public ResponseEntity<ResponseStructure<List<Doctor>>> fetchDoctorByDepartmentName(@PathVariable String departmentName)
	{
		ResponseStructure<List<Doctor>> response = new ResponseStructure<List<Doctor>>();
		List<Doctor> doctors = doctorRepository.findDoctorByDepartmentName(departmentName);
		if(!doctors.isEmpty())
		{
			response.setData(doctors);
			response.setMessage("The Records fetched Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Doctor>>>(response,HttpStatus.OK);	
		}
		else
		{
			throw new RecordNotFoundException("The Record not found in the Datatbase");
		}
	}
	
	@GetMapping("/patient/{patientName}")
	public ResponseEntity<ResponseStructure<List<Doctor>>> fetchDoctorByPatientName(@PathVariable String patientName)
	{
		ResponseStructure<List<Doctor>> response = new ResponseStructure<List<Doctor>>();
		List<Doctor> doctors = doctorRepository.findDoctorByPatientName(patientName);
		if(!doctors.isEmpty())
		{
			response.setData(doctors);
			response.setMessage("The Records fetched Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Doctor>>>(response,HttpStatus.OK);	
		}
		else
		{
			throw new RecordNotFoundException("The Record not found in the Datatbase");
		}
	}
	
	@GetMapping("/appointment/{appointmentId}")
	public ResponseEntity<ResponseStructure<List<Doctor>>> fetchDoctorByAppointmentId(@PathVariable Integer id)
	{
		ResponseStructure<List<Doctor>> response = new ResponseStructure<List<Doctor>>();
		List<Doctor> doctors = doctorRepository.findDoctorByAppointmentId(id);
		if(!doctors.isEmpty())
		{
			response.setData(doctors);
			response.setMessage("The Records fetched Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Doctor>>>(response,HttpStatus.OK);	
		}
		else
		{
			throw new RecordNotFoundException("The Record not found in the Datatbase");
		}
	}
	
	@GetMapping("/availableDays/{day}")
	public ResponseEntity<ResponseStructure<List<Doctor>>> fetchDoctorByAvailableDays(@PathVariable String day)
	{
		ResponseStructure<List<Doctor>> response = new ResponseStructure<List<Doctor>>();
		List<Doctor> doctors = doctorRepository.findDoctorByAvailableDays(day);
		if(!doctors.isEmpty())
		{
			response.setData(doctors);
			response.setMessage("The Records fetched Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Doctor>>>(response,HttpStatus.OK);	
		}
		else
		{
			throw new RecordNotFoundException("The Record not found in the Datatbase");
		}
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<Doctor>> updateDepartment(@RequestBody Doctor doctor)
	{
		ResponseStructure<Doctor> response = new ResponseStructure<Doctor>();
		if(doctor.getDoctorId()==null)
		{
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setMessage("ID is required to Update the Record");
			return new ResponseEntity<ResponseStructure<Doctor>>(response,HttpStatus.NOT_FOUND);
		}
		Optional<Doctor> opt = doctorRepository.findById(doctor.getDoctorId());
		if(opt.isPresent())
		{
			response.setData(doctorRepository.save(opt.get()));
			response.setMessage("The Department Record updated Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Doctor>>(response,HttpStatus.OK);
		}
		else
		{
			throw new IdNotFoundException("Id Does Not Exist in the Database");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteDoctorById(@PathVariable Integer Id)
	{
		ResponseStructure<String> response = new ResponseStructure<String>();
		Optional<Doctor> opt = doctorRepository.findById(Id);
		if(opt.isPresent())
		{
			doctorRepository.delete(opt.get());
			response.setMessage("The record Deleted Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.OK);
		}
		else
		{
			throw new IdNotFoundException("Id is Required to Delete the Record");
		}
	}
}
