package net.iskandar.murano_example;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import net.iskandar.murano_example.dto.EmployeeUpdateObject;
import net.iskandar.murano_example.dto.EmployeeListObject;
import net.iskandar.murano_example.dto.EmployeeOrderSettings;
import net.iskandar.murano_example.dto.PagingResult;
import net.iskandar.murano_example.dto.PagingSettings;
import net.iskandar.murano_example.dto.SelectOption;
import net.iskandar.murano_example.dto.StatusCase;

public interface EmployeeManagementAsync {

	void getPositions(AsyncCallback<List<SelectOption>> asyncCallback);

	/**
	 * Used to obtain statuses. 
	 * @param statusCase
	 * @return
	 */
	void getStatuses(StatusCase statusCase, AsyncCallback<List<SelectOption>> asyncCallback);
	void getEmployee(Integer employeeId, AsyncCallback<EmployeeUpdateObject> asyncCallback);
	void createEmployee(AsyncCallback<EmployeeUpdateObject> asyncCallback);
	
	void addEmployee(EmployeeUpdateObject employee, AsyncCallback<Integer> asyncCallback);
	void updateEmployee(EmployeeUpdateObject employeeEditObject, AsyncCallback asyncCallback);
	void deleteEmployee(Integer employeeId, AsyncCallback asyncCallback);

	void getEmployees(Integer statusId,
			EmployeeOrderSettings orderSettings, PagingSettings pagingSettings, AsyncCallback<PagingResult<EmployeeListObject>> asyncCallback);

}
