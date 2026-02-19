package com.hospital.record.system.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hospital.record.system.dto.ResponseStructure;
import com.hospital.record.system.entity.Appointment;
import com.hospital.record.system.exception.IdNotFoundException;
import com.hospital.record.system.exception.RecordNotFoundException;
import com.hospital.record.system.repository.AppointmentRepository;



@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@PutMapping
	public ResponseEntity<ResponseStructure<Appointment>> bookAppoinment(@RequestBody Appointment appointment)
	{
		ResponseStructure<Appointment> response = new ResponseStructure<Appointment>();
		response.setData(appointmentRepository.save(appointment));
		response.setMessage("The Appointment Booked Successfully");
		response.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<Appointment>>(response,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Appointment>>> getAllAppointment()
	{
		ResponseStructure<List<Appointment>> response = new ResponseStructure<>();
		response.setData(appointmentRepository.findAll());
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All the Appointments are Fetched Successfully");
		return new ResponseEntity<ResponseStructure<List<Appointment>>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Appointment>> fetchAppointmentById(@PathVariable Integer id)
	{
		ResponseStructure<Appointment> response = new ResponseStructure<Appointment>();
		Optional<Appointment>opt = appointmentRepository.findById(id);
		if(opt.isPresent())
		{
			response.setData(opt.get());
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Appointment Fetched Successfully");
			return new ResponseEntity<ResponseStructure<Appointment>>(response,HttpStatus.OK);
		}
		else
		{
			throw new IdNotFoundException("Id is not found in the Database");
		}
	}
	
	@GetMapping("/date/{date}")
	public ResponseEntity<ResponseStructure<List<Appointment>>> fetchAppointmentByDate(@PathVariable LocalDateTime date)
	{
		ResponseStructure<List<Appointment>> response = new ResponseStructure<List<Appointment>>();
		List<Appointment> appointments = appointmentRepository.findAppointmentByDate(date);
		if(!appointments.isEmpty())
		{
			response.setData(appointments);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Appointment Fetched Successfully");
			return new ResponseEntity<ResponseStructure<List<Appointment>>>(response,HttpStatus.OK);
		}
		else
		{
			throw new RecordNotFoundException("The Record not Available in the Database");
		}
		
		
	}
}

