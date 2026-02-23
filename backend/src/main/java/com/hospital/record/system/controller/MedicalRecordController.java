package com.hospital.record.system.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hospital.record.system.dto.ResponseStructure;
import com.hospital.record.system.entity.MedicalRecord;
import com.hospital.record.system.service.MedicalRecordService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    // Save medical record
    @PostMapping
    public ResponseEntity<ResponseStructure<MedicalRecord>> saveMedicalRecord(
            @RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.saveMedicalRecord(medicalRecord);
    }

    // Fetch all medical records
    @GetMapping
    public ResponseEntity<ResponseStructure<List<MedicalRecord>>> fetchAllMedicalRecord() {
        return medicalRecordService.fetchAllMedicalRecord();
    }

    // Fetch by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<MedicalRecord>> fetchMedicalRecordById(
            @PathVariable Integer id) {
        return medicalRecordService.fetchMedicalRecordById(id);
    }

    // Fetch by doctor id
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ResponseStructure<List<MedicalRecord>>> fetchMedicalRecordByDoctorId(
            @PathVariable Integer doctorId) {
        return medicalRecordService.fetchMedicalRecordByDoctorId(doctorId);
    }

    // Fetch by patient id
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ResponseStructure<List<MedicalRecord>>> fetchMedicalRecordByPatientId(
            @PathVariable Integer patientId) {
        return medicalRecordService.fetchMedicalRecordByPatientId(patientId);
    }

    // Fetch by appointment id
    @GetMapping("/appointmentId/{appointmentId}")
    public ResponseEntity<ResponseStructure<List<MedicalRecord>>> fetchMedicalRecordByAppointmentId(
            @PathVariable Integer appointmentId) {
        return medicalRecordService.fetchMedicalRecordByAppointmentId(appointmentId);
    }

    // Fetch by visit date
    @GetMapping("/visitDate/{visitDate}")
    public ResponseEntity<ResponseStructure<List<MedicalRecord>>> fetchMedicalRecordByVisitDate(
            @PathVariable LocalDate visitDate) {
        return medicalRecordService.fetchMedicalRecordByVisitDate(visitDate);
    }
}
