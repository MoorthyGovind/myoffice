package com.myoffice.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang.RandomStringUtils;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myoffice.common.MyOfficeEnum;
import com.myoffice.constant.AppConstant;
import com.myoffice.dto.ApprovalRequestDto;
import com.myoffice.dto.ApprovalResponseDto;
import com.myoffice.dto.EmployeeDto;
import com.myoffice.entity.Employee;
import com.myoffice.entity.EmployeeApproval;
import com.myoffice.exception.EmployeeNotFoundException;
import com.myoffice.repository.EmployeeApprovalRepository;
import com.myoffice.dto.ApprovalEmpDto;
import com.myoffice.dto.LoginRequestDto;
import com.myoffice.dto.LoginResponseDto;
import com.myoffice.dto.RegistrationRequestDto;
import com.myoffice.dto.RegistrationResponceDto;
import com.myoffice.exception.UserNotFoundException;
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
	public RegistrationResponceDto empRegistartion(@Valid RegistrationRequestDto registrationRequestDto) {
		Employee employee = new Employee();

		RegistrationResponceDto registrationResponceDto = new RegistrationResponceDto();
		employee.setEmployeeName(registrationRequestDto.getEmployeeName());
		employee.setEmailAddress(registrationRequestDto.getEmailAddress());
		employee.setDateOfJoining(registrationRequestDto.getDateOfJoining());
		employee.setDob(registrationRequestDto.getDob());
		employee.setPhoneNumber(registrationRequestDto.getPhoneNumber());
		employee.setYearsOfExperience(registrationRequestDto.getYearsOfExperience());
		employee.setDesignation(registrationRequestDto.getDesignation());
		employee.setPassword(AppConstant.CREDENTIAL_LOGIN);
		employee.setRole(MyOfficeEnum.Role.EMPLOYEE);
		Employee response = employeeRepository.save(employee);

		BeanUtils.copyProperties(response, registrationResponceDto);
		return registrationResponceDto;

	}

	@Override
	public LoginResponseDto authenticateEmployee(LoginRequestDto loginRequestDto) throws UserNotFoundException {

		Optional<Employee> user = employeeRepository.findByPhoneNumberAndPassword(loginRequestDto.getPhoneNumber(),
				loginRequestDto.getPassword());

		LoginResponseDto loginResponseDto = new LoginResponseDto();

		if (user.isPresent()) {
			BeanUtils.copyProperties(user.get(), loginResponseDto);
			loginResponseDto.setEmployeename(loginResponseDto.getEmployeename());
			loginResponseDto.setEmployeeId(loginResponseDto.getEmployeeId());
			loginResponseDto.setEmployeename(user.get().getEmployeeName());
			loginResponseDto.setRole(user.get().getRole());
			loginResponseDto.setSapId(user.get().getSapId());

			loginResponseDto.setMessage(AppConstant.LOGIN_SCCUESS_MESSAGE);

			loginResponseDto.setStatusCode(AppConstant.SUCCESS_STATUS_CODE);

			log.info("UserServiceImpl authenticateUser ---> user signed in");
			return loginResponseDto;
		} else {
			log.error("UserServiceImpl authenticateUser ---> NotFoundException occured");
			throw new UserNotFoundException(AppConstant.USER_NOT_FOUND);
		}

	}

	@Override
	public List<ApprovalEmpDto> getAllApprovalEmployee() {

		List<Employee> allApprovalEmps = employeeRepository.findAll();
		List<ApprovalEmpDto> employees = allApprovalEmps.stream()
				.filter(employee -> employee.getEmployeeApproval() == null).map(this::convertEmpEntityToDto)
				.collect(Collectors.toList());

		return employees;

	}

	private ApprovalEmpDto convertEmpEntityToDto(Employee employee) {
		ApprovalEmpDto approvalEmpDto = new ApprovalEmpDto();
		BeanUtils.copyProperties(employee, approvalEmpDto);
		approvalEmpDto.setEmployeename(employee.getEmployeeName());
		approvalEmpDto.setDesignation(employee.getDesignation());
		approvalEmpDto.setEmailAddress(employee.getEmailAddress());
		return approvalEmpDto;

	}

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

		// String employeePassword = generatePassword();
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
