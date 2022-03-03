package edu.towson.cosc457.connection;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Callable;

public class SqlUpdateTask implements Callable<Integer> {
	
	private final String sql;
	private final List<Object> values;
	
	public SqlUpdateTask(String sql) {
		this(sql, null);
	}
	
	public SqlUpdateTask(String sql, List<Object> values) {
		this.sql = sql;
		this.values = values;
	}
	
	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return Integer that represents the number of rows affected or null if failed
	 */
	@Override
	public Integer call() throws SQLException {
		try (PreparedStatement statement = DBConnector.getInstance().getConnection().prepareStatement(sql)) {
			if (values != null) {
				//iterate through list and set values based on types
				for (int i = 0, valuesSize = values.size(); i < valuesSize; i++) {
					Object value = values.get(i);
					
					if (value instanceof String) {
						statement.setString(i + 1, (String) value);
					}
					else if (value instanceof Integer) {
						statement.setInt(i + 1, (Integer) value);
					}
					else if (value instanceof LocalDate) {
						statement.setDate(i + 1, Date.valueOf((LocalDate) value));
					}
					else {
						throw new SQLException("Invalid DataType");
					}
				}
			}
			
			return statement.executeUpdate();
		}
	}
}
