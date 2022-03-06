package com.springboot.springbootexamples.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.springbootexamples.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query(value = "SELECT * FROM EMPLOYEE WHERE emp_id = ?1", nativeQuery = true)
	Employee findByEmpId(int empId);
}
