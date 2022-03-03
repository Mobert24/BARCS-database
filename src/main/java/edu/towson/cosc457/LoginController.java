package edu.towson.cosc457;

import edu.towson.cosc457.common.Popups;
import edu.towson.cosc457.connection.SqlQueryTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class LoginController {
	@FXML private TextField loginPasswordTextField;
	@FXML private Button loginButton;
	
	private final ExecutorService executor;
	
	public LoginController() {
		executor = App.getExecutor();
	}
	
	@FXML
	private void loginButtonClicked(ActionEvent event) throws IOException {
		try {
			int id = Integer.parseInt(loginPasswordTextField.getText());
			if (getAssociations(id)) {
				switchToInput();
			}
			else {
				throw new NumberFormatException("Invalid Staff Number");
			}
		}
		catch (NumberFormatException e) {
			Popups.errorAlert("Invalid Staff Number.");
		}
	}
	
	private boolean getAssociations(int id) {
		boolean isSuccessful = false;
		List<Object> values = new ArrayList<>();
		values.add(id);
		
		Future<ResultSet> submit = executor.submit(
				new SqlQueryTask("SELECT Association FROM Associations WHERE AssocP_ID=?", values));
		
		Future<ResultSet> submit2 = executor.submit(new SqlQueryTask("SELECT VetFlag FROM Person WHERE P_ID=?", values));
		
		List<String> associations = new ArrayList<>();
		
		try (ResultSet results = submit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			while (results.next()) {
				associations.add(results.getString(1));
			}
		}
		catch (SQLException | ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		//for vets
		try (ResultSet results = submit2.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			if (results.first() && results.getString(1).equals("1")) {
				associations.add("Veterinarian");
			}
		}
		catch (SQLException | ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		isSuccessful = !associations.isEmpty();
		
		if (isSuccessful) {
			App.setUserAssociations(associations);
		}
		
		return isSuccessful;
	}
	
	private void switchToInput() throws IOException {
		App.setRoot("input");
	}
}
