package com.myoffice.dto;


import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationRequestDto {
	
	private String employeeName;
	private String emailAddress;
	private Long phoneNumber;
	private LocalDate dob;
	private LocalDate dateOfJoining;
	private Integer yearsOfExperience;
	private String designation;
	
}
