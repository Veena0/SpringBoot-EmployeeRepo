package com.vir.model;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Employee {

@Id	
@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
@NotBlank(message = "Name is mandatory")
@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
	private String name;
@NotBlank(message = "Department is mandatory")
	private String department;
@NotBlank(message = "Department is mandatory")
	private double salary;
	
	
	public Employee() {
		super();
	}
	
	public Employee(Long id, String name, String department, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.salary = salary;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
}
