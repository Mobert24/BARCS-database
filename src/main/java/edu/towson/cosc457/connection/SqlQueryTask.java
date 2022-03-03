package edu.towson.cosc457.connection;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Callable;

public class SqlQueryTask implements Callable<ResultSet> {
	
	private final String sql;
	private final List<Object> values;
	
	public SqlQueryTask(String sql) {
		this(sql, null);
	}
	
	public SqlQueryTask(String sql, List<Object> values) {
		this.sql = sql;
		this.values = values;
	}
	
	/**
	 * Computes a result, or throws an exception if unable to do so.
	 * Must make sure that the code that calls this deals with the closing the connection
	 * @return ResultSet of the result or null if failed
	 */
	@Override
	public ResultSet call() throws SQLException {
		PreparedStatement statement = DBConnector.getInstance().getConnection().prepareStatement(sql);
		if (values != null) {
			//iterate through list and set values based on types
			for (int i = 0, valuesSize = values.size(); i < valuesSize; i++) {
				Object value = values.get(i);
				
				if (value instanceof String)
					statement.setString(i+1, (String)value);
				else if (value instanceof Integer)
					statement.setInt(i+1, (Integer)value);
				else if (value instanceof LocalDate)
					statement.setDate(i + 1, Date.valueOf((LocalDate)value));
				else
					throw new SQLException("Invalid DataType");
			}
		}
		
		return statement.executeQuery();
	}
}
