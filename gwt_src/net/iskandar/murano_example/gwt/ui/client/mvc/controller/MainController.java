package net.iskandar.murano_example.gwt.ui.client.mvc.controller;

import net.iskandar.murano_example.gwt.ui.client.mvc.view.MainView;

public interface MainController {

	void setView(MainView view);
	void onAddEmployee();
	void onEditEmployee(Integer employeeId);
	void onDeleteEmployee(Integer employeeId);
	
}
