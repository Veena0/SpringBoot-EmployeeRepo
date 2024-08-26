package com.vir.demo;

import org.junit.jupiter.api.BeforeEach;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vir.exception.EmployeeNotFoundException;
import com.vir.model.Employee;
import com.vir.model.UserInfo;
import com.vir.repo.EmployeeRepository;
import com.vir.repo.UserRepository;
import com.vir.service.EmployeeService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @Mock
    private UserRepository userRepository;
    
    @Mock
	private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private EmployeeService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAllEmployees_thenReturnEmployeeList() {
        Employee emp1 = new Employee(1L, "John Doe", "Developer", 100000);
        Employee emp2 = new Employee(2L, "Jane Smith", "Manager", 50000);

        when(repository.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        List<Employee> employees = service.getAllEmployees();

        assertEquals(2, employees.size());
        assertEquals("John Doe", employees.get(0).getName());
        assertEquals("Jane Smith", employees.get(1).getName());
    }

    @Test
    void whenGetEmployeeById_thenReturnEmployee() throws EmployeeNotFoundException {
        Employee employee = new Employee(1L, "John Doe", "Developer", 1000000);

        when(repository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> foundEmployee = service.getEmployeeById(1L);

        assertTrue(foundEmployee.isPresent());
        assertEquals("John Doe", foundEmployee.get().getName());
    }

    @Test
    void whenGetEmployeeById_thenReturnEmpty() throws EmployeeNotFoundException {
        when(repository.findById(111L)).thenReturn(Optional.empty());

        Optional<Employee> foundEmployee = service.getEmployeeById(111L);

        assertFalse(foundEmployee.isPresent());
        }

    @Test
    void whenCreateEmployee_thenReturnEmployee() {
        Employee employee = new Employee(1L, "John Doe", "Developer", 100000);

        when(repository.save(employee)).thenReturn(employee);

        Employee createdEmployee = service.saveEmployee(employee);

        assertEquals("John Doe", createdEmployee.getName());
        verify(repository, times(1)).save(employee);
    }
    
    @Test
    void whenCreateUser_thenReturnUser() {
       UserInfo user = new UserInfo(1, "user", "user@gmail.com", passwordEncoder.encode("password"), "ROLE_USER");

        when(userRepository.save(user)).thenReturn(user);

        String message = service.addUser(user);

        assertEquals("user added to the system", message);
        verify(userRepository, times(1)).save(user);
    }
    
      @Test
    void whenDeleteEmployee_thenNoException() {
        doNothing().when(repository).deleteById(1L);

        service.deleteEmployee(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
