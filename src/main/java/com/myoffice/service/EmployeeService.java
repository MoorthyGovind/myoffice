package com.myoffice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myoffice.dto.ApprovalRequestDto;
import com.myoffice.dto.ApprovalResponseDto;
import com.myoffice.exception.EmployeeNotFoundException;

import java.util.List;

import javax.validation.Valid;

import com.myoffice.dto.ApprovalEmpDto;
import com.myoffice.dto.LoginRequestDto;
import com.myoffice.dto.LoginResponseDto;
import com.myoffice.dto.RegistrationRequestDto;
import com.myoffice.dto.RegistrationResponceDto;
import com.myoffice.exception.UserNotFoundException;

public interface EmployeeService {

	public ApprovalResponseDto employeeApproval(Integer adminId, ApprovalRequestDto approvalRequestDto)
			throws EmployeeNotFoundException, JsonProcessingException;

	RegistrationResponceDto empRegistartion(@Valid RegistrationRequestDto registrationRequestDto);

	LoginResponseDto authenticateEmployee(@Valid LoginRequestDto loginRequestDto) throws UserNotFoundException;

	List<ApprovalEmpDto> getAllApprovalEmployee();
}
