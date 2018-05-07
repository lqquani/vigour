/**
 * 
 */
package org.qql.vigour.framework.common;

import javax.sql.DataSource;

import org.springframework.beans.factory.FactoryBean;

@SuppressWarnings("rawtypes")
public class AppDataSource implements FactoryBean {

	private DataSource dataSource = null;

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Object getObject() throws Exception {

		return this.dataSource;
	}

	public Class getObjectType() {

		return DataSource.class;
	}

	public boolean isSingleton() {
		return true;
	}
}
