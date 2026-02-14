package com.hospital.record.system.service;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import com.hospital.record.system.dto.ResponseStructure;
import com.hospital.record.system.entity.Department;
import com.hospital.record.system.exception.IdNotFoundException;
import com.hospital.record.system.exception.RecordNotFoundException;
import com.hospital.record.system.repository.DepartmentRepository;



@Service
public class DepartmentService 
{
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Department>> saveDepartment(@RequestBody Department department)
	{
		ResponseStructure<Department> response = new ResponseStructure<Department>();
		response.setData(departmentRepository.save(department));
		response.setMessage("The Department Saved Successfully");
		response.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<Department>>(response,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Department>>> fetchAllDepartment()
	{
		ResponseStructure<List<Department>> response = new ResponseStructure<List<Department>>();
		response.setData(departmentRepository.findAll());
		response.setMessage("The Department Record Fetched Successfully");
		response.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Department>>>(response,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<Department>> fetchDepartmentById(@PathVariable Integer id)
	{
		ResponseStructure<Department> response = new ResponseStructure<Department>();
		Optional<Department> opt = departmentRepository.findById(id);
		if(opt.isPresent())
		{
			response.setData(opt.get());
			response.setMessage("The Department Record Fetched Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Department>>(response,HttpStatus.OK);
		}
		else
		{
			throw new IdNotFoundException("The Id is not found in the Database");
		}
		
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<Department>> updateDepartment(@RequestBody Department department)
	{
		ResponseStructure<Department> response = new ResponseStructure<Department>();
		if(department.getDepartmentId()==null)
		{
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setMessage("ID is required to Update the Record");
			return new ResponseEntity<ResponseStructure<Department>>(response,HttpStatus.NOT_FOUND);
		}
		Optional<Department> opt = departmentRepository.findById(department.getDepartmentId());
		if(opt.isPresent())
		{
			response.setData(departmentRepository.save(opt.get()));
			response.setMessage("The Department Record updated Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Department>>(response,HttpStatus.OK);
		}
		else
		{
			throw new IdNotFoundException("Id Does Not Exist in the Database");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteById(@PathVariable Integer Id)
	{
		ResponseStructure<String> response = new ResponseStructure<String>();
		Optional<Department> opt = departmentRepository.findById(Id);
		if(opt.isPresent())
		{
			departmentRepository.delete(opt.get());
			response.setMessage("The record Deleted Successfully");
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.OK);
		}
		else
		{
			throw new IdNotFoundException("Id is Required to Delete the Record");
		}
	}
	
	@GetMapping("/deptName")
	public ResponseEntity<ResponseStructure<List<Department>>> fetchByDepartmentName(@PathVariable String name)
	{
		ResponseStructure<List<Department>> response = new ResponseStructure<List<Department>>();
		List<Department> departments = departmentRepository.findDepartmentByDeptName(name);
		if(!departments.isEmpty())
		{
			response.setData(departments);
			response.setMessage("The Department Record Fetched Successfully according to the given name: "+name);
			response.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Department>>>(response,HttpStatus.OK);
		}
		else
		{
			throw new RecordNotFoundException("The Record not found to be Deleted");
		}
	}
	
	

}
