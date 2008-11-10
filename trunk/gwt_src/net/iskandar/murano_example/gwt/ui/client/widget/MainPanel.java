package net.iskandar.murano_example.gwt.ui.client.widget;

import java.util.ArrayList;
import java.util.List;

import net.iskandar.murano_example.dto.EmployeeListObject;
import net.iskandar.murano_example.dto.EmployeeOrderSettings;
import net.iskandar.murano_example.dto.PagingResult;
import net.iskandar.murano_example.dto.SelectOption;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.binder.TableBinder;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import net.iskandar.murano_example.dto.PagingSettings;
import net.iskandar.murano_example.gwt.ui.client.Services;
import net.iskandar.murano_example.gwt.ui.client.Utils;
import net.iskandar.murano_example.gwt.ui.client.mvc.controller.MainController;
import net.iskandar.murano_example.gwt.ui.client.mvc.view.MainView;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;

import com.extjs.gxt.ui.client.widget.PagingToolBar;
import com.extjs.gxt.ui.client.widget.WidgetComponent;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.table.Table;
import com.extjs.gxt.ui.client.widget.table.TableColumn;
import com.extjs.gxt.ui.client.widget.table.TableColumnModel;
import com.extjs.gxt.ui.client.widget.toolbar.AdapterToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.TextToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

public class MainPanel extends ContentPanel implements MainView {

	private static final int PAGE_SIZE = 15;
	private Integer statusId;
	private MainController controller;
	private Table table;

	public MainPanel(final MainController controller, List<SelectOption> statuses) {

		super();
		statusId = statuses.get(0).getValue();
		controller.setView(this);
		this.controller = controller;
		setHeaderVisible(false);
		setLayout(new BorderLayout());
		ToolBar toolBar = new ToolBar();
		TextToolItem textToolItem = new TextToolItem("Add", "employee-add-icon");
		textToolItem
				.addSelectionListener(new SelectionListener<ComponentEvent>() {

					@Override
					public void componentSelected(ComponentEvent ce) {
						MainPanel.this.controller.onAddEmployee();
					}

				});
		toolBar.add(textToolItem);

		textToolItem = new TextToolItem("Edit", "employee-edit-icon");
		textToolItem
				.addSelectionListener(new SelectionListener<ComponentEvent>() {

					@Override
					public void componentSelected(ComponentEvent ce) {
						Integer employeeId = getSelectedEmployeeId();
						if (employeeId == null) {
							MessageBox.alert("Alert",
									"Please select an employee", null);
							return;
						}
						MainPanel.this.controller.onEditEmployee(employeeId);
					}

				});
		toolBar.add(textToolItem);

		textToolItem = new TextToolItem("Delete", "employee-delete-icon");
		textToolItem.addSelectionListener(new SelectionListener<ComponentEvent>() {

					@Override
					public void componentSelected(ComponentEvent ce) {
						final Integer employeeId = getSelectedEmployeeId();
						if (employeeId == null) {
							MessageBox.alert("Alert",
									"Please select an employee", null);
							return;
						}
						MessageBox.confirm("Confirm",
								"Are you sure want to delete "
										+ getSelectedEmployeeName() + "?",
								new Listener<WindowEvent>() {
									public void handleEvent(WindowEvent we) {
										Dialog dialog = (Dialog) we.component;
										Button btn = dialog.getButtonPressed();
										if(Dialog.YES.equals(btn.getItemId())){
											MainPanel.this.controller.onDeleteEmployee(employeeId);
										}
									}
								});
					}

				});
		
		toolBar.add(textToolItem);

		final ComboBox statusComboBox = Utils.createSelectOptionComboBox(statuses);
		statusComboBox.setKeyValue(statusId);
		statusComboBox.addListener(Events.BrowserEvent, new Listener(){

			public void handleEvent(BaseEvent be) {
				if(!statusId.equals(statusComboBox.getKeyValue())){
					statusId = statusComboBox.getKeyValue();
					loader.load(0, PAGE_SIZE);
				}
			}
			
		});
		AdapterToolItem adapterToolItem = new AdapterToolItem(statusComboBox);
		toolBar.add(new SeparatorToolItem());
		toolBar.add(adapterToolItem);
		
		add(toolBar, new BorderLayoutData(LayoutRegion.NORTH, 30f));

		List<TableColumn> columns = new ArrayList<TableColumn>();
		TableColumn column = new TableColumn(EmployeeListObject.ID_FIELD, "Id",
				20);
		column.setHidden(true);
		columns.add(column);
		columns.add(new TableColumn(EmployeeListObject.FIRST_NAME_FIELD,
				"First name", 180));
		columns.add(new TableColumn(EmployeeListObject.LAST_NAME_FIELD,
				"Last name", 180));
		columns.add(new TableColumn(EmployeeListObject.POSITION_FIELD,
				"Position", 180));
		columns.add(new TableColumn(EmployeeListObject.PHONE_NUMBER_FIELD,
				"Phone number", 100));
		columns.add(new TableColumn(EmployeeListObject.STATUS_FIELD, "Status",
				60));

		TableColumnModel cm = new TableColumnModel(columns);
		table = new Table(cm);

		loader = new BasePagingLoader(rpcProxy);
		loader.setRemoteSort(true);

		// store
		ListStore<BaseModelData> store = new ListStore<BaseModelData>(loader);

		// binder
		new TableBinder<BaseModelData>(table, store);

		final PagingToolBar pagingToolBar = new PagingToolBar(PAGE_SIZE);
		pagingToolBar.bind(loader);

		ContentPanel tablePanel = new ContentPanel();
		tablePanel.setBorders(false);
		tablePanel.setCollapsible(true);
		tablePanel.setAnimCollapse(false);
		tablePanel.setButtonAlign(HorizontalAlignment.CENTER);
		tablePanel.setHeaderVisible(false);
		tablePanel.setLayout(new FitLayout());
		tablePanel.setSize(770, 350);
		tablePanel.add(table);
		tablePanel.setBottomComponent(pagingToolBar);

		ContentPanel centerPanel = new ContentPanel();
		centerPanel.setHeaderVisible(false);
		centerPanel.setLayout(new FlowLayout());
		centerPanel.add(tablePanel);

		add(centerPanel, new BorderLayoutData(LayoutRegion.CENTER));

		DeferredCommand.addCommand(new Command() {
			public void execute() {
				loader.load(0, PAGE_SIZE);
			}
		});

	}

	private PagingLoadConfig pagingLoadConfig;

	private RpcProxy rpcProxy = new RpcProxy() {

		@Override
		protected void load(Object loadConfig, final AsyncCallback proxyCallback) {
			pagingLoadConfig = (PagingLoadConfig) loadConfig;
			EmployeeOrderSettings orderSettings = null;
			if(pagingLoadConfig.getSortInfo().getSortDir() != SortDir.NONE){
				orderSettings = new EmployeeOrderSettings();
				orderSettings.setField(pagingLoadConfig.getSortInfo()
						.getSortField());
				orderSettings.setOrder(pagingLoadConfig.getSortInfo().getSortDir()
						.name().toLowerCase());
			}
			PagingSettings pagingSettings = new PagingSettings();
			pagingSettings.setStart(pagingLoadConfig.getOffset());
			pagingSettings.setCount(pagingLoadConfig.getLimit());

			AsyncCallback<PagingResult<EmployeeListObject>> callback = new AsyncCallback<PagingResult<EmployeeListObject>>() {

				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}

				public void onSuccess(PagingResult<EmployeeListObject> result) {
					List<BaseModelData> resultList = new ArrayList<BaseModelData>();
					for (EmployeeListObject employeeListObject : result
							.getList()) {
						BaseModelData baseModelData = new BaseModelData();
						baseModelData.set(EmployeeListObject.ID_FIELD,
								employeeListObject.getId());
						baseModelData.set(EmployeeListObject.FIRST_NAME_FIELD,
								employeeListObject.getFirstName());
						baseModelData.set(EmployeeListObject.LAST_NAME_FIELD,
								employeeListObject.getLastName());
						baseModelData.set(EmployeeListObject.POSITION_FIELD,
								employeeListObject.getPosition());
						baseModelData.set(
								EmployeeListObject.PHONE_NUMBER_FIELD,
								employeeListObject.getPhoneNumber());
						baseModelData.set(EmployeeListObject.STATUS_FIELD,
								employeeListObject.getStatus());
						resultList.add(baseModelData);
					}
					BasePagingLoadResult<BaseModelData> pagingLoadResult = new BasePagingLoadResult<BaseModelData>(
							resultList, result.getSettings().getStart(),
							(int) result.getTotal());
					proxyCallback.onSuccess(pagingLoadResult);
				}

			};

			Services.get().getEmployeeManagement().getEmployees(statusId,
					orderSettings, pagingSettings, callback);

		}

	};
	private PagingLoader loader;

	private Integer getSelectedEmployeeId() {
		if(table.getSelectedItem() == null) return null;
		Integer employeeId = (Integer) table.getSelectedItem().getModel().get(
				EmployeeListObject.ID_FIELD);
		return employeeId;
	}

	private String getSelectedEmployeeName() {
		if(table.getSelectedItem() == null) return null;
		String firstName = (String) table.getSelectedItem().getModel().get(
				EmployeeListObject.FIRST_NAME_FIELD);
		String lastName = (String) table.getSelectedItem().getModel().get(
				EmployeeListObject.LAST_NAME_FIELD);
		return firstName + " " + lastName;
	}

	public void refreshList() {
		loader.load(pagingLoadConfig.getOffset(), pagingLoadConfig.getLimit());
	}

}
