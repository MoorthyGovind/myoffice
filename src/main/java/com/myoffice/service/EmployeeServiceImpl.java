package com.myoffice.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myoffice.constant.AppConstant;
import com.myoffice.dto.ApprovalRequestDto;
import com.myoffice.dto.ApprovalResponseDto;
import com.myoffice.dto.EmployeeDto;
import com.myoffice.entity.Employee;
import com.myoffice.entity.EmployeeApproval;
import com.myoffice.exception.EmployeeNotFoundException;
import com.myoffice.repository.EmployeeApprovalRepository;
import com.myoffice.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EmployeeApprovalRepository employeeApprovalRepository;

	@Autowired
	MessageSender messageSender;

	@Override
	public ApprovalResponseDto employeeApproval(Integer adminId, ApprovalRequestDto approvalRequestDto)
			throws EmployeeNotFoundException, JsonProcessingException {
		log.info("new employee approval by admin...");

		Optional<Employee> admin = employeeRepository.findById(adminId);
		if (!admin.isPresent()) {
			throw new EmployeeNotFoundException(AppConstant.ADMIN_NOT_FOUND);
		}
		
		Optional<Employee> employee = employeeRepository.findById(approvalRequestDto.getEmployeeId());
		if (!employee.isPresent()) {
			throw new EmployeeNotFoundException(AppConstant.EMPLOYEE_NOT_FOUND);
		}
		
		//String employeePassword = generatePassword();
		Employee updateEmployee = employee.get(); 
		updateEmployee.setActiveStatus(true);
		employeeRepository.save(updateEmployee);
		
		EmployeeApproval employeeApproval = new EmployeeApproval();
		employeeApproval.setApprover(admin.get());
		employeeApproval.setEmployee(employee.get());
		employeeApproval.setStatus(approvalRequestDto.getApprovalType());
		employeeApprovalRepository.save(employeeApproval);
		

		EmployeeDto employeeDto = new EmployeeDto();
		BeanUtils.copyProperties(employee.get(), employeeDto);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(employeeDto);

		log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		log.info("Application : sending employe request {}", jsonString);
		messageSender.sendMessage(jsonString);
		log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		ApprovalResponseDto approvalResponseDto = new ApprovalResponseDto();

		return approvalResponseDto;
	}

	/**
	 * Get the generated password by using alpha numeric characters
	 * 
	 * @return String of the generated password.
	 */
	public String generatePassword() {
		return RandomStringUtils.random(6, true, true);
	}

}
