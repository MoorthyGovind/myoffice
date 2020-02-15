package com.myoffice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponseDto {
	
	private Integer statusCode;
	private String message;
	private Integer employeeId;
	private String employeename;

}
