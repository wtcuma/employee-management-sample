package net.iskandar.murano_example.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.transaction.annotation.Transactional;

import net.iskandar.murano_example.dal.EmployeeDao;
import net.iskandar.murano_example.dal.PersistenceManager;
import net.iskandar.murano_example.dal.PositionDao;
import net.iskandar.murano_example.domain.Employee;
import net.iskandar.murano_example.domain.Position;
import net.iskandar.murano_example.domain.Status;

import net.iskandar.murano_example.dto.EmployeeUpdateObject;
import net.iskandar.murano_example.dto.EmployeeListObject;
import net.iskandar.murano_example.dto.EmployeeOrderSettings;
import net.iskandar.murano_example.dto.PagingResult;
import net.iskandar.murano_example.dto.PagingSettings;
import net.iskandar.murano_example.dto.SelectOption;

@Transactional
public class EmployeeManagementImpl extends EmployeeManagementAbstractImpl {

	private EmployeeDao employeeDao;
	private PositionDao positionDao;

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public PositionDao getPositionDao() {
		return positionDao;
	}

	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

	public List<SelectOption> getPositions() {
		try {
			List<SelectOption> positions = new LinkedList<SelectOption>();
			for (Position position : positionDao.list()) {
				positions.add(new SelectOption(position.getId(), position
						.getName()));
			}
			return positions;
		} catch (Throwable t) {
			log("Exception getting list of positions", t);
			throw new RuntimeException("Unexpected error, please don''t worry",
					t);
		}
	}

	private void log(String message, Throwable t) {
		/* log.error(message, t); */
		System.out.println("ERROR");
		System.out
				.println("==================================================================");
		System.out.println(message);
		t.printStackTrace(System.out);
		System.out
				.println("==================================================================");

	}

	public Integer addEmployee(EmployeeUpdateObject employeeEditObject) {
		try {
			if (!validateAndFixEmployee(employeeEditObject)) {
				throw new RuntimeException("Validation failed sorry");
			}
			Employee employee = createEmployeeInternal(employeeEditObject);
			return employeeDao.create(employee);
		} catch (Throwable t) {
			log("Exception adding employee", t);
			throw new RuntimeException("Unexpected error, please don''t worry",
					t);
		}

	}

	/**
	 * Creates, simply Employee domain object, of EmployeeUpdateObject transfer
	 * object, not persisting it to database
	 * 
	 * @param employeeEditObject
	 * @return newly created Employee domain object
	 */

	private Employee createEmployeeInternal(
			EmployeeUpdateObject employeeEditObject) {
		try {
			Employee employee = new Employee();
			employee.setFirstName(employeeEditObject.getFirstName());
			employee.setLastName(employeeEditObject.getLastName());
			employee.setPhoneNumber(employeeEditObject.getPhoneNumber());
			employee.setPosition(positionDao.get(employeeEditObject
					.getPositionId()));
			employee
					.setStatus(Status.values()[employeeEditObject.getStatusId()]);
			return employee;
		} catch (Throwable t) {
			log("Exception creating employee", t);
			throw new RuntimeException("Unexpected error, please don''t worry",
					t);
		}

	}

	public void updateEmployee(EmployeeUpdateObject employeeEditObject) {
		try {
			if (!validateAndFixEmployee(employeeEditObject)) {
				throw new RuntimeException("Validation failed sorry");
			}
			Employee employee = createEmployeeInternal(employeeEditObject);
			employee.setId(employeeEditObject.getId());
			employeeDao.update(employee);
		} catch (Throwable t) {
			log("Exception updating employee", t);
			throw new RuntimeException("Unexpected error, please don''t worry",
					t);
		}

	}

	public void deleteEmployee(Integer employeeId) {
		try {
			employeeDao.delete(employeeId);
		} catch (Throwable t) {
			log("Exception deleting employee", t);
			throw new RuntimeException("Unexpected error, please don''t worry",
					t);
		}

	}

	/**
	 * Loads EmployeeUpdateObject for review and/or edition
	 * 
	 * @param employeeId
	 *            an id of employee
	 * @return EmployeeUpdateObject fulfilled with current state of database
	 *         entity with the same id as employeeId supplied
	 */

	public EmployeeUpdateObject getEmployee(Integer employeeId) {
		try {
			Employee employee = employeeDao.get(employeeId);
			EmployeeUpdateObject employeeEditObject = new EmployeeUpdateObject();
			employeeEditObject.setId(employee.getId());
			employeeEditObject.setFirstName(employee.getFirstName());
			employeeEditObject.setLastName(employee.getLastName());
			employeeEditObject.setPhoneNumber(employee.getPhoneNumber());
			employeeEditObject.setPositionId(employee.getPosition().getId());
			employeeEditObject.setStatusId(employee.getStatus().ordinal());
			return employeeEditObject;
		} catch (Throwable t) {
			log("Exception getting employee for edit", t);
			throw new RuntimeException("Unexpected error, please don''t worry",
					t);
		}

	}

	public PagingResult<EmployeeListObject> getEmployees(Integer statusId,
			EmployeeOrderSettings orderSettings, PagingSettings pagingSettings) {
		try {
			if (pagingSettings == null) {
				throw new IllegalArgumentException(
						"pagingSettings can not be null");
			}
			if (orderSettings == null) {
				orderSettings = new EmployeeOrderSettings();
			}
			if (orderSettings.getField() == null) {
				orderSettings.setField(EmployeeOrderSettings.FIRST_NAME_FIELD);
			}
			if (orderSettings.getOrder() == null) {
				orderSettings.setOrder(EmployeeOrderSettings.ASC);
			}
			PagingResult<Employee> employeesResult = employeeDao.list(Status
					.values()[statusId], orderSettings, pagingSettings);
			PagingResult<EmployeeListObject> pagingResult = new PagingResult<EmployeeListObject>();
			pagingResult.setSettings(employeesResult.getSettings());
			pagingResult.setTotal(employeesResult.getTotal());
			List<EmployeeListObject> employeeListObjects = new LinkedList<EmployeeListObject>();
			for (Employee employee : employeesResult.getList()) {
				EmployeeListObject employeeListObject = new EmployeeListObject();
				employeeListObject.setId(employee.getId());
				employeeListObject.setFirstName(employee.getFirstName());
				employeeListObject.setLastName(employee.getLastName());
				employeeListObject.setPhoneNumber(employee.getPhoneNumber());
				employeeListObject
						.setPosition(employee.getPosition().getName());
				employeeListObject.setStatus(employee.getStatus().getLabel());
				employeeListObjects.add(employeeListObject);
			}
			pagingResult.setResult(employeeListObjects);
			return pagingResult;
		} catch (Throwable t) {
			log("Exception paging employees", t);
			throw new RuntimeException("Unexpected error, please don''t worry",
					t);
		}

	}

	public EmployeeUpdateObject createEmployee() {
		try {
			EmployeeUpdateObject employeeEditObject = new EmployeeUpdateObject();
			employeeEditObject.setStatusId(Status.ACTIVE.ordinal());
			return employeeEditObject;
		} catch (Throwable t) {
			log("Exception creating employee", t);
			throw new RuntimeException("Unexpected error, please don''t worry",
					t);
		}

	}

	private static boolean validateAndFixEmployee(
			EmployeeUpdateObject employeeUpdateObject) {

		String firstName = employeeUpdateObject.getFirstName().trim();
		String lastName = employeeUpdateObject.getLastName().trim();

		String phoneNumber = employeeUpdateObject.getPhoneNumber();
		if (phoneNumber == null) {
			phoneNumber = "";
		} else {
			phoneNumber = phoneNumber.trim();
		}

		Pattern phonePattern = Pattern
				.compile(EmployeeUpdateObject.PHONE_REGEX);
		boolean result = firstName.length() > 0
				&& firstName.length() <= 20
				&& lastName.length() > 0
				&& lastName.length() <= 20
				&& phoneNumber.length() >= 0
				&& phoneNumber.length() <= 30
				&& phonePattern.matcher(phoneNumber).matches()
				&& (employeeUpdateObject.getStatusId() == Status.ACTIVE
						.ordinal() || employeeUpdateObject.getStatusId() == Status.INACTIVE
						.ordinal());

		if (result) {
			if (Character.isLowerCase(firstName.charAt(0)))
				firstName = Character.toUpperCase(firstName.charAt(0))
						+ firstName.substring(1);
			employeeUpdateObject.setFirstName(firstName);

			if (Character.isLowerCase(lastName.charAt(0)))
				lastName = Character.toUpperCase(lastName.charAt(0))
						+ lastName.substring(1);
			employeeUpdateObject.setLastName(lastName);

			employeeUpdateObject.setPhoneNumber(phoneNumber);
		}

		return result;

	}

}
