package net.iskandar.murano_example.dal.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import net.iskandar.murano_example.dal.PositionDao;
import net.iskandar.murano_example.domain.Position;

public class PositionDaoImpl implements PositionDao {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Position get(Integer positionId) {
		Session session = sessionFactory.getCurrentSession();
		return (Position) session.get(Position.class, positionId);
	}

	public List<Position> list() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select p from Position p order by p.name");
		return query.list();
	}

}
