package com.hospital.record.system.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hospital.record.system.dto.ResponseStructure;
import com.hospital.record.system.entity.Appointment;
import com.hospital.record.system.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Book appointment
    @PostMapping
    public ResponseEntity<ResponseStructure<Appointment>> bookAppointment(
            @RequestBody Appointment appointment) {
        return appointmentService.bookAppoinment(appointment);
    }

    // Get all appointments
    @GetMapping
    public ResponseEntity<ResponseStructure<List<Appointment>>> getAllAppointments() {
        return appointmentService.getAllAppointment();
    }

    // Get by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Appointment>> getAppointmentById(
            @PathVariable Integer id) {
        return appointmentService.fetchAppointmentById(id);
    }

    // Get by date
    @GetMapping("/date/{date}")
    public ResponseEntity<ResponseStructure<List<Appointment>>> getByDate(
            @PathVariable LocalDateTime date) {
        return appointmentService.fetchAppointmentByDate(date);
    }

    // Get by doctor
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ResponseStructure<List<Appointment>>> getByDoctor(
            @PathVariable Integer doctorId) {
        return appointmentService.fetchAppointmentByDoctorId(doctorId);
    }

    // Get by patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ResponseStructure<List<Appointment>>> getByPatient(
            @PathVariable Integer patientId) {
        return appointmentService.fetchAppointmentByPatientId(patientId);
    }

    // Get by status
    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseStructure<List<Appointment>>> getByStatus(
            @PathVariable String status) {
        return appointmentService.fetchAppointmentByStatus(status);
    }

    // Cancel appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> cancelAppointment(
            @PathVariable Integer id) {
        return appointmentService.cancelAppointment(id);
    }

    // Update appointment
    @PutMapping
    public ResponseEntity<ResponseStructure<Appointment>> updateAppointment(
            @RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(appointment);
    }
}