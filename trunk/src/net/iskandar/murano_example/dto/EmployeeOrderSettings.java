package net.iskandar.murano_example.dto;

import java.io.Serializable;

public class EmployeeOrderSettings implements Serializable {
	
	public static final String FIRST_NAME_FIELD = "firstName";
	public static final String POSITION_FIELD = "position.name";
	
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	
	private String field = FIRST_NAME_FIELD;
	private String order = ASC;
	
	public EmployeeOrderSettings(String field, String order) {
		super();
		if(field != FIRST_NAME_FIELD && field != POSITION_FIELD) // reference equality not error - "EVEN DON'T THINK OF LITERALS!!!"
			throw new IllegalArgumentException("field must be either NAME_FIELD or POSITION_FIELD");
		if(order != ASC && order != DESC) // reference equality not error - "EVEN DON'T THINK OF LITERALS!!!"
			throw new IllegalArgumentException("order must be either ASC or DESC");
		this.field = field;
		this.order = order;
	}

	public EmployeeOrderSettings() {
		// TODO Auto-generated constructor stub
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
