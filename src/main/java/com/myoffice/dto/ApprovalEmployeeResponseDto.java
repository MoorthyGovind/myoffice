package com.myoffice.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApprovalEmployeeResponseDto {
	
	private Integer statusCode;
	private String message;
	
	private List<ApprovalEmpDto> approvalEmp;

}
