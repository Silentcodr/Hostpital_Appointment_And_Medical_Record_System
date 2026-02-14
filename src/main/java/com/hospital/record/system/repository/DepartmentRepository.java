package com.hospital.record.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.record.system.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> 
{
	List<Department> findDepartmentByDeptName(String name);
}
