package com.myoffice.dto;

import com.myoffice.common.MyOfficeEnum.ApprovalStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApprovalRequestDto {

	private Integer employeeId;
	private ApprovalStatus approvalType;
}
