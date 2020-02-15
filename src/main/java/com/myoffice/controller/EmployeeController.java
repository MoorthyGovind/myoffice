package com.myoffice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myoffice.constant.AppConstant;
import com.myoffice.dto.ApprovalRequestDto;
import com.myoffice.dto.ApprovalResponseDto;
import com.myoffice.exception.EmployeeNotFoundException;
import com.myoffice.dto.ApprovalEmpDto;
import com.myoffice.dto.ApprovalEmployeeResponseDto;
import com.myoffice.dto.LoginRequestDto;
import com.myoffice.dto.LoginResponseDto;
import com.myoffice.dto.RegistrationRequestDto;
import com.myoffice.dto.RegistrationResponceDto;
import com.myoffice.exception.UserNotFoundException;
import com.myoffice.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

/**
 * Get the employee activities such as create a new employee and approval stage.
 * 
 * @author Goivndasamy.C
 * @since 15-02-2020
 * @version V1.1
 *
 */
@RestController
@RequestMapping("/employees")
@CrossOrigin
@Slf4j
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;

	@PostMapping("/registration")
	public ResponseEntity<RegistrationResponceDto> empRegistartion(
			@Valid @RequestBody RegistrationRequestDto registrationRequestDto) {
		log.info("check the empoyee registration");
		RegistrationResponceDto empRegistartion = employeeService.empRegistartion(registrationRequestDto);
		empRegistartion.setStatusCode(HttpStatus.OK.value());
		empRegistartion.setMessage(AppConstant.SUCCESS_MESSAGE);
		return new ResponseEntity<>(empRegistartion, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> authenticateEmployee(@Valid @RequestBody LoginRequestDto loginRequestDto)
			throws UserNotFoundException {
		return ResponseEntity.ok().body(employeeService.authenticateEmployee(loginRequestDto));
	}

	@GetMapping
	public ResponseEntity<ApprovalEmployeeResponseDto> getAllApprovalEmployee() {
		ApprovalEmployeeResponseDto approvalEmployeeResponseDto = new ApprovalEmployeeResponseDto();

		List<ApprovalEmpDto> allApprovalEmployee = employeeService.getAllApprovalEmployee();
		approvalEmployeeResponseDto.setMessage(AppConstant.SUCCESS_MESSAGE);
		approvalEmployeeResponseDto.setStatusCode(HttpStatus.OK.value());
		approvalEmployeeResponseDto.setApprovalEmp(allApprovalEmployee);
		return new ResponseEntity<>(approvalEmployeeResponseDto, HttpStatus.OK);

	}

	/**
	 * Create a new employee approval by admin
	 * 
	 * @param approvalRequestDto
	 * @return
	 * @throws EmployeeNotFoundException
	 * @author Govindasamy.C
	 * @throws JsonProcessingException
	 * @since 15-02-2020
	 */
	@PostMapping("/{adminId}/approval")
	public ResponseEntity<ApprovalResponseDto> employeeApproval(@PathVariable Integer adminId,
			@Valid @RequestBody ApprovalRequestDto approvalRequestDto)
			throws EmployeeNotFoundException, JsonProcessingException {
		log.info("new employee approval by admin...");
		ApprovalResponseDto approvalResponseDto = employeeService.employeeApproval(adminId, approvalRequestDto);
		approvalResponseDto.setStatusCode(HttpStatus.OK.value());
		approvalResponseDto.setMessage(AppConstant.APPROVED_EMPLOYEE_SUCCESSFULLY);
		return new ResponseEntity<>(approvalResponseDto, HttpStatus.OK);
	}

}
