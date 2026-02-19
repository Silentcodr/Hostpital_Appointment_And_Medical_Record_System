package com.hospital.record.system.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.record.system.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> 
{
	List<Appointment> findAppointmentByDate(LocalDateTime date);
}
