package com.vir.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vir.model.Employee;
import com.vir.model.UserInfo;
import com.vir.repo.EmployeeRepository;
import com.vir.repo.UserRepository;

@Service
public class EmployeeService implements IEmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> getEmployeeById(Long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public Employee saveEmployee(Employee employee) {

		return employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployee(Long id) {
		employeeRepository.deleteById(id);
	}
  
	public String addUser(UserInfo userInfo) {
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		userRepository.save(userInfo);
		return "user added to the system";
		
	}
}
