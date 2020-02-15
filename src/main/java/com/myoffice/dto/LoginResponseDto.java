package com.myoffice.dto;

import com.myoffice.common.MyOfficeEnum.Role;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponseDto {
	
	private Integer statusCode;
	private String message;
	private Integer employeeId;
	private String employeename;
	private Long sapId;
	private Role role;

}
