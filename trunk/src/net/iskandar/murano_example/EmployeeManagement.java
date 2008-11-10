package net.iskandar.murano_example;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import net.iskandar.murano_example.dto.EmployeeUpdateObject;
import net.iskandar.murano_example.dto.EmployeeListObject;
import net.iskandar.murano_example.dto.EmployeeOrderSettings;
import net.iskandar.murano_example.dto.PagingResult;
import net.iskandar.murano_example.dto.PagingSettings;
import net.iskandar.murano_example.dto.SelectOption;
import net.iskandar.murano_example.dto.StatusCase;

public interface EmployeeManagement extends RemoteService {

	List<SelectOption> getPositions();

	/**
	 * Used to obtain statuses. 
	 * @param statusCase
	 * @return
	 */
	List<SelectOption> getStatuses(StatusCase statusCase);

	/**
	 * Creates employee template prepopulated with default fields.
	 * For now only "Status" - ACTIVE
	 * @return employee template
	 */
	EmployeeUpdateObject createEmployee();
	EmployeeUpdateObject getEmployee(Integer employeeId);
	
	/**
	 * Adds employee to our employee database.
	 * 
	 * @param employee
	 * @return
	 */
	Integer addEmployee(EmployeeUpdateObject employee);
	void updateEmployee(EmployeeUpdateObject employeeEditObject);
	void deleteEmployee(Integer employeeId);

	PagingResult<EmployeeListObject> getEmployees(Integer statusId,
			EmployeeOrderSettings orderSettings, PagingSettings pagingSettings);
	
}
