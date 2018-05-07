package org.qql.vigour.framework.common.entity;

import java.io.Serializable;

public class DataSourceInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String url;
	private String driverClass;
	private String userName;
	private String password;
	private String driverType;
	private String ValidationQuery;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getDriverType() {
		return driverType;
	}

	public void setDriverType(String type) {
		this.driverType = type;
	}

	public String getValidationQuery() {
		return ValidationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		ValidationQuery = validationQuery;
	}

}
