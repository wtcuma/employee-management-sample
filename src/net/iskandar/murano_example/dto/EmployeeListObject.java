package net.iskandar.murano_example.dto;

import java.io.Serializable;

public class EmployeeListObject implements Serializable {

	public static final String ID_FIELD = "id";
	public static final String FIRST_NAME_FIELD = "firstName";
	public static final String LAST_NAME_FIELD = "lastName";
	public static final String PHONE_NUMBER_FIELD = "phoneNumber";
	public static final String POSITION_FIELD = "position";
	public static final String STATUS_FIELD = "status";
	
	private Integer id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String position;
	private String status;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

}
