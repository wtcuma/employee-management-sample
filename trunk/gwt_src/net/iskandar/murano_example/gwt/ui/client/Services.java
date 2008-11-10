package net.iskandar.murano_example.gwt.ui.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import net.iskandar.murano_example.EmployeeManagementAsync;

public class Services {

	private static final Services instance = new Services();
	
	public static Services get() {
		return instance;
	}
	
	private EmployeeManagementAsync employeeManagement;// = new EmployeeManagementAsyncFakeStub();

	public EmployeeManagementAsync getEmployeeManagement() {
		if(employeeManagement == null){
			employeeManagement = GWT.create(net.iskandar.murano_example.EmployeeManagement.class);
			String moduleBaseUrl = GWT.getModuleBaseURL();
			if(!moduleBaseUrl.endsWith("/"))
				moduleBaseUrl += "/";
			((ServiceDefTarget) employeeManagement).setServiceEntryPoint(moduleBaseUrl + "rpc/employeeManagement.rpc");
		}
		return employeeManagement;
	}
	
}
