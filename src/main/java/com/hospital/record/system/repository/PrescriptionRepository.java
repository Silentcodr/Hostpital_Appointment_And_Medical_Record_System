package com.hospital.record.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.record.system.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer>{

}
