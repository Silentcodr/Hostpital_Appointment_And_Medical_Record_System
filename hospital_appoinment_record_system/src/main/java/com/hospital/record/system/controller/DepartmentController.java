package com.hospital.record.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hospital.record.system.dto.ResponseStructure;
import com.hospital.record.system.entity.Department;
import com.hospital.record.system.service.DepartmentService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/department")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Department>> saveDepartment(@RequestBody Department department) {
		return departmentService.saveDepartment(department);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<List<Department>>> fetchAllDepartment() {
		return departmentService.fetchAllDepartment();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Department>> fetchDepartmentById(@PathVariable Integer id) {
		return departmentService.fetchDepartmentById(id);
	}

	@PostMapping("/update")
	public ResponseEntity<ResponseStructure<Department>> updateDepartment(@RequestBody Department department) {
		return departmentService.updateDepartment(department);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteById(@PathVariable Integer id) {
		return departmentService.deleteById(id);
	}
}
