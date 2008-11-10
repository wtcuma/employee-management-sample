package net.iskandar.murano_example.dal.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import net.iskandar.murano_example.dal.EmployeeDao;
import net.iskandar.murano_example.domain.Employee;
import net.iskandar.murano_example.domain.Status;
import net.iskandar.murano_example.dto.EmployeeOrderSettings;
import net.iskandar.murano_example.dto.PagingResult;
import net.iskandar.murano_example.dto.PagingSettings;

public class EmployeeDaoImpl implements EmployeeDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Integer create(Employee employee) {
		Session session = sessionFactory.getCurrentSession();
		session.save(employee);
		return employee.getId();
	}

	public void delete(Integer employeeId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"delete from Employee e where e.id = :employeeId"
		);
		query.setParameter("employeeId", employeeId);
		query.executeUpdate();
	}

	public Employee get(Integer employeeId) {
		Session session = sessionFactory.getCurrentSession();
		return (Employee) session.get(Employee.class, employeeId);
	}

	public void update(Employee employee) {
		Session session = sessionFactory.getCurrentSession();
		session.update(employee);
	}
	
	public PagingResult<Employee> list(Status status,
			EmployeeOrderSettings orderSettings, PagingSettings pagingSettings) {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer queryBuf = new StringBuffer("from Employee e");
		boolean sf = status != Status.ALL;
		if (sf){
			queryBuf.append(" where e.status = :status");
		}
		String baseQuery = queryBuf.toString(); // saving partly statement for count statement
		if(orderSettings != null){
			queryBuf.append(" order by e." + orderSettings.getField() + " " + orderSettings.getOrder());
		}
		Query query = session.createQuery(queryBuf.toString());
		if(sf){
			query.setParameter("status", status);
		}
		if(pagingSettings != null){
			query.setFirstResult(pagingSettings.getStart());
			query.setMaxResults(pagingSettings.getCount());
		}
		PagingResult pagingResult = new PagingResult();
		pagingResult.setResult(query.list());
		// Fix paging settings as result can be little than requested
		pagingSettings.setCount(pagingResult.getList().size());
		pagingResult.setSettings(pagingSettings);
		// Count records in database to return
		query = session.createQuery("select count(e) " + baseQuery.toString());
		if(sf){
			query.setParameter("status", status);
		}
		Long count = (Long) query.uniqueResult();
		pagingResult.setTotal(count != null ? count : 0);
		return pagingResult;
	}

}
