package com.vir.service;

import java.util.List;
import java.util.Optional;

import com.vir.exception.EmployeeNotFoundException;
import com.vir.model.Employee;
import com.vir.model.UserInfo;

public interface IEmployeeService {

	public List<Employee> getAllEmployees();

	public Optional<Employee> getEmployeeById(Long id) throws EmployeeNotFoundException;

	public Employee saveEmployee(Employee employee);

	public void deleteEmployee(Long id);
	
	public String addUser(UserInfo userInfo);

}
