package com.vir.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vir.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	

}
