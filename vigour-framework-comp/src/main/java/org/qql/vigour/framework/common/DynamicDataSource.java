/**
 * 
 */
package org.qql.vigour.framework.common;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.qql.vigour.framework.holder.DataSourceHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author kevin
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	private Map<Object, Object> resolvedDataSources;
	
	@Override
	protected Object determineCurrentLookupKey() {
		String dataSource=DataSourceHolder.getDataSourceId();
//		DataSourceHolder.clear();
		return dataSource;
	}

	@Override
	public void afterPropertiesSet() {
		
		super.afterPropertiesSet();
	}

	public Map<Object, Object> getResolvedDataSources() {
		return resolvedDataSources;
	}

	public void setResolvedDataSources(Map<Object, Object> resolvedDataSources) {
		this.resolvedDataSources = resolvedDataSources;
	}
	
}
