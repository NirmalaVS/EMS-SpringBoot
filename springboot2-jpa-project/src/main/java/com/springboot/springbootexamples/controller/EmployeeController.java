package com.springboot.springbootexamples.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.springbootexamples.model.Employee;
import com.springboot.springbootexamples.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@PostMapping(value = "/employees", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	@GetMapping(value = "/employees")
	@Cacheable(value="EmployeeDetails")
	public @ResponseBody List<Employee> getEmployeesList() {
		return employeeRepository.findAll();
	}

	@GetMapping(value = "/employees/{id}")
	public @ResponseBody String getEmployeeById(@PathVariable(value = "id") int employeeId)
			throws JsonProcessingException {
		Employee employee = employeeRepository.findByEmpId(employeeId);
		ObjectMapper obj = new ObjectMapper();
		return obj.writeValueAsString(employee);
	}

	@PutMapping(value = "/employees/{id}")
	public @ResponseBody Employee updateEmployeeById(@PathVariable(value = "id") int employeeId,
			@RequestBody Employee employee) {
		Employee employees = employeeRepository.findByEmpId(employeeId);
		employees.setAge(employee.getAge());
		employees.setExperience(employee.getExperience());
		employees.setName(employee.getName());
		employees.setQualification(employee.getQualification());
		return employeeRepository.save(employees);
	}
	
	@DeleteMapping(value = "/employees/{id}")
	public @ResponseBody String deleteEmployeeById(@PathVariable(value = "id") int employeeId) {
		Employee employees = employeeRepository.findByEmpId(employeeId);
		employeeRepository.delete(employees);
		return "OK";
	}
}
