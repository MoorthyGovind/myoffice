package com.myoffice.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.myoffice.common.MyOfficeEnum.Role;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Amala.S
 * @version V1.1
 * @since 15-02-2020
 *
 */
@Getter
@Setter
@Entity
@SequenceGenerator(name = "employeeId", initialValue = 103, allocationSize = 1)
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeeId")
	private Integer employeeId;
	private String employeeName;
	private String emailAddress;
	private String password;
	private LocalDate dob;
	private LocalDate dateOfJoining;
	private String designation;
	private Long phoneNumber;
	private Integer yearsOfExperience;
	private Integer sapId;
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "employee")
    private EmployeeApproval employeeApproval;
}
