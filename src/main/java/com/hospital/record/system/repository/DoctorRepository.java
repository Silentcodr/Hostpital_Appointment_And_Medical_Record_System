package com.hospital.record.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.record.system.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor,Integer>{

}
