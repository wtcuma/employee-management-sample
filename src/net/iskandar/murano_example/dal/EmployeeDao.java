package net.iskandar.murano_example.dal;

import net.iskandar.murano_example.dal.impl.EmployeeDaoImpl;
import net.iskandar.murano_example.domain.Employee;
import net.iskandar.murano_example.domain.Status;
import net.iskandar.murano_example.dto.EmployeeOrderSettings;
import net.iskandar.murano_example.dto.PagingResult;
import net.iskandar.murano_example.dto.PagingSettings;

public interface EmployeeDao {
	
	Employee get(Integer id);
	Integer create(Employee employee);
	void update(Employee employee);
	void delete(Integer employeeId);
	PagingResult<Employee> list(Status status, EmployeeOrderSettings orderSettings, PagingSettings pagingSettings);
	
}
