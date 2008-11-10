package net.iskandar.murano_example.gwt.ui.client.mvc.controller.utils;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.google.gwt.user.client.rpc.AsyncCallback;

import net.iskandar.murano_example.EmployeeManagementAsync;
import net.iskandar.murano_example.dto.EmployeeUpdateObject;
import net.iskandar.murano_example.dto.SelectOption;
import net.iskandar.murano_example.dto.StatusCase;
import net.iskandar.murano_example.gwt.ui.client.Services;
import net.iskandar.murano_example.gwt.ui.client.Utils;
import net.iskandar.murano_example.gwt.ui.client.mvc.AppEvents;
import net.iskandar.murano_example.gwt.ui.client.widget.EmployeeEditData;

/**
 * Utility class building data for EditDialog
 * @author use
 *
 */

//TODO: Unit test this class somehow

public class EmployeeEditDataBuilder {

	private int requestCount = 3;
	private AsyncCallback<EmployeeEditData> callback;

	private EmployeeManagementAsync employeeManagement = Services.get()
			.getEmployeeManagement();

	public void build(AsyncCallback<EmployeeEditData> callback){
		build(null);
	}
	
	public void build(final Integer employeeId, AsyncCallback<EmployeeEditData> callback) {

		requestCount = 3;
		this.callback = callback;

		final EmployeeEditData employeeEditData = new EmployeeEditData();

		employeeManagement
				.getPositions(new AsyncCallback<List<SelectOption>>() {

					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					public void onSuccess(List<SelectOption> result) {
						List<BaseModelData> positions = Utils.convertSelectOptionsToModelData(result);
						employeeEditData.setPositions(positions);
						doOnSuccess(employeeEditData);
					}

				});

		employeeManagement.getStatuses(StatusCase.MODIFY_EMPLOYEE,
				new AsyncCallback<List<SelectOption>>() {

					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					public void onSuccess(List<SelectOption> result) {
						employeeEditData
								.setStatuses(Utils.convertSelectOptionsToModelData(result));
						doOnSuccess(employeeEditData);
					}

				});

		if (employeeId == null) {
			employeeManagement.createEmployee(new AsyncCallback<EmployeeUpdateObject>() {

						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}

						public void onSuccess(EmployeeUpdateObject result) {
							employeeEditData.setEditEmployeeObject(result);
							doOnSuccess(employeeEditData);
						}

			});
		} else {
			employeeManagement.getEmployee(employeeId, new AsyncCallback<EmployeeUpdateObject>() {

				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}

				public void onSuccess(EmployeeUpdateObject result) {
					employeeEditData.setEditEmployeeObject(result);
					doOnSuccess(employeeEditData);
				}

			});
		}

	}
	
	
	
	private synchronized void doOnSuccess(EmployeeEditData employeeEditData){
		if(--requestCount == 0){
			callback.onSuccess(employeeEditData);
		}
	}
	

}
