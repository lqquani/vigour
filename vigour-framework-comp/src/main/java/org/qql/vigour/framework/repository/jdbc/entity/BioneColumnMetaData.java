/**
 * 
 */
package org.qql.vigour.framework.repository.jdbc.entity;

import java.io.Serializable;

public class BioneColumnMetaData extends BioneTableMetaData implements Serializable {

	/**  * */
	private static final long serialVersionUID = 1L;

	private String columnName;	// 列名称
	private String dataType;	// 数据类型
	private String dataLength;	// 数据长度
	private String decimalDigits;// 小数位长度
	private boolean nullable;	// 是否可空
	
	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	/**
	 * @return the dataLength
	 */
	public String getDataLength() {
		return dataLength;
	}
	/**
	 * @param dataLength the dataLength to set
	 */
	public void setDataLength(String dataLength) {
		this.dataLength = dataLength;
	}
	/**
	 * @return the decimalDigits
	 */
	public String getDecimalDigits() {
		return decimalDigits;
	}
	/**
	 * @param decimalDigits the decimalDigits to set
	 */
	public void setDecimalDigits(String decimalDigits) {
		this.decimalDigits = decimalDigits;
	}
	/**
	 * @return the nullable
	 */
	public boolean isNullable() {
		return nullable;
	}
	/**
	 * @param nullable the nullable to set
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
}
