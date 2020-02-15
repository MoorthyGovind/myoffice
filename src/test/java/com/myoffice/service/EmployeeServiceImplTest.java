package com.myoffice.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.myoffice.dto.ApprovalEmpDto;
import com.myoffice.entity.Employee;
import com.myoffice.repository.EmployeeRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeeServiceImplTest {
	@InjectMocks
	EmployeeServiceImpl employeeServiceImpl;
	@Mock
	EmployeeRepository employeeRepository;

	Employee emp = new Employee();
	List<Employee> employees = new ArrayList<Employee>();

	@Before
	public void init() {
		emp.setEmployeeId(1);
		emp.setEmployeeName("amala");
		employees.add(emp);
	}

	@Test
	public void testGetAllApprovalEmployee() {
		Mockito.when(employeeRepository.findAll()).thenReturn(employees);

		List<ApprovalEmpDto> allApprovalEmployee = employeeServiceImpl.getAllApprovalEmployee();
		assertThat(allApprovalEmployee).hasSize(1);
	}

}
