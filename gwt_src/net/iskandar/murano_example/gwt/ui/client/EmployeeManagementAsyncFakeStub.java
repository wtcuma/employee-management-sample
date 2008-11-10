package net.iskandar.murano_example.gwt.ui.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.user.client.rpc.AsyncCallback;

import net.iskandar.murano_example.EmployeeManagementAsync;
import net.iskandar.murano_example.dto.EmployeeUpdateObject;
import net.iskandar.murano_example.dto.EmployeeListObject;
import net.iskandar.murano_example.dto.EmployeeOrderSettings;
import net.iskandar.murano_example.dto.PagingResult;
import net.iskandar.murano_example.dto.PagingSettings;
import net.iskandar.murano_example.dto.SelectOption;
import net.iskandar.murano_example.dto.StatusCase;

public class EmployeeManagementAsyncFakeStub implements EmployeeManagementAsync {

	
	private SelectOption[] statuses = new SelectOption[] {
			new SelectOption(1, "Active"), new SelectOption(2, "Inactive"),
			new SelectOption(3, "All") };

	private SelectOption[] positions = new SelectOption[] {
			new SelectOption(1, "Java Developer"),
			new SelectOption(2, "Java Architect"),
			new SelectOption(3, "Java Team Lead") };

	private List<EmployeeUpdateObject> employees = new ArrayList<EmployeeUpdateObject>();

	private void initEmployees() {
		EmployeeUpdateObject employeeEditObject = new EmployeeUpdateObject();
		employeeEditObject.setFirstName("Iskandar");
		employeeEditObject.setLastName("Zaynutdinov");
		employeeEditObject.setPhoneNumber("312312");
		employeeEditObject.setPositionId(2);
		employeeEditObject.setStatusId(1);
		employees.add(employeeEditObject);
		employeeEditObject.setId(employees.size());
		employeeEditObject = new EmployeeUpdateObject();
		employeeEditObject.setFirstName("Anvarbek");
		employeeEditObject.setLastName("Abidov");
		employeeEditObject.setPhoneNumber("5453432");
		employeeEditObject.setPositionId(1);
		employeeEditObject.setStatusId(1);
		employees.add(employeeEditObject);
		employeeEditObject.setId(employees.size());
		employeeEditObject = new EmployeeUpdateObject();
		employeeEditObject.setFirstName("Bahrom");
		employeeEditObject.setLastName("Soultonov");
		employeeEditObject.setPhoneNumber("656454");
		employeeEditObject.setPositionId(1);
		employeeEditObject.setStatusId(1);
		employees.add(employeeEditObject);
		employeeEditObject.setId(employees.size());
		employeeEditObject = new EmployeeUpdateObject();
		employeeEditObject.setFirstName("Kirill");
		employeeEditObject.setLastName("Tsibriy");
		employeeEditObject.setPhoneNumber("8778686");
		employeeEditObject.setPositionId(3);
		employeeEditObject.setStatusId(2);
		employees.add(employeeEditObject);
		employeeEditObject.setId(employees.size());
		for (int i = 0; i < 100; i++) {
			employeeEditObject = new EmployeeUpdateObject();
			employeeEditObject.setFirstName(Integer.toString(i) + "Somebody");
			employeeEditObject.setLastName("Somebody");
			employeeEditObject.setPhoneNumber("8778686");
			employeeEditObject.setPositionId(1);
			employeeEditObject.setStatusId(1);
			employees.add(employeeEditObject);
			employeeEditObject.setId(employees.size());
		}
	}

	public EmployeeManagementAsyncFakeStub() {
		super();
		initEmployees();
	}

	public void addEmployee(EmployeeUpdateObject employee,
			AsyncCallback<Integer> asyncCallback) {
		employees.add(employee);
		employee.setId(employees.size());
		asyncCallback.onSuccess(employee.getId());
	}

	public void deleteEmployee(Integer employeeId, AsyncCallback asyncCallback) {
		employees.remove(employeeId.intValue() - 1);
		asyncCallback.onSuccess(null);
	}

	public void getEmployee(Integer employeeId,
			AsyncCallback<EmployeeUpdateObject> asyncCallback) {
		asyncCallback.onSuccess(employees.get(employeeId - 1));
	}

	public void getDefaultOrderSettings(
			AsyncCallback<EmployeeOrderSettings> asyncCallback) {
		asyncCallback.onSuccess(null);
	}

	public void getDefaultStatusId(AsyncCallback<Integer> asyncCallback) {
		asyncCallback.onSuccess(1);
	}

	public void getEmployees(Integer statusId,
			EmployeeOrderSettings orderSettings, PagingSettings pagingSettings,
			AsyncCallback<PagingResult<EmployeeListObject>> asyncCallback) {
		
        Info.display("MessageBox", "Loading list:\n statusId={0}", statusId != null ? statusId.toString() : null);

		List<EmployeeListObject> result = new ArrayList<EmployeeListObject>();
		int lastIndex = pagingSettings.getStart() + pagingSettings.getCount();
		if(lastIndex > employees.size()) lastIndex = employees.size();
		for (int i = pagingSettings.getStart(); i < lastIndex; i ++) {
			EmployeeUpdateObject employeeUpdateObject = employees.get(i);
			EmployeeListObject employeeListObject = new EmployeeListObject();
			employeeListObject.setId(employeeUpdateObject.getId());
			employeeListObject.setFirstName(employeeUpdateObject.getFirstName());
			employeeListObject.setLastName(employeeUpdateObject.getLastName());
			employeeListObject.setPosition(positions[employeeUpdateObject.getPositionId() - 1].getLabel());
			employeeListObject.setPhoneNumber(employeeUpdateObject.getPhoneNumber());
			employeeListObject.setStatus(statuses[employeeUpdateObject.getStatusId() - 1].getLabel());
			result.add(employeeListObject);
		}
		PagingResult<EmployeeListObject> pagingResult = new PagingResult<EmployeeListObject>();
		pagingSettings.setCount(lastIndex - pagingSettings.getStart());
		pagingResult.setSettings(pagingSettings);
		pagingResult.setTotal(employees.size());
		pagingResult.setResult(result);
		asyncCallback.onSuccess(pagingResult);
	}

	public void getPositions(AsyncCallback<List<SelectOption>> asyncCallback) {
		asyncCallback.onSuccess(Arrays.asList(positions));
	}

	public void getStatuses(StatusCase statusCase,
			AsyncCallback<List<SelectOption>> asyncCallback) {
		asyncCallback.onSuccess(Arrays.asList(statuses));
	}

	public void updateEmployee(EmployeeUpdateObject employeeEditObject,
			AsyncCallback asyncCallback) {
		asyncCallback.onSuccess(null);
	}

	public void createEmployee(AsyncCallback<EmployeeUpdateObject> asyncCallback) {
		EmployeeUpdateObject employeeUpdateObject = new EmployeeUpdateObject();
		employeeUpdateObject.setStatusId(1);
		asyncCallback.onSuccess(employeeUpdateObject);
	}

}
