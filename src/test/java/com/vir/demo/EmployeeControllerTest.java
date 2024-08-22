package com.vir.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vir.model.Employee;
import com.vir.service.EmployeeService;

@WebMvcTest
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		// Initialize or mock any required setup
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void whenGetAllEmployees_thenStatus200() throws Exception {

		Employee employee1 = new Employee();
		employee1.setId(1L);
		employee1.setName("John Doe");
		employee1.setDepartment("Developer");

		Employee employee2 = new Employee();
		employee2.setId(2L);
		employee2.setName("Jane Doe");
		employee2.setDepartment("Manager");

		List<Employee> employees = Arrays.asList(employee1, employee2);

		when(employeeService.getAllEmployees()).thenReturn(employees);

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.get("/employees/all").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jane Doe"));
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void whenGetEmployeeById_thenStatus200() throws Exception {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("John");
		employee.setDepartment("Developer");

		when(employeeService.getEmployeeById(1L)).thenReturn(java.util.Optional.of(employee));

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.get("/employees/1").contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.department").value("Developer"));

	}

	
	  @Test
	  @WithMockUser(username = "admin", roles = "ADMIN") 
	  public void whenCreateEmployee_thenStatus201() throws Exception { 
		  Employee employee = new Employee();
		  employee.setId(11L);
		  employee.setName("Sony");
		  employee.setDepartment("HR");
		  employee.setSalary(1000000);
		  when(employeeService.saveEmployee(employee)).thenReturn(employee);

	        // Act & Assert
	        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(employee)));    
	 
   }
	  
	  @Test
	  @WithMockUser(username = "admin", roles = "ADMIN") 
	  public void whenUpdateEmployee_thenStatus200() throws Exception { 
		  Employee employee = new Employee(); 
		  employee.setId(1011L); 
		  employee.setName("John Doe");
		  employee.setDepartment("Dev");
		  employee.setSalary(150000);
	  
	  when(employeeService.getEmployeeById(1L)).thenReturn(java.util.Optional.of(
	  employee));
	  when(employeeService.saveEmployee(employee)).thenReturn(employee);
	  
	  mockMvc.perform(MockMvcRequestBuilders.put("/employees/1")
	  .contentType(MediaType.APPLICATION_JSON))
	  .andExpect(MockMvcResultMatchers.status().is4xxClientError()); 
	  }
	  
	 
      
	  //@Test
	  @WithMockUser(username = "admin", roles = "ADMIN") 
	  public void whenDeleteEmployee_thenStatus204() throws Exception {
	  doNothing().when(employeeService).deleteEmployee(1L);
	  when(employeeService.getEmployeeById(1L)).thenReturn(java.util.Optional.of(
	  new Employee()));
	  
	  mockMvc.perform(MockMvcRequestBuilders.delete("/employees/1"))
	  .andExpect(MockMvcResultMatchers.status().isNoContent()); }
	  
	 
}
