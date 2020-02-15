package com.myoffice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myoffice.dto.ApprovalRequestDto;
import com.myoffice.dto.ApprovalResponseDto;
import com.myoffice.exception.EmployeeNotFoundException;

public interface EmployeeService {

	public ApprovalResponseDto employeeApproval(Integer adminId, ApprovalRequestDto approvalRequestDto)
			throws EmployeeNotFoundException, JsonProcessingException;
}
