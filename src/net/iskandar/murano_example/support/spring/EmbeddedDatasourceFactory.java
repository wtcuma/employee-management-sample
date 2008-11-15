package net.iskandar.murano_example.support.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;


/**
 * Special connection factory to use embedded derby database
 * @author use
 *
 */

public class EmbeddedDatasourceFactory implements FactoryBean, ServletContextAware, DisposableBean {

	private BasicDataSource dataSource;

	public Object getObject() throws Exception {
		return dataSource;
	}

	public Class getObjectType() {
		return javax.sql.DataSource.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public void destroy() throws Exception {
		dataSource.close();
	}

	public void setServletContext(ServletContext servletContext) {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
		dataSource.setUrl("jdbc:derby:" + servletContext.getRealPath("WEB-INF/database"));
		
	}

}
