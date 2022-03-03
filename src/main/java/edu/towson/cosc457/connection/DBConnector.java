package edu.towson.cosc457.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.towson.cosc457.App;
import edu.towson.cosc457.common.Popups;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Singleton Class creates a database connection through an ssh Tunnel
 */
public class DBConnector {
	private static final DBConnector instance = new DBConnector();
	
	// for more info on Hikari Connection Pool go here: https://github.com/brettwooldridge/HikariCP
	private HikariDataSource ds = new HikariDataSource();
	private final Tunnel tunnel;
	private final Dotenv dotenv;
	
	/**
	 * Creates the ssh Tunnel and configures the DataSource
	 */
	private DBConnector() {
		this.dotenv = App.dotenv;
		
		tunnel = new Tunnel();
		tunnel.go();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		HikariConfig config = new HikariConfig();
		
		config.setJdbcUrl(dotenv.get("SERVER_URL"));
		config.setUsername(dotenv.get("MY_USERNAME"));
		config.setPassword(dotenv.get("DB_PASSWORD"));
		config.setCatalog(dotenv.get("MY_USERNAME") + "db");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		try {
			ds = new HikariDataSource(config);
		}
		catch (Exception e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		
	}
	
	public static DBConnector getInstance() {
		return instance;
	}
	
	/**
	 * Gets a Connection from the DataSource
	 * @return A Connection from the DataSource Connection Pool
	 * @throws SQLException if database access error occurs
	 */
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	/**
	 * checks if both DataSource and Tunnel are closed
	 * @return true if both DataSource and Tunnel are closed, false otherwise
	 */
	public boolean isClosed() {
		return ds.isClosed() && !tunnel.isConnected();
	}
	
	/**
	 * Closes the DataSource, Connection Pool and Tunnel
	 */
	public void close() {
		ds.close();
		tunnel.close();
	}
	
}
