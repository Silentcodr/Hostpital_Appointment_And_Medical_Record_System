package com.hospital.record.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hospital.record.system.dto.ResponseStructure;
import com.hospital.record.system.entity.Prescription;
import com.hospital.record.system.service.PrescriptionService;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    // Save prescription
    @PostMapping
    public ResponseEntity<ResponseStructure<Prescription>> savePrescription(
            @RequestBody Prescription prescription) {
        return prescriptionService.savePrescription(prescription);
    }

    // Fetch all prescriptions
    @GetMapping
    public ResponseEntity<ResponseStructure<List<Prescription>>> fetchAllPrescription() {
        return prescriptionService.fetchAllPrescription();
    }

    // Fetch prescription by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Prescription>> fetchPrescriptionById(
            @PathVariable Integer id) {
        return prescriptionService.fetchPrescriptionById(id);
    }

    // Fetch by medical record id
    @GetMapping("/medicalRecordId/{medicalRecordId}")
    public ResponseEntity<ResponseStructure<List<Prescription>>> fetchPrecriptionByMedicalRecordId(
            @PathVariable Integer medicalRecordId) {
        return prescriptionService.fetchPrecriptionByMedicalRecordId(medicalRecordId);
    }

    // Fetch by patient id
    @GetMapping("/patientId/{patientId}")
    public ResponseEntity<ResponseStructure<List<Prescription>>> fetchPrecriptionByPatientId(
            @PathVariable Integer patientId) {
        return prescriptionService.fetchPrecriptionByPatientId(patientId);
    }
}