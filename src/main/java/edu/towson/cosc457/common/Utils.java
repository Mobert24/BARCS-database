package edu.towson.cosc457.common;


import edu.towson.cosc457.App;
import edu.towson.cosc457.connection.SqlQueryTask;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Utils {
	private static final ExecutorService executor = App.getExecutor();
	private Utils() {}
	
	public static boolean isAnimalIdValid(int id) {
		boolean isValid = false;
		Future<ResultSet> submit = executor.submit(
				new SqlQueryTask("SELECT A_ID FROM Animal WHERE A_ID=?", new ArrayList<>(Collections.singleton(id))));
		
		try (ResultSet results = submit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			if (results.first()) {
				isValid = true;
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
		return isValid;
	}
	
	//checks if foster exists and if they are not on quarantine
	public static boolean isValidFoster(int id) {
		List<Object> values = new ArrayList<>();
		values.add(id);
		boolean isValid = false;
		Future<ResultSet> submit = executor.submit(
				new SqlQueryTask("SELECT P_ID, FosterFlag, BannedYN FROM Person WHERE P_ID=?", values));
		
		Future<ResultSet> submit2 = executor.submit(
				new SqlQueryTask("SELECT DATE_ADD(StartDate, INTERVAL LengthQuar DAY) AS EndDate " +
						                 "FROM QLog WHERE FosterQ_ID=? HAVING EndDate > CURDATE()", values));
		
		try (ResultSet results = submit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			if (results.first()) {
				String fosterFlag = results.getString(2);
				String banned = results.getString(3);
				if (fosterFlag.equals("1") && banned.equalsIgnoreCase("N")) {
					isValid = true;
				}
				
				if (banned.equalsIgnoreCase("Y")) {
					Popups.errorAlert("Person is Banned");
				}
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
		
		//second query
		try (ResultSet results = submit2.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			if (results.first()) {
				Popups.infoAlert("Foster is on Quarantine");
				isValid = false;
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
		return isValid;
	}
	
	public static List<Pair<Integer, String>> getLocations(String... excluding) {
		StringBuilder query = new StringBuilder("SELECT L_ID, Name, Type FROM Location");
		List<Object> values = new ArrayList<>();
		Future<ResultSet> submit;
		
		int size = excluding.length;
		if (size != 0) {
			query.append(" WHERE ");
			for (int i = 0; i < size; i++) {
				query.append("Type<>?");
				values.add(excluding[i]);
				
				if (i < size - 1) {
					query.append(" AND ");
				}
			}
		}
		
		//execute query
		submit = executor.submit(new SqlQueryTask(query.toString(), values));
		
		List<Pair<Integer, String>> choices = new ArrayList<>();
		
		//get the results and put them into a List of Pairs
		try (ResultSet results = submit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			while (results.next()) {
				choices.add(new Pair<>(results.getInt(1), results.getString(2)));
			}
			
			return choices;
		}
		catch (ExecutionException | SQLException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		//if an exception was thrown, we return an empty list
		return new ArrayList<>();
	}
	
	public static List<String> getAllSpecies() {
		Future<ResultSet> submit = executor.submit(new SqlQueryTask("SELECT DISTINCT Species FROM Other"));
		
		try (ResultSet results = submit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			List<String> species = new ArrayList<>();
			
			while (results.next()) {
				species.add(results.getString(1));
			}
			
			return species;
		}
		catch (ExecutionException | SQLException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		//if an exception was thrown, we return an empty list
		return new ArrayList<>();
	}
	
	public static List<String> getAllPreferences() {
		Future<ResultSet> submit = executor.submit(new SqlQueryTask("SELECT DISTINCT Preference FROM Person"));
		
		try (ResultSet results = submit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			List<String> preferences = new ArrayList<>();
			
			while (results.next()) {
				preferences.add(results.getString(1));
			}
			
			return preferences;
		}
		catch (ExecutionException | SQLException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		//if an exception was thrown, we return an empty list
		return new ArrayList<>();
	}
	
	/**
	 * Sets the associated controls visibility and disabled to the opposite of what they were before
	 * There are constraints on naming:
	 * Original control ids must be <tab><field group><control name><control type>
	 * Ids passed to this method can use one or more of any of the groupings
	 * @param parent Pane containing Nodes to search
	 * @param id String regex to match against Node ids of Nodes to switch
	 * @param exclude String regex to match against Node ids of Nodes to exclude from switch
	 */
	public static void switchNodeVisibility(Pane parent, String id, String exclude) {
		//search inside Nodes who are Parents themselves
		//NOTE: a Parent should always be an instance of a Pane
		parent.getChildren()
		      .filtered(node -> node instanceof Pane)
		      .forEach(node -> switchNodeVisibility((Pane)node, id, exclude));
		
		//search in all the children who are not Parents
		parent.getChildren()
		      .filtered(
		      		node -> !(node instanceof Pane) &&
					        !String.valueOf(node.getId()).matches(exclude) &&
					        String.valueOf(node.getId()).matches(id))
		      .forEach(
		      		node -> {
				        if (node instanceof CheckBox && ((CheckBox) node).isSelected()) {
					        ((CheckBox) node).fire();
				        }
						else if (node instanceof RadioButton && ((RadioButton) node).isSelected()) {
							((RadioButton) node).fire();
						}
		      			node.setVisible(!node.isVisible());
		      			node.setDisable(!node.isDisabled());
		      });
	}
	
	//Restricts input to only integers
	public static TextFormatter<String> intFormatter() {
	    TextFormatter<String> intFormatter = new TextFormatter<>(change -> {
		    if (!change.isContentChange()) {
			return change;
		    }
		    
		    String newText = change.getText();
		    if (Pattern.matches("[^0-9]", newText)) {
			return null;
		    }
		    
		    return change;
		});
	    
	    return intFormatter;
	}
	
	//Restricts input to non-whitespace characters
	public static TextFormatter<String> whiteSpaceFormatter() {
	    TextFormatter<String> whiteSpaceFormatter = new TextFormatter<>(change -> {
		    if(!change.isContentChange()) {
			return change;
		    }
		    
		    String newText = change.getText();
		    if (newText.matches("\\s")) {
			return null;
		    }
		    
		    return change;
		});
		
		return whiteSpaceFormatter;
	}
	
	//Restricts middle initial field to one character
	public static TextFormatter<String> middleInitialFormatter() {
	    TextFormatter<String> middleInitialFormatter = new TextFormatter<>(change -> {
		    if(!change.isContentChange()) {
			return change;
		    }
		    
		    String newText = change.getControlNewText();
		    if (newText.length() > 1) {
			return null;
		    }
		    
		    return change;
		});
		
		return middleInitialFormatter;
	}
	
	//Restricts input to max of 10 numbers
	public static TextFormatter<String> phoneFormatter() {
	    TextFormatter<String> phoneFormatter = new TextFormatter<>(change -> {
		    if(!change.isContentChange()) {
			return change;
		    }
		    
		    String newText = change.getControlNewText();
		    String changeText = change.getText();
		    if (newText.length() > 10 || changeText.matches("[^0-9]")) {
			return null;
		    }
		    
		    return change;
		});
		
		return phoneFormatter;
	}
	
	//Restricts input to max of 9 numbers
	public static TextFormatter<String> SSNFormatter() {
	    TextFormatter<String> SSNFormatter = new TextFormatter<>(change -> {
		    if(!change.isContentChange()) {
			return change;
		    }
		    
		    String newText = change.getControlNewText();
		    String changeText = change.getText();
		    if (newText.length() > 9 || changeText.matches("[^0-9]")) {
			return null;
		    }
		    
		    return change;
		});
		
		return SSNFormatter;
	}
	
	//Restricts input to max of 5 numbers
	//Restricts input to max of 9 numbers
	public static TextFormatter<String> ZIPFormatter() {
	    TextFormatter<String> ZIPFormatter = new TextFormatter<>(change -> {
		    if(!change.isContentChange()) {
			return change;
		    }
		    
		    String newText = change.getControlNewText();
		    String changeText = change.getText();
		    if (newText.length() > 5 || changeText.matches("[^0-9]")) {
			return null;
		    }
		    
		    return change;
		});
		
		return ZIPFormatter;
	}
}
