package com.myoffice.common;

/**
 * MyOfficeEnum is the enum for the maintaining the values of the
 * role values and status details.
 * 
 * @author Amala.S
 * @since 15-02-2020
 * @version V1.1
 *
 */
public class MyOfficeEnum {

	
	/**
	 * Maintaining the role type such as Admine and user
	 * 
	 * @author Govindasamy
	 * @since 15-02-2020
	 */
	public enum Role {
		ADMIN, MANAGER, EMPLOYEE;

	}
	/**
	 * Maintaining the approval status of Pending and Approved
	 * 
	 * @author Amala.S
	 * @since 15-02-2020
	 * @version V1.1
	 */
	public enum ApprovalStatus {
		PENDING, REJECTED, APPROVED;
	}
}
