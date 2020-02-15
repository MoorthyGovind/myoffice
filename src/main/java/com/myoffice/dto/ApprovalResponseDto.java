package com.myoffice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApprovalResponseDto {

	private Integer statusCode;
	private String message;
	private Integer sapId;
	private String password;
}
