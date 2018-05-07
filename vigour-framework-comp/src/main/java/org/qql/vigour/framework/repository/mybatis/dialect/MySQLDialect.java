package org.qql.vigour.framework.repository.mybatis.dialect;

import java.sql.Connection;

import org.qql.vigour.framework.util.SQLUtils;


/**
 * Mysql方言的实现
 * 
 * @since JDK 1.5
 */
public class MySQLDialect implements Dialect {

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		return getLimitString(sql, offset, Integer.toString(offset), Integer.toString(limit));
	}

	public boolean supportsLimit() {
		return true;
	}

	/**
	 * 将sql变成分页sql语句,提供将offset及limit使用占位符号(placeholder)替换.
	 * 
	 * <pre>
	 * 如mysql
	 * dialect.getLimitString("select * from user", 12, ":offset",0,":limit") 将返回
	 * select * from user limit :offset,:limit
	 * </pre>
	 * 
	 * @param sql
	 *            实际SQL语句
	 * @param offset
	 *            分页开始纪录条数
	 * @param offsetPlaceholder
	 *            分页开始纪录条数－占位符号
	 * @param limitPlaceholder
	 *            分页纪录条数占位符号
	 * @return 包含占位符的分页sql
	 */
	public String getLimitString(String sql, int offset, String offsetPlaceholder, String limitPlaceholder) {
		StringBuilder stringBuilder = new StringBuilder(sql);
		stringBuilder.append(" limit ");
		if (offset > 0) {
			stringBuilder.append(offsetPlaceholder).append(",").append(limitPlaceholder);
		} else {
			stringBuilder.append(limitPlaceholder);
		}
		return stringBuilder.toString();
	}

	@Override
	public String getCountString(String sql) {
		return SQLUtils.buildCountSQL(sql);
	}

	@Override
	public String getSortString(String sql, String orderBy, String orderType) {
		if (sql.contains("order by")) {
			return sql;
		} else {
			return sql + " order by " + orderBy + " " + orderType;
		}
	}

	@Override
	public String getFilterString(String sql, String conditionSql) {
		if (sql.contains("where")) {
			return sql + " and " + conditionSql;
		} else {
			return sql +" where 1=1 and " + conditionSql;
		}
	}

	@Override
	public String getDateString(String date) {
		return "'" + date + "'";
	}

	/* (non-Javadoc)
	 * @see com.yuchengtech.bione.repository.mybatis.dialect.Dialect#getTableMetaDataSql(java.sql.Connection, java.lang.String[])
	 */
	@Override
	public String getTableMetaDataSql(Connection conn, String... tableNames) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuchengtech.bione.repository.mybatis.dialect.Dialect#getTableMetaDataSqlWithColumns(java.sql.Connection, java.lang.String[])
	 */
	@Override
	public String getTableMetaDataSqlWithColumns(String schemaNm,Connection conn,
			String... columnNames) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yuchengtech.bione.repository.mybatis.dialect.Dialect#getColumnMetaDataSql(java.sql.Connection, java.lang.String[])
	 */
	@Override
	public String getColumnMetaDataSql(Connection conn, String... tableNames) {
		// TODO Auto-generated method stub
		return null;
	}
}
