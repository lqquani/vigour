package org.qql.vigour.framework.repository.jdbc.support;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementSetter;


public class ArgPreparedStatementSetter implements PreparedStatementSetter {
	
	private final Object[] args;

	public ArgPreparedStatementSetter(Object[] args) {
		this.args = args;
	}

	public void setValues(PreparedStatement ps) throws SQLException {
		if (this.args != null) {
			for (int i = 0; i < this.args.length; i++) {
				ps.setObject(i + 1, this.args[i]);
			}
		}
	}

}
