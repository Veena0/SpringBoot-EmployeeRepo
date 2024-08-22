package com.vir.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vir.model.Employee;
import com.vir.model.UserInfo;
import com.vir.service.IEmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	 @Autowired
	    private IEmployeeService employeeService;

	    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class.getName());
	    
	    @GetMapping("/home") 
		  public String welcome() {
			  return "Welcome to the site this page was not secured"; // Return the view name 
			  }

	    @GetMapping("/all")
	    public ResponseEntity<List<Employee>> getAllEmployees() {
	        List<Employee> employees = employeeService.getAllEmployees();
	        return new ResponseEntity<>(employees, HttpStatus.OK);
	    }

	   
	    @GetMapping("/{id}")
	    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
	        Optional<Employee> employee = employeeService.getEmployeeById(id);
	        if (employee.isPresent()) {
	            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
	        } else {
	            logger.warn("Employee with ID " + id + " not found");
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	    @PostMapping
	    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
	        Employee createdEmployee = employeeService.saveEmployee(employee);
	        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) {
	        if (employeeService.getEmployeeById(id).isPresent()) {
	            employee.setId(id);
	            Employee updatedEmployee = employeeService.saveEmployee(employee);
	            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
	        } else {
	            logger.warn("Employee with ID " + id + " not found for update");
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
	        if (employeeService.getEmployeeById(id).isPresent()) {
	            employeeService.deleteEmployee(id);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } else {
	            logger.warn("Employee with ID " + id + " not found for deletion");
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    
	    @PostMapping("/new")
	    public String addNewUser(@RequestBody UserInfo userInfo) {
	    	
	    	return employeeService.addUser(userInfo);
	    }

}
