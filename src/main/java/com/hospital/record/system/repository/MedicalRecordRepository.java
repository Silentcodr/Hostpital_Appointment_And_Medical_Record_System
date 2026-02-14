package com.hospital.record.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.record.system.entity.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {

}
