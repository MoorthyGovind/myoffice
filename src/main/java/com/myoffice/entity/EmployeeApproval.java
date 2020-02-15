package com.myoffice.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.myoffice.common.MyOfficeEnum.ApprovalStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class EmployeeApproval {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer approvalId;
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
	@ManyToOne
	@JoinColumn(name = "approver_id")
	private Employee approver;
	@Enumerated(EnumType.STRING)
	private ApprovalStatus status;
}
