package com.myoffice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myoffice.constant.AppConstant;
import com.myoffice.dto.ApprovalRequestDto;
import com.myoffice.dto.ApprovalResponseDto;
import com.myoffice.exception.EmployeeNotFoundException;
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
			@Valid @RequestBody ApprovalRequestDto approvalRequestDto) throws EmployeeNotFoundException, JsonProcessingException {
		log.info("new employee approval by admin...");
		ApprovalResponseDto approvalResponseDto = employeeService.employeeApproval(adminId, approvalRequestDto);
		approvalResponseDto.setStatusCode(HttpStatus.OK.value());
		approvalResponseDto.setMessage(AppConstant.APPROVED_EMPLOYEE_SUCCESSFULLY);
		return new ResponseEntity<>(approvalResponseDto, HttpStatus.OK);
	}

}
