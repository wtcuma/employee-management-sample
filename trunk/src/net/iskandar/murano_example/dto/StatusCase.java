package net.iskandar.murano_example.dto;

import java.io.Serializable;

/**
 * Used to point use case of getStatus method.
 * @see #getStatuses
 * @author use
 *
 */
public enum StatusCase {
	FILTER_MENU , // ACTIVE, INACTIVE, ALL
	MODIFY_EMPLOYEE  // ACTIVE, INACTIVE
	/*
	 * On flexible filter screen we may need ALL(default), ACTIVE, INACTIVE
	 */
}