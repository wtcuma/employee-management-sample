package net.iskandar.murano_example.dal.impl.support.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.iskandar.murano_example.domain.Status;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.UserType;

/**
 * Support for single character status
 * @author use
 *
 */

//TODO: 

public class CharacterStatusType implements EnhancedUserType {

	public Object fromXMLString(String xmlValue) {
		return Enum.valueOf(Status.class, xmlValue);
	}

	public String objectToSQLString(Object value) {
		return '\'' + ((Enum) value).name().substring(1) + '\'';
	}

	public String toXMLString(Object value) {
		return ((Enum) value).name();
	}

	public Object assemble(Serializable arg0, Object arg1)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object deepCopy(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	public Serializable disassemble(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean equals(Object arg0, Object arg1) throws HibernateException {
		// TODO Auto-generated method stub
		return false;
	}

	public int hashCode(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	public Object nullSafeGet(ResultSet rs, String[] columns, Object owner)
			throws HibernateException, SQLException {
		String value = (String) rs.getObject(columns[0]);
		return value != null ? (value.equals("A") ? Status.ACTIVE 
							  : value.equals("I") ? Status.INACTIVE 
									              : Status.ALL) : null;
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Hibernate.CHARACTER.sqlType());
		} else {
			st.setString(index, ((Status) value) == Status.ACTIVE ? "A"
					: ((Status) value) == Status.INACTIVE ? "I" : "*");
		}
	}

	public Object replace(Object arg0, Object arg1, Object arg2)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	public Class returnedClass() {
		return Status.class;
	}

	public int[] sqlTypes() {
		return new int[] { Hibernate.CHARACTER.sqlType() };
	}

}
