package com.hospital.record.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.record.system.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> 
{
	
}
