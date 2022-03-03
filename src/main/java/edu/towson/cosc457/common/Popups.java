package edu.towson.cosc457.common;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

public class Popups {
	
	private Popups() {}
	
	/**
	 *
	 * @param values List of values for the user to choose from
	 * @param reason String to display in the dialog box
	 * @param <T> The Type From the List of values
	 * @return the result or null if no result selected
	 */
	public static <T> T displayChoiceDialog(List<T> values, String reason) {
		T defaultValue = values.get(0);
		var choiceDlg = new ChoiceDialog<>(defaultValue, values);
		choiceDlg.setTitle(reason + " Selector");
		choiceDlg.setHeaderText("Select a " + reason);
		choiceDlg.setContentText(reason + ":");
		
		//show the choice dialog box and wait for a response
		var result = choiceDlg.showAndWait();
		return result.orElse(null);
		
	}
	
	/**
	 * Builds a TableView and returns it
	 * @param headers a List consisting of the column names
	 * @param data the data to put into the table
	 * @return A completed TableView with its data
	 */
	public static TableView<ObservableList<Object>> buildDataTable(List<String> headers, ObservableList<ObservableList<Object>> data) {
		TableView<ObservableList<Object>> dataTable = new TableView<>();
		
		//for each column add the header text, then specify how to get the data to populate it.
		int size = headers.size();
		for (int i = 0; i < size; i++) {
			final int cellIndex = i;
			TableColumn<ObservableList<Object>, Object> column = new TableColumn<>(headers.get(i));
			column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(cellIndex)));
			dataTable.getColumns().add(column);
		}
		dataTable.setItems(data);
		
		return dataTable;
	}
	
	/**
	 * Must ensure that the number of tableNames is equivalent to the number of tables passed
	 * @param tableNames the List of names of all the tables passed to this method
	 * @param tables the tables of data passed to this method
	 */
	public static void displayDataTables(List<String> tableNames, List<TableView<ObservableList<Object>>> tables) {
		
		if (tableNames.size() != tables.size()) {
			errorAlert("Number of table names does not match number of tables");
			return;
		}
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(700, 500);
		scrollPane.setFitToWidth(true);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		VBox vBox = new VBox(5);
		vBox.setPadding(new Insets(10,10,10,10));
		
		int size = tableNames.size();
		for (int i = 0; i < size; i++) {
			Label tableName = new Label(tableNames.get(i));
			tableName.setFont(Font.font("System", FontWeight.BOLD, 14));
			tableName.setUnderline(true);
			vBox.getChildren().add(tableName);
			vBox.getChildren().add(tables.get(i));
		}
		
		scrollPane.setContent(vBox);
		Group group = new Group(scrollPane);
		Scene scene = new Scene(group, 700, 500);
		Stage stage = new Stage();
		stage.setTitle("Report");
		stage.setScene(scene);
		stage.showAndWait();
	}
	
	public static String displayInputDialog(String reason) {
		var inputDlg = new TextInputDialog();
		inputDlg.setTitle(reason + " Selector");
		inputDlg.setHeaderText("Select a " + reason);
		inputDlg.setContentText(reason + ":");
		
		//show the choice dialog box and wait for a response
		var result = inputDlg.showAndWait();
		return result.orElse(null);
	}
	
	public static void infoAlert(String message) {
		Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
		infoAlert.setTitle("Notice");
		infoAlert.setHeaderText(null);
		
		infoAlert.setContentText(message);
		infoAlert.showAndWait();
	}
        
	public static void errorAlert(String errorMsg) {
		Alert invalidInput = new Alert(Alert.AlertType.ERROR);
		invalidInput.setTitle("Error");
		invalidInput.setHeaderText(null);
		
		invalidInput.setContentText(errorMsg);
		invalidInput.showAndWait();
	}
}
