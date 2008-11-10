package net.iskandar.murano_example.dto;

import java.io.Serializable;

public class SelectOption implements Serializable {

	public static final String VALUE_FIELD = "value";
	public static final String LABEL_FIELD = "label";
	
	private Integer value;
	private String label;
	
	public SelectOption() {
		super();
	}

	public SelectOption(Integer value, String label) {
		super();
		this.value = value;
		this.label = label;
	}

	public Integer getValue() {
		return value;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
