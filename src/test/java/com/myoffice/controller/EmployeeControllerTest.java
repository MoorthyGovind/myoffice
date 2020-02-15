package com.myoffice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.myoffice.dto.ApprovalEmpDto;
import com.myoffice.dto.ApprovalEmployeeResponseDto;
import com.myoffice.dto.LoginRequestDto;
import com.myoffice.dto.LoginResponseDto;
import com.myoffice.dto.RegistrationRequestDto;
import com.myoffice.dto.RegistrationResponceDto;
import com.myoffice.exception.UserNotFoundException;
import com.myoffice.service.EmployeeService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeeControllerTest {
	@InjectMocks
	EmployeeController employeeController;
	@Mock
	EmployeeService employeeService;
	
	@Before
	public void init() {

	}


	@Test
	public void testEmpRegistartion() {

		RegistrationRequestDto registrationRequestDto = new RegistrationRequestDto();
		RegistrationResponceDto registrationResponceDto = new RegistrationResponceDto();

		Mockito.when(employeeService.empRegistartion(registrationRequestDto)).thenReturn(registrationResponceDto);

		ResponseEntity<RegistrationResponceDto> empRegistartion = employeeController
				.empRegistartion(registrationRequestDto);

		assertEquals(200, empRegistartion.getStatusCode().value());
	}
	
	@Test
	public void testAuthenticateEmployee() throws UserNotFoundException {
		LoginResponseDto loginResponseDto=new LoginResponseDto();
		LoginRequestDto loginRequestDto=new LoginRequestDto();
		
		Mockito.when(employeeService.authenticateEmployee(loginRequestDto)).thenReturn(loginResponseDto);
		
		ResponseEntity<LoginResponseDto> authenticateEmployee = employeeController.authenticateEmployee(loginRequestDto);
		assertEquals(200,authenticateEmployee.getStatusCodeValue());
		
	}
	
	
}
