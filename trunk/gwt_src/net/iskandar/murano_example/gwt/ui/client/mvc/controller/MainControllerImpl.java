package net.iskandar.murano_example.gwt.ui.client.mvc.controller;

import java.util.List;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

import net.iskandar.murano_example.EmployeeManagementAsync;
import net.iskandar.murano_example.dto.EmployeeUpdateObject;
import net.iskandar.murano_example.dto.SelectOption;
import net.iskandar.murano_example.dto.StatusCase;
import net.iskandar.murano_example.gwt.ui.client.Services;
import net.iskandar.murano_example.gwt.ui.client.mvc.AppEvents;
import net.iskandar.murano_example.gwt.ui.client.mvc.controller.utils.EmployeeEditDataBuilder;
import net.iskandar.murano_example.gwt.ui.client.mvc.view.MainView;
import net.iskandar.murano_example.gwt.ui.client.widget.EditDialog;
import net.iskandar.murano_example.gwt.ui.client.widget.EditDialogListener;
import net.iskandar.murano_example.gwt.ui.client.widget.EmployeeEditData;

public class MainControllerImpl implements MainController {

	private EmployeeManagementAsync employeeManagement = Services.get()
			.getEmployeeManagement();

	private MainView view;

	public void setView(MainView view) {
		this.view = view;
	}

	public void onAddEmployee() {
		doEditEmployee(null);
	}

	public void onDeleteEmployee(Integer employeeId) {
		employeeManagement.deleteEmployee(employeeId, new AsyncCallback() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			public void onSuccess(Object result) {
				view.refreshList();
			}

		});

	}

	public void onEditEmployee(Integer employeeId) {
		doEditEmployee(employeeId);
	}

	private void doEditEmployee(Integer employeeId) {
		new EmployeeEditDataBuilder().build(employeeId,
				new AsyncCallback<EmployeeEditData>() {

					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					public void onSuccess(EmployeeEditData employeeEditData) {
						new EditDialog(employeeEditData,
								new EditDialogListener() {

									public void onSave(
											EmployeeUpdateObject employeeEditObject) {
										saveEmployee(employeeEditObject);
									}

								}).show();
					}

				});
	}

	private void saveEmployee(final EmployeeUpdateObject employeeUpdateObject) {
		if (employeeUpdateObject.getId() != null) {
			employeeManagement.updateEmployee(employeeUpdateObject,
					new AsyncCallback() {

						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}

						public void onSuccess(Object result) {
							view.refreshList();
						}

					});
		} else {
			employeeManagement.addEmployee(employeeUpdateObject,
					new AsyncCallback<Integer>() {

						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
						}

						public void onSuccess(Integer result) {
							view.refreshList();
						}

					});
		}

	}

}
