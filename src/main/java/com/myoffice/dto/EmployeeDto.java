package com.myoffice.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.management.relation.Role;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long sapId;
	private String employeeName;
	private String emailAddress;
	private String password;
	private String dob;
	private String dateOfJoining;
	private String role;

}
