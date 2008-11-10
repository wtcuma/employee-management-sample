package net.iskandar.murano_example.gwt.ui.client.widget;

import java.util.Arrays;

import net.iskandar.murano_example.dto.EmployeeUpdateObject;
import net.iskandar.murano_example.dto.SelectOption;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;

import net.iskandar.murano_example.gwt.ui.client.Utils;
import net.iskandar.murano_example.gwt.ui.client.widget.ComboBox;

import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

public class EditDialog extends Window {

	private EditDialogListener editDialogListener;

	private TextField<String> firstNameField;
	private TextField<String> lastNameField;
	private ComboBox positionComboBox;
	private ListStore positionStore;
	private TextField<String> phoneField;

	private ListStore statusStore;
	private ComboBox statusComboBox;

	private EmployeeUpdateObject employeeUpdateObject;

	private ButtonBar buttonBar;
	private Button saveButton;

	private KeyListener keyListener = new KeyListener() {

		@Override
		public void componentKeyUp(ComponentEvent event) {
			Field field = (Field) event.component;
			validateFields(field);
		}

	};

	private Listener listener  = new Listener(){

		public void handleEvent(BaseEvent be) {
			validateFields(null);
		}
		
	};

	public EditDialog(EmployeeEditData employeeEditData,
			EditDialogListener editDialogListener) {

		super();
		this.employeeUpdateObject = employeeEditData.getEditEmployeeObject();
		this.editDialogListener = editDialogListener;
		setPosition(250, 100);
		setWidth(350);
		FormLayout layout = new FormLayout();
		layout.setLabelWidth(90);
		layout.setDefaultWidth(155);
		setLayout(layout);
		setModal(true);

		firstNameField = new TextField();
		firstNameField.setValue(employeeUpdateObject.getFirstName());
		firstNameField.setAllowBlank(false);
		firstNameField.setMaxLength(EmployeeUpdateObject.FIRST_NAME_MAX_LENGTH);
		firstNameField.addKeyListener(keyListener);
		firstNameField.setFieldLabel("First name");
		add(firstNameField);

		lastNameField = new TextField();
		lastNameField.setValue(employeeUpdateObject.getLastName());
		lastNameField.setAllowBlank(false);
		lastNameField.setMaxLength(EmployeeUpdateObject.LAST_NAME_MAX_LENGTH);
		lastNameField.addKeyListener(keyListener);
		lastNameField.setFieldLabel("Last name");
		add(lastNameField);

		positionComboBox = Utils.createComboBox(employeeEditData.getPositions());
		positionComboBox.addListener(Events.BrowserEvent, listener);
		positionComboBox.setAllowBlank(false);
		positionComboBox.setFieldLabel("Position");
		positionComboBox.setKeyValue(employeeUpdateObject.getPositionId());
		add(positionComboBox);

		phoneField = new TextField();
		phoneField.addKeyListener(keyListener);
		phoneField.setValue(employeeUpdateObject.getPhoneNumber());
		phoneField.setMaxLength(EmployeeUpdateObject.PHONE_MAX_LENGTH);
		phoneField.setFieldLabel("Phone number");
		phoneField.setRegex(EmployeeUpdateObject.PHONE_REGEX);
		add(phoneField);

		addWindowListener(new WindowListener() {

			@Override
			public void windowDeactivate(WindowEvent we) {
				positionStore.removeAll();
			}

		});

		statusStore = new ListStore();
		statusStore.add(employeeEditData.getStatuses());
		statusComboBox = new ComboBox();
		statusComboBox.addListener(Events.BrowserEvent, listener);
		statusComboBox.setEditable(false);
		statusComboBox.setAllowBlank(false);
		statusComboBox.setStore(statusStore);
		statusComboBox.setFieldLabel("Status");
		statusComboBox.setValueField(SelectOption.VALUE_FIELD);
		statusComboBox.setDisplayField(SelectOption.LABEL_FIELD);
		statusComboBox.setKeyValue(employeeUpdateObject.getStatusId());
		add(statusComboBox);

		saveButton = new Button("Save",
				new SelectionListener<ComponentEvent>() {

					@Override
					public void componentSelected(ComponentEvent ce) {
						onSave();
					}

				});
		saveButton.disable();

		buttonBar = new ButtonBar();
		buttonBar.add(saveButton);
		buttonBar.add(new Button("Cancel",
				new SelectionListener<ComponentEvent>() {

					@Override
					public void componentSelected(ComponentEvent ce) {
						close();
					}

				}));
		setButtonBar(buttonBar);

	}

	protected void validateFields(Field field) {
		if(field != null){
			field.validate();
		}
		if(!"".equals(firstNameField.getValue())
				&& firstNameField.getValue() != null /* Prevent validation of blank field */
				&& firstNameField.isValid()
				&& !"".equals(lastNameField.getValue()) /* Prevent validation of blank field */
				&& lastNameField.getValue() != null
				&& lastNameField.isValid()
				&& positionComboBox.getKeyValue() != null /* Prevent validation of blank field */
				&& positionComboBox.isValid()
				&& !"".equals(phoneField.getValue())
				&& phoneField.getValue() != null /* Prevent validation of blank field */
				&& phoneField.isValid()
				/* Now check if user changed data */
				&& (employeeUpdateObject.getId() == null /* Prevent check if new employee */ 
					|| (!firstNameField.getValue().equals(employeeUpdateObject.getFirstName())
					    || !lastNameField.getValue().equals(employeeUpdateObject.getLastName())
					    || !("" + positionComboBox.getKeyValue()).equals("" + employeeUpdateObject.getPositionId())
					    || !phoneField.getValue().equals(employeeUpdateObject.getPhoneNumber())
					    || !statusComboBox.getKeyValue().equals(employeeUpdateObject.getStatusId())))){
			saveButton.enable();
		} else {
			saveButton.disable();
		}
	}

	private void onSave() {
// Affect update object
		employeeUpdateObject.setFirstName(firstNameField.getValue());
		employeeUpdateObject.setLastName(lastNameField.getValue());
		employeeUpdateObject.setPositionId(positionComboBox.getKeyValue());
		employeeUpdateObject.setPhoneNumber(phoneField.getValue());
		employeeUpdateObject.setStatusId(statusComboBox.getKeyValue());
// Peform save
		editDialogListener.onSave(employeeUpdateObject);
// Close dialog		
		close();
	}

}
