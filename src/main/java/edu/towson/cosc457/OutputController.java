package edu.towson.cosc457;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Pair;

import edu.towson.cosc457.common.*;
import edu.towson.cosc457.connection.*;

public class OutputController {
	
	//<editor-fold desc="All FXML Controls">
	@FXML private BorderPane outputBorderPane;
	
	@FXML private Button secondaryButton; //switches the view from output to input
	
	//<editor-fold desc="Person Tab Controls">
	@FXML private Tab personTab; //controls after here are in the person tab
	
	@FXML private AnchorPane personAnchorPane;
	
	@FXML private CheckBox personStaffCheckBox;
	@FXML private CheckBox personFosterCheckBox;
	@FXML private CheckBox personAdopterCheckBox;
	@FXML private CheckBox personVolunteerCheckBox;
	@FXML private CheckBox personVetCheckBox;
	
	@FXML private ToggleGroup personBannedToggleGroup;
	@FXML private RadioButton personBannedNoneRadioButton;
	@FXML private RadioButton personBannedNotRadioButton;
	@FXML private RadioButton personBannedYesRadioButton;
	
	@FXML private GridPane personGridPane;
	
	@FXML private CheckBox personNameCheckBox;
	@FXML private HBox personNameFirstHBox;
	@FXML private Label personNameFirstLabel;
	@FXML private TextField personNameFirstTextField;
	
	@FXML private HBox personNameMiddleHBox;
	@FXML private Label personNameMiddleLabel;
	@FXML private TextField personNameMiddleTextField;
	
	@FXML private HBox personNameLastHBox;
	@FXML private Label personNameLastLabel;
	@FXML private TextField personNameLastTextField;
	
	@FXML private CheckBox personStaffAssociationsCheckBox;
	@FXML private ChoiceBox<String> personStaffAssociationsChoiceBox;
	
	@FXML private CheckBox personFosterPrefCheckBox;
	@FXML private ChoiceBox<String> personFosterPrefChoiceBox;
	@FXML private CheckBox personFosterQuarantineCheckBox;
	@FXML private CheckBox personFosterAnimalsCheckBox;
	
	@FXML private CheckBox personVolunteerLevelCheckBox;
	@FXML private ChoiceBox<String> personVolunteerLevelChoiceBox;
	//</editor-fold>
	
	//<editor-fold desc="Animal Tab Controls">
	@FXML private Tab animalTab; //controls after this are in the animal tab
	
	@FXML private AnchorPane animalAnchorPane; //the anchor pane contains all the controls for filtering the queries
	
	@FXML private CheckBox animalCatCheckBox;
	@FXML private CheckBox animalDogCheckBox;
	@FXML private CheckBox animalOtherCheckBox;
	@FXML private CheckBox animalCatWorkingCheckBox;
	
	@FXML private ToggleGroup animalFixedToggleGroup;
	@FXML private RadioButton animalFixedYesRadioButton;
	@FXML private RadioButton animalFixedNotRadioButton;
	@FXML private RadioButton animalFixedNoneRadioButton;
	
	@FXML private GridPane animalGridPane;
	
	@FXML private CheckBox animalNameCheckBox;
	@FXML private ChoiceBox<String> animalNameFilterChoiceBox;
	@FXML private TextField animalNameTextField;
	
	@FXML private CheckBox animalParentCheckBox;
	
	@FXML private CheckBox animalHeightCheckBox;
	@FXML private ChoiceBox<String> animalHeightFilterChoiceBox;
	@FXML private TextField animalHeightTextField;
	
	@FXML private CheckBox animalWeightCheckBox;
	@FXML private ChoiceBox<String> animalWeightFilterChoiceBox;
	@FXML private TextField animalWeightTextField;
	
	@FXML private CheckBox animalSizeCheckBox;
	@FXML private ChoiceBox<String> animalSizeChoiceBox;
	
	@FXML private CheckBox animalDOBCheckBox;
	@FXML private ChoiceBox<String> animalDOBFilterChoiceBox;
	@FXML private DatePicker animalDOBStartDatePicker;
	@FXML private DatePicker animalDOBEndDatePicker;
	
	@FXML private CheckBox animalBreedCheckBox;
	@FXML private ChoiceBox<String> animalBreedFilterChoiceBox;
	@FXML private TextField animalBreedTextField;
	
	@FXML private CheckBox animalGenderCheckBox;
	@FXML private HBox animalGenderHBox;
	@FXML private ToggleGroup animalGenderToggleGroup;
	@FXML private RadioButton animalGenderMaleRadioButton;
	@FXML private RadioButton animalGenderFemaleRadioButton;
	
	@FXML private CheckBox animalAgeCheckBox;
	@FXML private ChoiceBox<String> animalAgeFilterChoiceBox;
	@FXML private TextField animalAgeTextField;
	@FXML private ChoiceBox<String> animalAgeUnitsChoiceBox;
	
	@FXML private CheckBox animalStageCheckBox;
	@FXML private ChoiceBox<String> animalStageChoiceBox;
	
	@FXML private CheckBox animalLocationCheckBox;
	@FXML private ChoiceBox<Pair<Integer, String>> animalLocationChoiceBox;
	
	@FXML private CheckBox animalOutcomeCheckBox;
	@FXML private ChoiceBox<String> animalOutcomeChoiceBox;
	@FXML private DatePicker animalOutcomeFromDatePicker;
	@FXML private DatePicker animalOutcomeToDatePicker;
	
	@FXML private CheckBox animalOtherSpeciesCheckBox;
	@FXML private ChoiceBox<String> animalOtherSpeciesChoiceBox;
	//</editor-fold>
	
	//<editor-fold desc="Medical Tab Controls">
	@FXML private Tab medicalTab; //controls after here are in the medical tab
	
	@FXML private AnchorPane medicalAnchorPane;
	
	@FXML private ChoiceBox<String> medicalEntryFilterChoiceBox;
	@FXML private TextField medicalEntryTextField;
	//</editor-fold>
	
	//in the ButtonBar
	@FXML private Button generateReportButton;
	//</editor-fold>
	
	//<editor-fold desc="Constants">
	private static final String[] STRING_QUERY_FILTERS = {"IS", "CONTAINS", };
	private static final String[] NUMBER_QUERY_FILTERS = {"IS", "GREATER THAN", "GREATER THAN OR EQUAL", "LESS THAN", "LESS THAN OR EQUAL"};
	private static final String[] DATE_QUERY_FILTERS = {"IS", "IS ON OR AFTER", "BEFORE", "ON OR BEFORE", "IN THE RANGE OF"};
	
	private static final Map<String, String> QUERY_FILTERS_MAP = Map.of("IS", "=",
	                                                                   "CONTAINS", "%",
	                                                                   "GREATER THAN", ">",
	                                                                   "GREATER THAN OR EQUAL", ">=",
	                                                                   "LESS THAN", "<",
	                                                                   "LESS THAN OR EQUAL", "<=",
	                                                                   "IS ON OR AFTER", ">=",
	                                                                   "BEFORE", "<",
	                                                                   "ON OR BEFORE", "<=",
	                                                                   "IN THE RANGE OF", ">=,<=");
	private static final Map<String, String> AGE_FLIP_SIGN = Map.of("=", "=",
	                                                                ">", "<",
	                                                                "<", ">",
	                                                                ">=", "<=",
	                                                                "<=", ">=");
	
	private static final String[] VOL_LEVELS = {"1", "2", "3"};
	private static final String[] ASSOCIATIONS = {"Administrator", "Coordinator", "General Staff"};
	private static final String[] AGE_UNITS = {"Years", "Weeks"};
	private static final String[] SIZES = {"Large", "Medium", "Small"};
	private static final String[] STAGES = {"Available", "Pending Medical", "Behavior", "Working Cat Queue", "Bottle Babies", "Orphan Kittens", "With Kittens", "Bite Quarantine"};
	private static final String[] OUTCOMES = {"Adoption", "Transfer", "Wildlife", "Service", "Died", "Euthanasia", "Clinic", "Release"};
	private static final String[] MEDICAL_OPTIONS = {"Animal Name", "Medical Record"};
	//</editor-fold>
	
	private final ExecutorService executor;
	
	public OutputController() {
		executor = App.getExecutor();
	}
	
	@FXML
	private void initialize() {
		//query for locations
		List<Pair<Integer, String>> locations = Utils.getLocations("Foster");
		
		//query for species
		List<String> species = Utils.getAllSpecies();
		
		//query for foster preferences
		List<String> preferences = Utils.getAllPreferences();
		
		Utils.switchNodeVisibility(personGridPane, "person[a-zA-Z]+", "personNameCheckBox");
		Utils.switchNodeVisibility(animalGridPane, "animal[a-zA-Z]+", "[a-zA-Z]+CheckBox");
		Utils.switchNodeVisibility(animalGridPane, "animalOtherSpeciesCheckBox", "");
		Utils.switchNodeVisibility(animalAnchorPane, "animalCatWorkingCheckBox", "");
		
		//load constants
		//person
		personStaffAssociationsChoiceBox.setItems(FXCollections.observableList(Arrays.asList(ASSOCIATIONS)));
		personStaffAssociationsChoiceBox.setValue(ASSOCIATIONS[0]);
		personFosterPrefChoiceBox.setItems(FXCollections.observableList(preferences));
		personFosterPrefChoiceBox.setValue(preferences.get(0));
		personVolunteerLevelChoiceBox.setItems(FXCollections.observableList(Arrays.asList(VOL_LEVELS)));
		personVolunteerLevelChoiceBox.setValue(VOL_LEVELS[0]);
		
		//animal
		animalNameFilterChoiceBox.setItems(FXCollections.observableList(Arrays.asList(STRING_QUERY_FILTERS)));
		animalNameFilterChoiceBox.setValue(STRING_QUERY_FILTERS[0]);
		animalHeightFilterChoiceBox.setItems(FXCollections.observableList(Arrays.asList(NUMBER_QUERY_FILTERS)));
		animalHeightFilterChoiceBox.setValue(NUMBER_QUERY_FILTERS[0]);
		animalWeightFilterChoiceBox.setItems(FXCollections.observableList(Arrays.asList(NUMBER_QUERY_FILTERS)));
		animalWeightFilterChoiceBox.setValue(NUMBER_QUERY_FILTERS[0]);
		animalDOBFilterChoiceBox.setItems(FXCollections.observableList(Arrays.asList(DATE_QUERY_FILTERS)));
		animalDOBFilterChoiceBox.setValue(DATE_QUERY_FILTERS[0]);
		animalBreedFilterChoiceBox.setItems(FXCollections.observableList(Arrays.asList(STRING_QUERY_FILTERS)));
		animalBreedFilterChoiceBox.setValue(STRING_QUERY_FILTERS[0]);
		animalAgeFilterChoiceBox.setItems(FXCollections.observableList(Arrays.asList(NUMBER_QUERY_FILTERS)));
		animalAgeFilterChoiceBox.setValue(NUMBER_QUERY_FILTERS[0]);
		animalAgeUnitsChoiceBox.setItems(FXCollections.observableList(Arrays.asList(AGE_UNITS)));
		animalAgeUnitsChoiceBox.setValue(AGE_UNITS[0]);
		
		
		animalSizeChoiceBox.setItems(FXCollections.observableList(Arrays.asList(SIZES)));
		animalSizeChoiceBox.setValue(SIZES[0]);
		animalStageChoiceBox.setItems(FXCollections.observableList(Arrays.asList(STAGES)));
		animalStageChoiceBox.setValue(STAGES[0]);
		//todo: switch dropdowns that use Pair to ComboBox
		// find out how to do display values so that we only display the value, but it is still a Pair
		animalLocationChoiceBox.setItems(FXCollections.observableList(locations));
		animalLocationChoiceBox.setValue(locations.isEmpty()
				                                 ? new Pair<>(-1, "No Locations Found")
				                                 : locations.get(0));
		animalOutcomeChoiceBox.setItems(FXCollections.observableList(Arrays.asList(OUTCOMES)));
		animalOutcomeChoiceBox.setValue(OUTCOMES[0]);
		animalOtherSpeciesChoiceBox.setItems(FXCollections.observableList(species));
		animalOtherSpeciesChoiceBox.setValue(species.isEmpty() ? "No Species Found" : species.get(0));
		
		//medical
		medicalEntryFilterChoiceBox.setItems(FXCollections.observableList(Arrays.asList(MEDICAL_OPTIONS)));
		medicalEntryFilterChoiceBox.setValue(MEDICAL_OPTIONS[0]);
	}
	
	@FXML
	private void switchToInput() throws IOException {
		App.setRoot("input");
	}
	
	@FXML
	private void logout() throws IOException {
		App.setRoot("login");
	}
	
	@FXML
	private void generateReportButtonClicked(ActionEvent event) {
		if (personTab.isSelected()) {
			generatePersonReport();
		}
		else if (animalTab.isSelected()) {
			generateAnimalReport();
		}
		else if (medicalTab.isSelected()) {
			generateMedicalReport();
		}
	}
	
	@FXML
	private void selectStaffCheckBox(ActionEvent event) {
		if (!personStaffCheckBox.isSelected() && personVetCheckBox.isSelected()) {
			personVetCheckBox.setSelected(false);
		}
		selectOuterCheckBox(event);
	}
	
	@FXML
	private void selectVetCheckBox(ActionEvent event) {
		if (!personStaffCheckBox.isSelected() && personVetCheckBox.isSelected())
			personStaffCheckBox.fire();
	}
	
	@FXML
	private void selectCatCheckBox(ActionEvent event) {
		if (!animalCatCheckBox.isSelected() && animalCatWorkingCheckBox.isSelected()) {
			animalCatWorkingCheckBox.setSelected(false);
		}
		selectOuterCheckBox(event);
	}
	
	//this only applies to Fosters
	@FXML
	private void selectFosterFiltersCheckBox(ActionEvent event) {
		if (personFosterQuarantineCheckBox.isSelected() || personFosterAnimalsCheckBox.isSelected()) {
			//make the two checkboxes exclusive of each other
			if (personFosterQuarantineCheckBox.isSelected()) {
				personFosterAnimalsCheckBox.setDisable(true);
			}
			else {
				personFosterQuarantineCheckBox.setDisable(true);
			}
			
			if (personStaffCheckBox.isSelected()) {
				personStaffCheckBox.fire();
			}
			if (personVolunteerCheckBox.isSelected()) {
				personVolunteerCheckBox.fire();
			}
			if (personAdopterCheckBox.isSelected()) {
				personAdopterCheckBox.fire();
			}
			
			personStaffCheckBox.setDisable(true);
			personVetCheckBox.setDisable(true);
			personVolunteerCheckBox.setDisable(true);
			personAdopterCheckBox.setDisable(true);
		}
		else if (!personFosterQuarantineCheckBox.isSelected() && !personFosterAnimalsCheckBox.isSelected()) {
			personStaffCheckBox.setDisable(false);
			personVetCheckBox.setDisable(false);
			personVolunteerCheckBox.setDisable(false);
			personAdopterCheckBox.setDisable(false);
			personFosterQuarantineCheckBox.setDisable(false);
			personFosterAnimalsCheckBox.setDisable(false);
		}
	}
	
	// this is for the animal/person type CheckBoxes outside of the gridPane
	// this only switches visibility of other CheckBoxes in the same group as the calling CheckBox
	@FXML
	private void selectOuterCheckBox(ActionEvent event) {
		CheckBox checkBox = (CheckBox)event.getSource();
		Pane parent = (Pane)checkBox.getParent();
		
		String originalId = checkBox.getId();
		String id = originalId.replace("CheckBox", "[a-zA-Z]+CheckBox");
		
		Utils.switchNodeVisibility(parent, id, originalId);
	}
	
	//only use with checkBoxes
	@FXML
	private void selectCheckBox(ActionEvent event) {
		CheckBox checkBox = (CheckBox)event.getSource();
		Pane parent = (Pane)checkBox.getParent();
		
		String originalId = checkBox.getId();
		String id = originalId.replace("CheckBox", "[a-zA-Z]+");
		
		//originalId allows us to exclude the CheckBox that is the source of the event
		Utils.switchNodeVisibility(parent, id, originalId);
	}
	
	//<editor-fold desc="Person Tab Methods">
	//generate the report for the person
	//todo: can add the phone numbers for a person in a separate table and add it to the output
	private void generatePersonReport() {
		StringBuilder query = new StringBuilder("SELECT * FROM Person");
		List<String> conditions = new ArrayList<>();
		List<String> joins = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		
		//collect info from non-disabled controls
		boolean isNameValid = getPersonNameIDForQuery(conditions, values);
		getPersonBannedForQuery(conditions, values);
		getPersonTypesForQuery(query, joins, conditions, values);
		
		//if an error occurred in any of the get methods, exit buildQuery
		if (!isNameValid) {
			return;
		}
		
		//exit the build query early if either of these ran and returned true
		if (handleFostersOnQuarantine() || handleAnimalsOnly()) {
			return;
		}
		
		String sql = buildQuery(query, joins, conditions);          //collate them into a sql statement
		
		//run sql query and handle the results
		handleQueries(sql, values);
	}
	
	private String searchPerson() {
		String firstName = personNameFirstTextField.getText();
		String middleInitial = personNameMiddleTextField.getText();
		String lastName = personNameLastTextField.getText();
		
		if (firstName.isBlank() && lastName.isBlank()) {
			Popups.errorAlert("You must enter at least the first or last name for a Person");
			return "";
		}
		
		if (middleInitial.length() > 1) {
			Popups.errorAlert("Only One letter is allowed for the Middle Initial");
			return "";
		}
		
		//database query  for valid names
		StringBuilder query = new StringBuilder("SELECT P_ID, FName, MI, LName FROM Person WHERE ");
		List<String> queryParts = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		
		if (!firstName.isBlank()) {
			values.add(firstName);
			queryParts.add("FName=?");
		}
		if (!middleInitial.isBlank()) {
			values.add(middleInitial);
			queryParts.add("MI=?");
		}
		if (!lastName.isBlank()) {
			values.add(lastName);
			queryParts.add("LName=?");
		}
		
		//build the String for the query
		query.append(queryParts.get(0));
		for (int i = 1; i < queryParts.size(); i++) {
			query.append(" AND ").append(queryParts.get(i));
		}
		query.append(" ORDER BY P_ID");
		
		final Future<ResultSet> submit = executor.submit(new SqlQueryTask(query.toString(), values));
		
		List<Pair<String, String>> choices = new ArrayList<>();
		
		try (ResultSet results = submit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			while (results.next()) {
				choices.add(new Pair<>(results.getString(1),
				                       results.getString(2) + " " +
						                       results.getString(3) + " " +
						                       results.getString(4))
				);
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
		
		//call the choice dialog to have user select one a set of options
		if (choices.size() > 1) {
			Pair<String, String> person = Popups.displayChoiceDialog(choices, "People");
			
			return person != null ? person.getKey() : "";
		}
		else if (choices.size() == 1) {         //if only one person returned from query then return
			//return the id
			return choices.get(0).getKey();
		}
		else {
			return "";
		}
	}
	
	private boolean getPersonNameIDForQuery(List<String> conditions, List<Object> values) {
		if (personNameCheckBox.isSelected()) {
			String id;
			
			//db query to make sure we know which person needs to be used
			id = searchPerson(); //returns the value to be used in query
			
			if (id.isBlank()) {
				Popups.infoAlert("No Name matches your search");
				return false;
			}
			
			values.add(id);
			conditions.add("P_ID=?");
		}
		return true;
	}
	
	private void getPersonBannedForQuery(List<String> conditions, List<Object> values) {
		if (!personBannedNoneRadioButton.isSelected()) {
			values.add(personBannedYesRadioButton.isSelected() ? "Y" : "N");
			conditions.add("BannedYN=?");
		}
	}
	
	private void getPersonTypesForQuery(StringBuilder query, List<String> joins, List<String> conditions, List<Object> values) {
		//staff
		if (personStaffCheckBox.isSelected()) {
			values.add("1");
			
			String assoc = "";
			if (personStaffAssociationsCheckBox.isSelected()) {
				//todo: may not want to use a join here
				query.append(", Associations"); //add table
				joins.add("AssocP_ID=P_ID"); //join condition
				values.add(personStaffAssociationsChoiceBox.getValue());
				assoc = " AND Association=?";
			}
			conditions.add("(StaffFlag=?" + assoc + ")");
			
			if (personVetCheckBox.isSelected()) {
				values.add("1");
				conditions.add("VetFlag=?");
			}
		}
		
		//foster
		if (personFosterCheckBox.isSelected()) {
			values.add("1");
			
			String pref = "";
			if (personFosterPrefCheckBox.isSelected()) {
				values.add(personFosterPrefChoiceBox.getValue());
				pref = " AND Preference=?";
			}
			conditions.add("(FosterFlag=?" + pref + ")");
			
			//make method calls for the quarantine in the generate method
			//make method calls for the show animals only in the generate method
		}
		
		//adopter
		if (personAdopterCheckBox.isSelected()) {
			values.add("1");
			conditions.add("AdopterFlag=?");
		}
		
		//volunteer
		if (personVolunteerCheckBox.isSelected()) {
			values.add("1");
			
			String level = "";
			if (personVolunteerLevelCheckBox.isSelected()) {
				values.add(personVolunteerLevelChoiceBox.getValue());
				level = " AND VolLevel=?";
			}
			conditions.add("(VolFlag=?" + level + ")");
		}
	}
	
	//will show fosters currently on quarantine
	private boolean handleFostersOnQuarantine() {
		if (personFosterQuarantineCheckBox.isSelected()) {
			String query = "SELECT P_ID, FName, MI, LName, StartDate, " +
					"DATE_ADD(StartDate, INTERVAL LengthQuar DAY) AS EndDate, Reason, Description " +
					"FROM Person, QLog WHERE P_ID=FosterQ_ID HAVING EndDate > CURDATE()";
			
			handleQueries(query, new ArrayList<>());
			return true;
		}
		return false;
	}
	
	//will only show the animals associated with a foster
	private boolean handleAnimalsOnly() {
		if (personFosterAnimalsCheckBox.isSelected()) {
			if (!personNameCheckBox.isSelected()) {
				Popups.errorAlert("You must select a person to see the animals for a Foster");
				return true; //we have to return true to exit from the outer method that calls this
			}
			
			String id = searchPerson();
			
			if (id.isBlank()) {
				Popups.infoAlert("No Name matches your search");
				return true;
			}
			
			List<Object> values = new ArrayList<>();
			values.add(id);
			String query = "SELECT * FROM Animal, Foster_Log WHERE A_ID=FosterA_ID AND Foster_ID=?";
			
			handleQueries(query, values);
			return true;
		}
		return false;
	}
	//</editor-fold>
	
	//<editor-fold desc="Animal Tab Methods">
	//generate the report for the animal
	private void generateAnimalReport() {
		StringBuilder query = new StringBuilder("SELECT * FROM Animal");
		List<String> conditions = new ArrayList<>();
		List<String> joins = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		
		//collect info from non-disabled controls
		boolean isNameValid = getAnimalNameIDForQuery(conditions, values);      //name
		boolean isHeightValid = getAnimalHeightForQuery(conditions, values);    //height
		boolean isWeightValid = getAnimalWeightForQuery(conditions, values);    //weight
		getAnimalSizeForQuery(conditions, values);                              //size
		boolean isDOBValid = getAnimalDOBForQuery(conditions, values);          //date of birth
		boolean isBreedValid = getAnimalBreedForQuery(conditions, values);      //breed
		getAnimalGenderForQuery(conditions, values);                            //gender
		boolean isAgeValid = getAnimalAgeForQuery(conditions, values);          //age
		getAnimalStageForQuery(conditions, values);                             //stage
		getAnimalsLocationForQuery(conditions, values);                         //location
		getAnimalOutcomeForQuery(conditions, values);                           //outcome
		getAnimalFixedForQuery(conditions, values);                             //fixed
		getAnimalTypesForQuery(conditions, values);                             //animal types
		
		//if an error occurred in any of the get methods, exit buildQuery
		if (!isNameValid || !isHeightValid || !isWeightValid || !isDOBValid || !isBreedValid || !isAgeValid) {
			return;
		}
		
		String sql = buildQuery(query, joins, conditions);          //collate them into a sql statement
		
		//run sql query and handle the results
		handleQueries(sql, values);
	}
	
	private String searchAnimal(String filter) {
		String name = animalNameTextField.getText().replace("'", "’");
		
		if (name.isBlank()) {
			Popups.errorAlert("You must enter an animal name");
			return "";
		}
		
		//database query for valid names
		List<Object> values = new ArrayList<>();
		values.add(filter.equals("=") ? name : "%" + name + "%");
		
		final Future<ResultSet> submit = executor.submit(
				new SqlQueryTask("SELECT A_ID, Name FROM Animal WHERE Name LIKE ? ORDER BY A_ID", values));
		
		List<Pair<String, String>> choices = new ArrayList<>();
		
		try (ResultSet results = submit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			while (results.next()) {
				choices.add(new Pair<>(results.getString(1), results.getString(2)));
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
		
		//call the choice dialog to have user select one a set of options
		if (choices.size() > 1) {
			Pair<String, String> animal = Popups.displayChoiceDialog(choices, "Animal");
			
			return animal != null ? animal.getKey() : "";
		}
		else if (choices.size() == 1) {         //if only one animal returned from query then return
			//return the id
			return choices.get(0).getKey();
		}
		else {
			return "";
		}
	}
	
	private boolean getAnimalNameIDForQuery(List<String> conditions, List<Object> values) {
		if (animalNameCheckBox.isSelected()) {
			String id;
			String filter = QUERY_FILTERS_MAP.get(animalNameFilterChoiceBox.getValue());
			
			//db query to make sure we know which animal needs to be used
			id = searchAnimal(filter); //returns value to use in query
			
			//display children must require a name be entered
			if (id.isBlank() && animalParentCheckBox.isSelected()) {
				Popups.errorAlert("To display children, you must enter a valid name");
				return false;
			}
			else if (animalParentCheckBox.isSelected()) {
				values.add(id); //for children's parent
				values.add(id); //for parent
				conditions.add("( Parent_ID=? OR A_ID=? )");
			}
			else {
				if (id.isBlank()) {
					Popups.infoAlert("No Name matches your search");
					return false;
				}
				
				values.add(id);
				conditions.add("A_ID=?");
			}
		}
		return true;
	}
	
	private boolean getAnimalHeightForQuery(List<String> conditions, List<Object> values) {
		if (animalHeightCheckBox.isSelected()) {
			String filter;
			int height;
			
			try {
				height = Integer.parseInt(animalHeightTextField.getText());
			}
			catch (NumberFormatException e) {
				Popups.errorAlert("Enter a valid integer for the animal height");
				return false;
			}
			
			filter = QUERY_FILTERS_MAP.get(animalHeightFilterChoiceBox.getValue());
			values.add(height);
			conditions.add("Height" + filter + "?");
		}
		return true;
	}
	
	private boolean getAnimalWeightForQuery(List<String> conditions, List<Object> values) {
		if (animalWeightCheckBox.isSelected()) {
			String filter;
			int weight;
			
			try {
				weight = Integer.parseInt(animalWeightTextField.getText());
			}
			catch (NumberFormatException e) {
				Popups.errorAlert("Enter a valid integer for the animal weight");
				return false;
			}
			
			filter = QUERY_FILTERS_MAP.get(animalWeightFilterChoiceBox.getValue());
			values.add(weight);
			conditions.add("Weight" + filter + "?");
		}
		return true;
	}
	
	private void getAnimalSizeForQuery(List<String> conditions, List<Object> values) {
		if (animalSizeCheckBox.isSelected()) {
			values.add(animalSizeChoiceBox.getValue());
			conditions.add("Size=?");
		}
	}
	
	private boolean getAnimalDOBForQuery(List<String> conditions, List<Object> values) {
		if (animalDOBCheckBox.isSelected()) {
			String filter;
			LocalDate startDate;
			LocalDate endDate;
			if (animalDOBStartDatePicker.getValue() == null) {
				Popups.errorAlert("Choose a value for the animal's Start Date of Birth");
				return false;
			}
			
			startDate = animalDOBStartDatePicker.getValue();
			filter = QUERY_FILTERS_MAP.get(animalDOBFilterChoiceBox.getValue());
			values.add(startDate);
			
			if (filter.equals(">=,<=")) {
				endDate = animalDOBEndDatePicker.getValue();
				values.add(endDate);
				conditions.add("DOB>=? AND DOB<=?");
			}
			else {
				conditions.add("DOB" + filter + "?");
			}
		}
		return true;
	}
	
	private boolean getAnimalBreedForQuery(List<String> conditions, List<Object> values) {
		if (animalBreedCheckBox.isSelected()) {
			String filter;
			String breed;
			
			filter = QUERY_FILTERS_MAP.get(animalBreedFilterChoiceBox.getValue());
			
			breed = animalBreedTextField.getText();
			if (breed.isBlank()) {
				Popups.errorAlert("Enter a value for the animal breed");
				return false;
			}
			
			breed = filter.equals("=") ? breed : "%" + breed + "%";
			
			values.add(breed);
			conditions.add("Breed=?");
		}
		return true;
	}
	
	private void getAnimalGenderForQuery(List<String> conditions, List<Object> values) {
		if (animalGenderCheckBox.isSelected()) {
			values.add(animalGenderMaleRadioButton.isSelected() ? "M" : "F");
			conditions.add("Sex=?");
		}
	}
	
	//todo: this does not work correctly for =
	private boolean getAnimalAgeForQuery(List<String> conditions, List<Object> values) {
		if (animalAgeCheckBox.isSelected()) {
			String filter;
			int age;
			
			try {
				age = Integer.parseInt(animalAgeTextField.getText());
			}
			catch (NumberFormatException e) {
				Popups.errorAlert("Enter a valid integer for the animal age");
				return false;
			}
			
			filter = AGE_FLIP_SIGN.get(QUERY_FILTERS_MAP.get(animalAgeFilterChoiceBox.getValue()));
			String units = animalAgeUnitsChoiceBox.getValue();
			values.add(age);
			conditions.add("DOB" + filter + "DATE_SUB(CURDATE(), INTERVAL ? " + (units.equals("Weeks") ? "WEEK)" : "YEAR)"));
		}
		return true;
	}
	
	private void getAnimalStageForQuery(List<String> conditions, List<Object> values) {
		if (animalStageCheckBox.isSelected()) {
			values.add(animalStageChoiceBox.getValue());
			conditions.add("Stage=?");
		}
	}
	
	private void getAnimalsLocationForQuery(List<String> conditions, List<Object> values) {
		if (animalLocationCheckBox.isSelected()) {
			values.add(animalLocationChoiceBox.getValue().getKey());
			conditions.add("A_ID IN (SELECT PetLocA_ID FROM Pet_Locations WHERE PetL_ID=?)");
		}
	}
	
	private void getAnimalOutcomeForQuery(List<String> conditions, List<Object> values) {
		if (animalOutcomeCheckBox.isSelected()) {
			values.add(animalOutcomeChoiceBox.getValue());
			conditions.add("Outcome=?");
			
			if (animalOutcomeFromDatePicker.getValue() != null) {
				LocalDate fromDate;
				LocalDate toDate;
				
				fromDate = animalOutcomeFromDatePicker.getValue();
				values.add(fromDate);
				
				//when the second DatePicker is empty only use the first one for an exact date
				if (animalOutcomeToDatePicker.getValue() != null) {
					toDate = animalOutcomeToDatePicker.getValue();
					values.add(toDate);
					conditions.add("OutcomeDate>=? AND OutcomeDate<=?");
				}
				else {
					conditions.add("OutcomeDate=?");
				}
			}
		}
	}
	
	private void getAnimalFixedForQuery(List<String> conditions, List<Object> values) {
		if (!animalFixedNoneRadioButton.isSelected()) {
			values.add(animalFixedYesRadioButton.isSelected() ? "Y" : "N");
			conditions.add("FixedYN=?");
		}
	}
	
	private void getAnimalTypesForQuery(List<String> conditions, List<Object> values) {
		if (animalOtherCheckBox.isSelected() || animalCatCheckBox.isSelected() || animalDogCheckBox.isSelected()) {
			StringBuilder types = new StringBuilder();
			
			//Other animal
			if (animalOtherCheckBox.isSelected()) {
				types.append("A_ID IN (SELECT Other_ID FROM Other"); //show if animal in Other table
				
				//species
				if (animalOtherSpeciesCheckBox.isSelected()) {
					values.add(animalOtherSpeciesChoiceBox.getValue());
					types.append(" WHERE Species=?");
				}
				types.append(")");
			}
			
			//Cat
			if (animalCatCheckBox.isSelected()) {
				if (animalOtherCheckBox.isSelected()) {
					types.append(" OR ");
				}
				
				types.append("A_ID IN (SELECT Cat_ID FROM Cat"); //show if animal in Cat table
				
				//working cat
				if (animalCatWorkingCheckBox.isSelected()) {
					values.add("Y");
					types.append(" WHERE WorkCatYN=?");
				}
				types.append(")");
			}
			
			//dog
			if (animalDogCheckBox.isSelected()) {
				if (animalOtherCheckBox.isSelected() || animalCatCheckBox.isSelected()) {
					types.append(" OR ");
				}
				
				types.append("A_ID IN (SELECT Dog_ID FROM Dog)"); //show if animal in Dog table
			}
			
			conditions.add("(" + types.toString() + ")");
		}
	}
	//</editor-fold>
	
	//<editor-fold desc="Medical Tab Methods">
	private void generateMedicalReport() {
		List<String> queries = new ArrayList<>();
		List<List<Object>> allQueriesValues = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		String query;
		
		if (medicalEntryTextField.getText().isBlank()) {
			Popups.errorAlert("You must enter a Name or Medical Record Number");
			return;
		}
		
		String filter = medicalEntryFilterChoiceBox.getValue();
		String id = filter.equals(MEDICAL_OPTIONS[0])
				? getAnimalIDByName(medicalEntryTextField.getText())
				: medicalEntryTextField.getText();
		
		if (id.isBlank()) {
			Popups.errorAlert("No Animal found for that Name");
			return;
		}
		
		values.add(id);
		
		//run all the queries for the associated tables
		
		//Medical_Records
		query = getMedicalRecordsQuery(filter);
		queries.add(query);
		allQueriesValues.add(values);
		
		//Tests
		query = getTestsQuery(filter);
		queries.add(query);
		allQueriesValues.add(values);
		
		//Conditions
		query = getConditionsQuery(filter);
		queries.add(query);
		allQueriesValues.add(values);
		
		//Medication_Given
		query = getMedicationsGivenQuery(filter);
		queries.add(query);
		allQueriesValues.add(values);
		
		//Vaccines_Given
		query = getVaccinesGivenQuery(filter);
		queries.add(query);
		allQueriesValues.add(values);
		
		//Notes
		query = getNotesQuery(filter);
		queries.add(query);
		allQueriesValues.add(values);
		
		//run sql query and handle the results
		handleQueries(queries, allQueriesValues);
	}
	
	//medical tab get animal ID
	private String getAnimalIDByName(String name) {
		//database query for valid names
		List<Object> values = new ArrayList<>();
		values.add(name.replace("'", "’"));
		
		final Future<ResultSet> submit = executor.submit(
				new SqlQueryTask("SELECT A_ID, Name FROM Animal WHERE Name=? ORDER BY A_ID", values));
		
		List<Pair<String, String>> choices = new ArrayList<>();
		
		try (ResultSet results = submit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			while (results.next()) {
				choices.add(new Pair<>(results.getString(1), results.getString(2)));
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
		
		//call the choice dialog to have user select one a set of options
		if (choices.size() > 1) {
			Pair<String, String> animal = Popups.displayChoiceDialog(choices, "Animal");
			
			return animal != null ? animal.getKey() : "";
		}
		else if (choices.size() == 1) {         //if only one animal returned from query then return
			//return the id
			return choices.get(0).getKey();
		}
		else {
			return "";
		}
	}
	
	private String getMedicalRecordsQuery(String filter) {
		String query;
		if (filter.equals(MEDICAL_OPTIONS[0])) {
			query = "SELECT * FROM Medical_Records WHERE RecA_ID=?";
		}
		else {
			query = "SELECT * FROM Medical_Records WHERE Record_ID=?";
		}
		return query;
	}
	
	private String getTestsQuery(String filter) {
		String query;
		if (filter.equals(MEDICAL_OPTIONS[0])) {
			query = "SELECT * FROM Tests WHERE TestA_ID=?";
		}
		else {
			query = "SELECT * FROM Tests WHERE TestR_ID=?";
		}
		return query;
	}
	
	private String getConditionsQuery(String filter) {
		String query;
		if (filter.equals(MEDICAL_OPTIONS[0])) {
			query = "SELECT * FROM Conditions WHERE CondA_ID=?";
		}
		else {
			query = "SELECT * FROM Conditions WHERE CondR_ID=?";
		}
		return query;
	}
	
	private String getMedicationsGivenQuery(String filter) {
		String query;
		if (filter.equals(MEDICAL_OPTIONS[0])) {
			query = "SELECT * FROM Medication_Given, Medications WHERE Medication_ID=M_ID AND MedA_ID=?";
		}
		else {
			query = "SELECT * FROM Medication_Given, Medications WHERE Medication_ID=M_ID AND MedR_ID=?";
		}
		return query;
	}
	
	private String getVaccinesGivenQuery(String filter) {
		String query;
		if (filter.equals(MEDICAL_OPTIONS[0])) {
			query = "SELECT * FROM Vaccines_Given, Vaccines WHERE Vaccine_ID=V_ID AND VacA_ID=?";
		}
		else {
			query = "SELECT * FROM Vaccines_Given, Vaccines WHERE Vaccine_ID=V_ID AND VacR_ID=?";
		}
		return query;
	}
	
	private String getNotesQuery(String filter) {
		String query;
		if (filter.equals(MEDICAL_OPTIONS[0])) {
			query = "SELECT * FROM Notes WHERE NoteA_ID=?";
		}
		else {
			query = "SELECT * FROM Notes WHERE NoteR_ID=?";
		}
		return query;
	}
	//</editor-fold>
	
	private String buildQuery(StringBuilder query, List<String> joins, List<String> conditions) {
		
		if (!joins.isEmpty() || !conditions.isEmpty()) {
			query.append(" WHERE ");
		}
		
		//add the joins to the query if there are any
		if (!joins.isEmpty()) {
			StringBuilder joinBuilder = new StringBuilder("( ");
			joinBuilder.append(joins.get(0));
			for (int i = 1; i < joins.size(); i++) {
				joinBuilder.append(" OR ").append(joins.get(i));
			}
			joinBuilder.append(" ) ");
			
			query.append(joinBuilder);
		}
		
		//add the conditions to the query if there are any
		if (!conditions.isEmpty()) {
			if (!joins.isEmpty()) {
				query.append(" AND ");
			}
			query.append(conditions.get(0));
			for (int i = 1; i < conditions.size(); i++) {
				query.append(" AND ").append(conditions.get(i));
			}
		}
		
		return query.toString();
	}
	
	//this is for one query
	//makes handleQueries easier to use for a single query
	private void handleQueries(String sql, List<Object> values) {
		List<String> tempSql = new ArrayList<>();
		tempSql.add(sql);
		
		List<List<Object>> tempValues = new ArrayList<>();
		tempValues.add(values);
		
		handleQueries(tempSql, tempValues);
	}
	
	private void handleQueries(List<String> sql, List<List<Object>> values) {
		List<Future<ResultSet>> queries = new ArrayList<>();
		
		int size = sql.size();
		for (int i = 0; i < size; i++) {
			queries.add(executor.submit(new SqlQueryTask(sql.get(i), values.get(i))));
		}
		
		List<String> tableNames = new ArrayList<>();
		List<TableView<ObservableList<Object>>> tables = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			List<String> headers = new ArrayList<>();
			ObservableList<ObservableList<Object>> data = FXCollections.observableArrayList();
			
			//get the results of the query
			try (ResultSet results = queries.get(i).get();
			     Statement stmt = results.getStatement();
			     Connection conn = stmt.getConnection()
			) {
				ResultSetMetaData metaData = results.getMetaData();
				//todo: this may lead to bugs depending on whether the first column's tableName is the one I want to retrieve
				tableNames.add(metaData.getTableName(1));
				int columns = metaData.getColumnCount();
				for (int j = 1; j <= columns; j++) {
					headers.add(metaData.getColumnName(j));
				}
				while (results.next()) {
					//get the data for each row and put the list of values in the two dimensional List 'data'
					ObservableList<Object> rowData = FXCollections.observableArrayList();
					for (int j = 1; j <= columns; j++) {
						rowData.add(results.getObject(j));
					}
					data.add(rowData);
				}
			}
			catch (SQLException | ExecutionException e) {
				e.printStackTrace();
				Popups.errorAlert(e.getMessage());
				return;
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				Popups.errorAlert(e.getMessage());
				Thread.currentThread().interrupt();
				return;
			}
			
			tables.add(Popups.buildDataTable(headers, data));
		}
		Popups.displayDataTables(tableNames, tables);     //display the results
	}
}
