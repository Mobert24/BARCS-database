package edu.towson.cosc457.connection;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import edu.towson.cosc457.App;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Properties;

/**
 * Connects to a server through ssh
 */
class Tunnel {
	private final JSch jsch;
	private final Dotenv dotenv;
	private final Properties config;
	
	private Session session;
	
	Tunnel() {
		jsch = new JSch();
		dotenv = App.dotenv;
		config = new Properties();
		config.put("StrictHostKeyChecking", "no");
	}
	
	/**
	 * Connect to the server through ssh
	 */
	public void go() {
		try {
			session = jsch.getSession(dotenv.get("MY_USERNAME"), dotenv.get("SSH_HOST"), 22);
			session.setPassword(dotenv.get("SSH_PASSWORD"));
			session.setConfig(config);
			session.connect();
			
			if(session.isConnected()) {
				System.out.println("SSH Connected...");
			}
		}
		catch (JSchException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * checks if the Tunnel is connected
	 * @return true if the Tunnel is connected
	 */
	public boolean isConnected() {
		return session.isConnected();
	}

	public void close() {
		session.disconnect();
	}
	
}
