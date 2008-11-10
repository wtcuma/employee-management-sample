package net.iskandar.murano_example.gwt.ui.client;

import java.util.List;

import net.iskandar.murano_example.EmployeeManagementAsync;
import net.iskandar.murano_example.dto.EmployeeUpdateObject;
import net.iskandar.murano_example.dto.SelectOption;
import net.iskandar.murano_example.dto.StatusCase;
import net.iskandar.murano_example.gwt.ui.client.mvc.AppEvents;
import net.iskandar.murano_example.gwt.ui.client.mvc.controller.MainControllerImpl;
import net.iskandar.murano_example.gwt.ui.client.widget.MainPanel;
       
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class EmployeeManagement implements EntryPoint {

	private EmployeeManagementAsync employeeManagement = Services.get().getEmployeeManagement();
	
	public void onModuleLoad() {
		employeeManagement.getStatuses(StatusCase.FILTER_MENU, new AsyncCallback<List<SelectOption>>(){

			public void onFailure(Throwable caught) {
				MessageBox.alert("Alert", caught.getMessage(), new WindowListener(){
						
				});
				
			}

			public void onSuccess(List<SelectOption> result) {
				Viewport viewport = new Viewport();
				viewport.setLayout(new FitLayout());
				ContentPanel contentPanel = new ContentPanel(new BorderLayout());
				contentPanel.setHeading("Employee management");
				ContentPanel westPanel = new ContentPanel();
				westPanel.setBorders(false);
				westPanel.setHeaderVisible(false);
				contentPanel.add(westPanel, new BorderLayoutData(LayoutRegion.WEST, 30f));
				MainPanel mainPanel = new MainPanel(new MainControllerImpl(), result);
				contentPanel.add(mainPanel, new BorderLayoutData(LayoutRegion.CENTER));
				viewport.add(contentPanel);
				RootPanel.get().add(viewport);
				mainPanel.layout();
			}
			
		});
	}
	
}
