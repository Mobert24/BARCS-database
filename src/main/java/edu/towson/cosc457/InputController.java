package edu.towson.cosc457;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.beans.value.*;
import javafx.collections.FXCollections;
import javafx.util.Pair;

import edu.towson.cosc457.common.*;
import edu.towson.cosc457.connection.*;
import java.sql.Connection;
import java.sql.Statement;
import org.javatuples.Triplet;




public class InputController {
	
	//<editor-fold desc="All FXML Controls">
	@FXML private BorderPane inputBorderPane;
	
	@FXML private Button primaryButton; //switches the view from input to output
	
	//<editor-fold desc="Person Tab Controls">
	@FXML private Tab personTab;
	
	@FXML private AnchorPane personAnchorPane;
	
	//<editor-fold desc="New Person Tab Controls">
	@FXML private Tab newPersonTab;
	
	@FXML private AnchorPane newPersonAnchorPane;
	
	@FXML private CheckBox newPersonStaffCheckBox;
	@FXML private CheckBox newPersonVolunteerCheckBox;
	@FXML private CheckBox newPersonAdopterCheckBox;
	@FXML private CheckBox newPersonFosterCheckBox;
	@FXML private CheckBox newPersonVetCheckBox;
	
	@FXML private ChoiceBox<String> newPersonVolunteerChoiceBox;
	
	@FXML private CheckBox newPersonStaffGeneralSubBox;
	@FXML private CheckBox newPersonStaffAdministratorSubBox;
	@FXML private CheckBox newPersonStaffCoordinatorSubBox;
	
	@FXML private ComboBox<String> newPersonStateComboBox;
	
	@FXML private TextField newPersonWorkPhoneTextField;
	@FXML private TextField newPersonHomePhoneTextField;
	@FXML private TextField newPersonStaffSSNTextField;
	@FXML private TextField newPersonFosterPreferenceTextField;
	@FXML private TextField newPersonFnameTextField;
	@FXML private TextField newPersonMiddleInitialTextField;
	@FXML private TextField newPersonLnameTextField;
	@FXML private TextField newPersonAddressTextField;
	@FXML private TextField newPersonCityTextField;
	@FXML private TextField newPersonZIPTextField;
	@FXML private TextField newPersonEmailTextField;
	@FXML private TextField newPersonVolunteerLocationTextField;
	
	@FXML private DatePicker newPersonDOBPicker;
	//</editor-fold>
	
	//<editor-fold desc="Update Person Tab Controls">
	@FXML private Tab updatePersonTab;
	
	@FXML private TextField updatePersonFirstNameTextField;
	@FXML private TextField updatePersonLastNameTextField;
	@FXML private TextField updatePersonEmailTextField;
	@FXML private TextField updatePersonAddressTextField;
	@FXML private TextField updatePersonCityTextField;
	@FXML private TextField updatePersonZIPTextField;
	@FXML private TextField updatePersonPhoneTextField;
	@FXML private TextField updatePersonVolunteerLocationTextField;
	@FXML private TextField updatePersonFosterPreferencesTextField;
	
	@FXML private CheckBox updatePersonStaffCheckBox;
	@FXML private CheckBox updatePersonAdopterCheckBox;
	@FXML private CheckBox updatePersonFosterCheckBox;
	@FXML private CheckBox updatePersonVolunteerCheckBox;
	@FXML private CheckBox updatePersonVeterinarianCheckBox;
	@FXML private CheckBox updatePersonBannedYNCheckBox;
	
	
	@FXML private CheckBox updatePersonStaffGeneralSubBox;
	@FXML private CheckBox updatePersonStaffAdministratorSubBox;
	@FXML private CheckBox updatePersonStaffCoordinatorSubBox;
	
	@FXML private ChoiceBox<String> updatePersonVolunteerLevelChoiceBox;
	
	@FXML private ComboBox<String> updatePersonStateComboBox;
	
	@FXML private AnchorPane updatePersonAnchorPane;
	//</editor-fold>
	
	//</editor-fold>
	
	//<editor-fold desc="Animal Tab Controls">
	@FXML private Tab animalTab;
	
	@FXML private AnchorPane animalAnchorPane;
	
	//<editor-fold desc="Animal Intake Tab Controls">
	@FXML private Tab animalIntakeTab;
	
	@FXML private AnchorPane animalIntakeAnchorPane;
	
	@FXML private ToggleGroup animalIntakeSpeciesRadio;
	@FXML private RadioButton animalIntakeCatRadioButton;
	@FXML private RadioButton animalIntakeDogRadioButton;
	@FXML private RadioButton animalIntakeOtherRadioButton;
	
	@FXML private ToggleGroup animalIntakeSexRadio;
	@FXML private RadioButton animalIntakeMaleRadioButton;
	@FXML private RadioButton animalIntakeFemaleRadioButton;
	
	@FXML private DatePicker animalIntakeDatePicker;
	@FXML private DatePicker animalIntakeDOBPicker;
	
	@FXML private CheckBox animalIntakeCatWorkingCheckBox;
	@FXML private CheckBox animalIntakeCatDeclawedCheckBox;
	@FXML private CheckBox animalIntakeDogBiteCheckBox;
	@FXML private CheckBox animalIntakeFixedCheckBox;
	
	@FXML private TextField animalIntakeStatusTextField;
	@FXML private TextField animalIntakeReasonTextField;
	@FXML private TextField animalIntakeNameTextField;
	@FXML private TextField animalIntakeBreedTextField;
	@FXML private TextField animalIntakeHeightTextField;
	@FXML private TextField animalIntakeWeightTextField;
	@FXML private TextField animalIntakeStageTextField;
	@FXML private TextField animalIntakeOtherSpeciesTextField;
	
	@FXML private ChoiceBox<String> animalIntakeSizeChoiceBox;
	
	@FXML private TextField animalIntakeParentTextField;
	//</editor-fold>
	
	//<editor-fold desc="Update Animal Tab Controls">
	@FXML private Tab animalUpdateTab;
	
	@FXML private AnchorPane animalUpdateAnchorPane;
	
	@FXML private TextField animalUpdateNameTextField;
	@FXML private Button animalUpdateFindAnimalButton;
	
	@FXML private ToggleGroup animalUpdateTypeToggleGroup;
	@FXML private RadioButton animalUpdateOtherRadioButton;
	@FXML private RadioButton animalUpdateCatRadioButton;
	@FXML private RadioButton animalUpdateDogRadioButton;
	
	@FXML private CheckBox animalUpdateFixedCheckBox;
	@FXML private CheckBox animalUpdateCatDeclawedCheckBox;
	@FXML private CheckBox animalUpdateDogBiteHistoryCheckBox;
	@FXML private CheckBox animalUpdateCatWorkingCheckBox;
	
	@FXML private TextField animalUpdateStageTextField;
	@FXML private TextField animalUpdateHeightTextField;
	@FXML private TextField animalUpdateWeightTextField;
	@FXML private ChoiceBox<String> animalUpdateSizeChoiceBox;
	//</editor-fold>
	
	//<editor-fold desc="Foster Animal Tab Controls">
	@FXML private Tab animalFosterTab;
	
	@FXML private AnchorPane animalFosterAnchorPane;
	
	@FXML private TextField animalFosterPersonTextField;
	@FXML private TextField animalFosterAnimalTextField;
	
	@FXML private ToggleGroup animalFosterToggleGroup;
	@FXML private RadioButton animalFosterStartReasonRadioButton;
	@FXML private RadioButton animalFosterEndReasonRadioButton;
	
	@FXML private Label animalFosterStartReasonLabel;
	@FXML private TextField animalFosterStartReasonTextField;
	
	@FXML private Label animalFosterEndReasonLabel;
	@FXML private TextField animalFosterEndReasonTextField;
	//</editor-fold>
	
	//<editor-fold desc="Outcome Animal Tab Controls">
	@FXML private Tab animalOutcomeTab;
	
	@FXML private AnchorPane animalOutcomeAnchorPane;
	
	@FXML private TextField animalOutcomeAnimalNameTextField;
	@FXML private TextField animalOutcomeOTextField;
	@FXML private TextField animalOutcomeOReasonTextField;
	@FXML private DatePicker animalOutcomeODateDatePicker;
	//</editor-fold>
	
	
	//<editor-fold desc="Animal Hold Tab Controls">
	@FXML private Tab animalHoldTab;
	
	@FXML private TextField holdAnimalNameTextField;
	@FXML private TextField holdReasonTextField;
	@FXML private TextField holdEndReasonTextField;
	
	@FXML private DatePicker holdStartDateDatePicker;
	@FXML private DatePicker holdEndDateDatePicker;
	@FXML private DatePicker holdReviewDateDatePicker;
	//</editor-fold>
	
	//<editor-fold desc="Medical Record Tab Controls">
	@FXML private Tab medicalRecordTab;
	
	@FXML private AnchorPane medicalRecordAnchorPane;
	@FXML private VBox medicalRecordVBox;
	
	//<editor-fold desc="Basic Info Tab Controls">
	@FXML private Tab basicInfoTab;
	
	@FXML private AnchorPane basicInfoAnchorPane;
	
	@FXML private TextField basicInfoAnimalNameTextField;
	@FXML private TextField basicInfoTypeTextField;
	
	@FXML private TextArea basicInfoNotesTextArea;
	
	@FXML private DatePicker basicInfoExamDateDatePicker;
	@FXML private DatePicker basicInfoReviewDateDatePicker;
	
	
	//</editor-fold>
	
	//<editor-fold desc="Vaccines Tab Controls">
	@FXML private Tab vaccinesTab;
	
	@FXML private AnchorPane vaccinesAnchorPane;
	
	@FXML private TextField vaccinesNameTextField;
	@FXML private TextField vaccinesAnimalNameTextField;
	@FXML private TextField vaccinesFirstNameTextField;
	@FXML private TextField vaccinesMiddleInitialTextField;
	@FXML private TextField vaccinesLastNameTextField;
	
	@FXML private DatePicker vaccinesVaccDateDatePicker;
	
	@FXML private TextArea vaccinesDescriptionTextArea;
	//</editor-fold>
	
	//<editor-fold desc="Medications Tab Controls">
	@FXML private Tab medicationsTab;
	
	@FXML private AnchorPane medicationsAnchorPane;
	
	@FXML private TextField medicationsNameTextField;
	@FXML private TextField medicationsAnimalNameTextField;
	@FXML private TextField medicationsFirstNameTextField;
	@FXML private TextField medicationsMiddleInitialTextField;
	@FXML private TextField medicationsLastNameTextField;
	@FXML private TextField medicationsDosageTextField;
	
	@FXML private DatePicker medicationsGivenDateDatePicker;
	@FXML private DatePicker medicationsReviewDateDatePicker;
	//</editor-fold>
	
	//<editor-fold desc="Conditions Tab Controls">
	@FXML private Tab conditionsTab;
	
	@FXML private AnchorPane conditionsAnchorPane;
	
	@FXML private TextField conditionsAnimalNameTextField;
	@FXML private TextField conditionsConditionNameTextField;
	@FXML private TextField conditionsBodyPartTextField;
	
	@FXML private DatePicker conditionsNotedDateDatePicker;
	//</editor-fold>
	
	//<editor-fold desc="Tests Tab Controls">
	@FXML private Tab testsTab;
	
	@FXML private AnchorPane testsAnchorPane;
	
	@FXML private TextField testsAnimalNameTextField;
	@FXML private TextField testsTestNameTextField;
	@FXML private TextField testsResultTextField;
	
	@FXML private DatePicker testsTestDateDatePicker;
	@FXML private DatePicker testsResultDateDatePicker;
	@FXML private DatePicker testsRetestDateDatePicker;
	//</editor-fold>
	
	//</editor-fold>
	
	//<editor-fold desc="Location Tab Controls">
	@FXML private Tab locationTab;
	
	@FXML private AnchorPane locationAnchorPane;
	
	@FXML private TextField locationNameTextField;
	@FXML private TextField locationCapacityTextField;
	@FXML private TextField locationFirstNameTextField;
	@FXML private TextField locationMiddleInitialTextField;
	@FXML private TextField locationLastNameTextField;
	
	@FXML private ChoiceBox<String> locationTypeChoiceBox;
	//</editor-fold>
	
	//<editor-fold desc="Inventory Tab Controls">
	@FXML private Tab inventoryTab;
	
	@FXML private AnchorPane inventoryAnchorPane;
	
	@FXML private AnchorPane inventoryMedicationAnchorPane;
	
	@FXML private ToggleGroup inventoryTypeRadio;
	@FXML private RadioButton inventoryMedicationRadioButton;
	@FXML private RadioButton inventoryVaccineRadioButton;
	@FXML private TextField inventoryNameTextField;
	@FXML private TextField inventoryCountTextField;
	
	@FXML private Label inventoryMedicationLabel;
	
	@FXML private Label inventoryMedicationLengthLabel;
	@FXML private TextField inventoryMedicationLengthTextField;
	@FXML private Label inventoryMedicationAdminFreqLabel;
	@FXML private TextField inventoryMedicationAdminFreqTextField;
	@FXML private Label inventoryMedicationDosageLabel;
	@FXML private TextField inventoryMedicationDosageTextField;
	
	@FXML private Label inventoryVaccineLabel;
	
	@FXML private Label inventoryVaccineRevacFreqLabel;
	@FXML private TextField inventoryVaccineRevacFreqTextField;
	@FXML private Label inventoryVaccineExpectedByLabel;
	@FXML private TextField inventoryVaccineExpectedByTextField;
	//</editor-fold>
	
	@FXML private Button submitButton;
	//</editor-fold>
	//</editor-fold>
	
	//<editor-fold desc="Constants">
	//constants
	private static final String[] VOLUNTEER_LEVELS = {"1", "2", "3"};
	private static final String[] LOCATION_TYPES = {"Foster", "Business", "Shelter", "Hospital"};
	private static final String[] ANIMAL_SIZES = {"Small", "Medium", "Large"};
	private static final String[] US_STATES = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL",
						   "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA",
						   "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE",
						   "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK",
						   "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT",
						   "VA", "WA", "WV", "WI", "WY"};
	//</editor-fold>
	
	private final ExecutorService executor;
	
	//instance variable to hold the id for the animalUpdate/updatePerson
	private int animalUpdateId;
	private int updatePersonId;
	//instance variable for holding association selection state
	private final String[] updateAssociationSelected = {"0", "0", "0"};
	private final String[] associationNames = {"Administrator", "Coordinator", "General Staff"};
	
	
	
	public InputController() {
		executor = App.getExecutor();
	}
	
	@FXML
	private void initialize() {
		disableAllTabs();
		setPermissions(App.getUserAssociations());
		
		Utils.switchNodeVisibility(animalUpdateAnchorPane, "animalUpdateCat[a-zA-Z]+", "animalUpdateCatRadioButton");
		Utils.switchNodeVisibility(animalUpdateAnchorPane, "animalUpdateDog[a-zA-Z]+", "animalUpdateDogRadioButton");
		
		animalUpdateCatRadioButton.setDisable(true);
		animalUpdateDogRadioButton.setDisable(true);
		animalUpdateOtherRadioButton.setDisable(true);
		animalFosterEndReasonLabel.setDisable(true);
		animalFosterEndReasonTextField.setDisable(true);
		
		//load constants
		//person
		newPersonVolunteerChoiceBox.setItems(FXCollections.observableList(Arrays.asList(VOLUNTEER_LEVELS)));
		newPersonVolunteerChoiceBox.setValue(VOLUNTEER_LEVELS[0]);
		
		updatePersonVolunteerLevelChoiceBox.setItems(FXCollections.observableList(Arrays.asList(VOLUNTEER_LEVELS)));
		
		newPersonStateComboBox.setItems(FXCollections.observableList(Arrays.asList(US_STATES)));
		
		updatePersonStateComboBox.setItems(FXCollections.observableList(Arrays.asList(US_STATES)));
		
		//location
		locationTypeChoiceBox.setItems(FXCollections.observableList(Arrays.asList(LOCATION_TYPES)));
		locationTypeChoiceBox.setValue(LOCATION_TYPES[0]);
		
		//animal
		animalIntakeSizeChoiceBox.setItems(FXCollections.observableList(Arrays.asList(ANIMAL_SIZES)));
		animalIntakeSizeChoiceBox.setValue(ANIMAL_SIZES[0]);
		animalIntakeDatePicker.setValue(LocalDate.now());
		animalUpdateSizeChoiceBox.setItems(FXCollections.observableList(Arrays.asList(ANIMAL_SIZES)));
		
		
		
		
		//<editor-fold desc="Listeners">
		//add listeners
		//<editor-fold desc="Inventory Listener">
		inventoryTypeRadio.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle o, Toggle n) -> {
			//switch node visibility of old and new radios
			RadioButton oldRadio = (RadioButton)o;
			RadioButton newRadio = (RadioButton)n;
			
			
			Pane oldParent = (Pane)oldRadio.getParent();
			Pane newParent = (Pane)newRadio.getParent();
			
			String oldOriginalId = oldRadio.getId();
			String newOriginalId = newRadio.getId();
			
			String oldId = oldOriginalId.replace("RadioButton", "[a-zA-Z]+");
			String newId = newOriginalId.replace("RadioButton", "[a-zA-Z]+");
			
			Utils.switchNodeVisibility(oldParent, oldId, oldOriginalId);
			Utils.switchNodeVisibility(newParent, newId, newOriginalId);
		});
		//</editor-fold>
		
		//<editor-fold desc="Intake Species Listener">
		animalIntakeSpeciesRadio.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle o, Toggle n) -> {
			//switch node visibility of old and new radios
			RadioButton oldRadio = (RadioButton)o;
			RadioButton newRadio = (RadioButton)n;
			
			
			Pane oldParent = (Pane)oldRadio.getParent();
			Pane newParent = (Pane)newRadio.getParent();
			
			String oldOriginalId = oldRadio.getId();
			String newOriginalId = newRadio.getId();
			
			String oldId = oldOriginalId.replace("RadioButton", "[a-zA-Z]+");
			String newId = newOriginalId.replace("RadioButton", "[a-zA-Z]+");
			
			Utils.switchNodeVisibility(oldParent, oldId, oldOriginalId);
			Utils.switchNodeVisibility(newParent, newId, newOriginalId);
		});
		//</editor-fold>

		//</editor-fold>
		
		//<editor-fold desc="Text Formatters">
		
		//Set text formatters
		//int formatters
		animalIntakeHeightTextField.setTextFormatter(Utils.intFormatter());
		animalIntakeWeightTextField.setTextFormatter(Utils.intFormatter());
		newPersonWorkPhoneTextField.setTextFormatter(Utils.intFormatter());
		newPersonHomePhoneTextField.setTextFormatter(Utils.intFormatter());
		locationCapacityTextField.setTextFormatter(Utils.intFormatter());
		inventoryCountTextField.setTextFormatter(Utils.intFormatter());
		//whitespace formatters
		newPersonFnameTextField.setTextFormatter(Utils.whiteSpaceFormatter());
		newPersonLnameTextField.setTextFormatter(Utils.whiteSpaceFormatter());
		locationFirstNameTextField.setTextFormatter(Utils.whiteSpaceFormatter());
		locationLastNameTextField.setTextFormatter(Utils.whiteSpaceFormatter());
		vaccinesFirstNameTextField.setTextFormatter(Utils.whiteSpaceFormatter());
		vaccinesLastNameTextField.setTextFormatter(Utils.whiteSpaceFormatter());
		medicationsFirstNameTextField.setTextFormatter(Utils.whiteSpaceFormatter());
		medicationsLastNameTextField.setTextFormatter(Utils.whiteSpaceFormatter());
		//middle initial formatter
		newPersonMiddleInitialTextField.setTextFormatter(Utils.middleInitialFormatter());
		locationMiddleInitialTextField.setTextFormatter(Utils.middleInitialFormatter());
		vaccinesMiddleInitialTextField.setTextFormatter(Utils.middleInitialFormatter());
		medicationsMiddleInitialTextField.setTextFormatter(Utils.middleInitialFormatter());
		//phone formatter
		newPersonWorkPhoneTextField.setTextFormatter(Utils.phoneFormatter());
		newPersonHomePhoneTextField.setTextFormatter(Utils.phoneFormatter());
		updatePersonPhoneTextField.setTextFormatter(Utils.phoneFormatter());
		//ssn formatter
		newPersonStaffSSNTextField.setTextFormatter(Utils.SSNFormatter());
		//zip formatter
		newPersonZIPTextField.setTextFormatter(Utils.ZIPFormatter());
		updatePersonZIPTextField.setTextFormatter(Utils.ZIPFormatter());
		//</editor-fold>
	}
	
	private void setPermissions(List<String> userAssociations) {
		for (String association : userAssociations) {
			if (association.equalsIgnoreCase(associationNames[0])) {
				personTab.setDisable(false);
				locationTab.setDisable(false);
			}
			else if (association.equalsIgnoreCase(associationNames[1])) {
				personTab.setDisable(false);
				locationTab.setDisable(false);
				animalFosterTab.setDisable(false);
			}
			else if (association.equalsIgnoreCase(associationNames[2])) {
			
			}
			else if (association.equalsIgnoreCase("veterinarian")) {
				medicalRecordTab.setDisable(false);
				inventoryTab.setDisable(false);
				
			}
		}
	}
	
	private void disableAllTabs() {
		//all the outer tabs except animal
		personTab.setDisable(true);
		medicalRecordTab.setDisable(true);
		locationTab.setDisable(true);
		inventoryTab.setDisable(true);
		
		//animal tabs
		animalFosterTab.setDisable(true);
	}
	
	@FXML
	private void switchToOutput() throws IOException {
		App.setRoot("output");
	}
	
	@FXML
	private void logout() throws IOException {
		App.setRoot("login");
	}
	
	//Find person with names matching values entered in name fields.
	//Toggle disabled fields and set values
	@FXML
	private void findPersonButtonPressed(ActionEvent event) {
		String firstName = updatePersonFirstNameTextField.getText();
		String lastName = updatePersonLastNameTextField.getText();
		
		if (firstName.equals("") && lastName.equals("")) {
			Popups.errorAlert("You must enter at least the first or last name for a Person");
			return;
		}
		
		updatePersonId = getPersonID(firstName, "", lastName);
		List<Object> values = new ArrayList<>();
		values.add(updatePersonId);
		
		//database query  for valid names
		String query = "SELECT P_ID, FName, MI, LName, Address, Email, BannedYN,"
				+ "StaffFlag, VetFlag, FosterFlag, Preference, AdopterFlag, VolFlag, VolLevel FROM Person WHERE P_ID = ?";
		
		final Future<ResultSet> submit = executor.submit(new SqlQueryTask(query, values));
		
		try (ResultSet results = submit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			while (results.next()) {
				String address = results.getString(5);
				String[] splitArray = address.split(",", 4);
				
				updatePersonAddressTextField.setText(splitArray[0].trim());
				updatePersonAddressTextField.setDisable(false);
				
				updatePersonCityTextField.setText(splitArray[1].trim());
				updatePersonCityTextField.setDisable(false);
				
				if (splitArray.length >= 3) {
					updatePersonStateComboBox.setValue(splitArray[2].trim());
				} else {
					updatePersonStateComboBox.setValue("");
				}
				updatePersonStateComboBox.setDisable(false);
				
				if (splitArray.length == 4) {
					updatePersonZIPTextField.setText(splitArray[3].trim());
				}
				else {
					updatePersonZIPTextField.setText("");
				}
				updatePersonZIPTextField.setDisable(false);
				
				updatePersonEmailTextField.setText(results.getString(6));
				updatePersonEmailTextField.setDisable(false);
				
				updatePersonBannedYNCheckBox.setDisable(false);
				if (results.getString(7).equals("Y")) {
					updatePersonBannedYNCheckBox.setSelected(true);
				}
				
				updatePersonStaffCheckBox.setDisable(false);
				if (results.getString(8).equals("1")) {
					updatePersonStaffCheckBox.fire();
				}
				
				updatePersonVeterinarianCheckBox.setDisable(false);
				if (results.getString(9).equals("1")) {
					updatePersonVeterinarianCheckBox.fire();
				}
				updatePersonFosterCheckBox.setDisable(false);
				if (results.getString(10).equals("1")) {
					updatePersonFosterCheckBox.fire();
				}
				if (updatePersonFosterCheckBox.isSelected()) {
					updatePersonFosterPreferencesTextField.setText(results.getString(11));
				}
				updatePersonAdopterCheckBox.setDisable(false);
				if (results.getString(12).equals("1")) {
					updatePersonAdopterCheckBox.fire();
				}
				updatePersonVolunteerCheckBox.setDisable(false);
				if (results.getString(13).equals("1")) {
					updatePersonVolunteerCheckBox.fire();
				}
				if (updatePersonVolunteerCheckBox.isSelected()) {
					updatePersonVolunteerLevelChoiceBox.setValue(results.getString(13));
				}
				updatePersonPhoneTextField.setDisable(false);
				
				//set association check box values = result of association query
				if (updatePersonStaffCheckBox.isSelected()) {
					String[] states = getAssociations(updatePersonId);
					if (states[0].equals("1")) {
						updatePersonStaffAdministratorSubBox.setSelected(true);
						updateAssociationSelected[0] = "1";
					}
					else {
						updateAssociationSelected[0] = "0";
					}
					if (states[1].equals("1")) {
						updatePersonStaffCoordinatorSubBox.setSelected(true);
						updateAssociationSelected[1] = "1";
					}
					else {
						updateAssociationSelected[1] = "0";
					}
					if (states[2].equals("1")) {
						updatePersonStaffGeneralSubBox.setSelected(true);
						updateAssociationSelected[2] = "1";
					}
					else {
						updateAssociationSelected[2] = "0";
					}
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
	}
		
	//a user can have many associations, represent through checkboxes, and return an array of strings representing state
	@FXML
	private String[] getAssociations(int personID) {
	    //first value = admin
	    //second value = coord
	    //third value = general staff
	    String[] states = {"0", "0", "0"}; //Person can only have three associations, all initialized to 0
	    List<Object> values = new ArrayList<>();
	    values.add(personID);
	    
	    final Future<ResultSet> submit = executor.submit(
			    new SqlQueryTask("SELECT Association FROM Associations WHERE AssocP_ID = ?", values));
	    
	    List<String> associationsResult = new ArrayList<>();
	    
	    try (ResultSet result = submit.get();
		 Statement stmt = result.getStatement();
		 Connection conn = stmt.getConnection();
		) {
		while (result.next()) {
		    associationsResult.add(result.getString(1));
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
	    if (!associationsResult.isEmpty()) {
		    for (String s : associationsResult) {
			    if (s.equalsIgnoreCase(associationNames[0])) {
				    states[0] = "1";
			    }
			    else if (s.equalsIgnoreCase(associationNames[1])) {
				    states[1] = "1";
			    }
			    else if (s.equalsIgnoreCase(associationNames[2])) {
				    states[2] = "1";
			    }
		    }
	    }
		else {
		    Popups.errorAlert("No associations found for staff member, please set an association.");
	    }
		return states;
	}
	//get locationID given a volunteer's P_ID
	@FXML
	private int getVolunteerLocationID(int volunteerID) {
	    //list, query, get value
	    List<Object> values = new ArrayList<>();
	    values.add(volunteerID);
	    
	    final Future<ResultSet> submit = executor.submit(
			    new SqlQueryTask("SELECT VolLoc_ID FROM Vol_At WHERE Vol_ID = ?", values));
	    
	    List<Integer> locationID = new ArrayList<>();
	    
	    try (ResultSet results = submit.get();
		 Statement stmt = results.getStatement();
		 Connection conn = stmt.getConnection();
		) {
		while(results.next()) {
		    locationID.add(results.getInt(1));
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
	    if (locationID.size() == 1){
		return locationID.get(0); //will either return 0 or the id of the location
	    }
	    else {
		return 0;
	    }
	}
	
	//Get a location's name using its id
	@FXML
	private String getLocationName(int locationID) {
	    List<Object> values = new ArrayList<>();
	    values.add(locationID);
	    
	    final Future<ResultSet> submit = executor.submit(
			    new SqlQueryTask("SELECT Name FROM Location WHERE L_ID = ?", values));
	    
	    List<String> nameResults = new ArrayList<>();
	    
	    try (ResultSet results = submit.get();
		 Statement stmt = results.getStatement();
		 Connection conn = stmt.getConnection();
		) {
		while (results.next()) {
		    nameResults.add(results.getString(1));
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
	    if (nameResults.size() == 1) {
		return nameResults.get(0);
	    }
	    else {
		return "";
	    }
	}
	
	
	//only use on checkboxes
	@FXML
	private void selectCheckBox(ActionEvent event) {
		CheckBox checkBox = (CheckBox) event.getSource();
		Pane parent = (Pane) checkBox.getParent();
		
		String originalId = checkBox.getId();
		String id = originalId.replace("CheckBox", "[a-zA-Z]+");
		
		Utils.switchNodeVisibility(parent, id, originalId);
	}
	
	@FXML
	private void animalUpdateRadioButtonSelected(ActionEvent event) {
		RadioButton radioButton = (RadioButton) event.getSource();
		Pane parent = (Pane) radioButton.getParent();
		
		String originalId = radioButton.getId();
		String id = originalId.replace("RadioButton", "[a-zA-Z]+");
		
		Utils.switchNodeVisibility(parent, id, originalId);
	}
	
	@FXML
	private void animalFosterRadioButtonClicked(ActionEvent event) {
		if (animalFosterStartReasonRadioButton.isSelected()) {
			animalFosterStartReasonTextField.setDisable(false);
			animalFosterStartReasonLabel.setDisable(false);
			animalFosterEndReasonTextField.setDisable(true);
			animalFosterEndReasonLabel.setDisable(true);
		}
		else {
			animalFosterStartReasonTextField.setDisable(true);
			animalFosterStartReasonLabel.setDisable(true);
			animalFosterEndReasonTextField.setDisable(false);
			animalFosterEndReasonLabel.setDisable(false);
		}
	}
	
	@FXML
	private void getAnimalInfo(ActionEvent event) {
		String animal = animalUpdateNameTextField.getText();
		animalUpdateId = 0;
		
		try {
			animalUpdateId = Integer.parseInt(animal);
		}
		catch (NumberFormatException e) {
			animalUpdateId = getAnimalID(animal);
		}
		
		if (animalUpdateId == 0) {
			return;
		}
		
		//do query to get animal info
		displayAnimalUpdateInfo();
	}
	
	//Checks active tab, and calls submit function associated with tab
	@FXML
	private void submitButtonPressed(ActionEvent event) {
		if (personTab.isSelected()) {
			if (newPersonTab.isSelected()) {
				newPersonSubmit();
			}
			else if (updatePersonTab.isSelected()) {
				updatePersonSubmit();
			}
		}
		else if (locationTab.isSelected()) {
			locationSubmit();
		}
		else if (inventoryTab.isSelected()) {
			inventorySubmit();
		}
		else if (animalTab.isSelected()) {
			if (animalIntakeTab.isSelected()) {
				animalIntakeSubmit();
			}
			else if (animalUpdateTab.isSelected()) {
				animalUpdateSubmit();
			}
			else if (animalFosterTab.isSelected()) {
				animalFosterSubmit();
			}
			else if (animalOutcomeTab.isSelected()) {
				animalOutcomeSubmit(event);
			}
			else if (animalHoldTab.isSelected()) {
				holdSubmit(event);
			}
		}
		else if (medicalRecordTab.isSelected()) {
			if (basicInfoTab.isSelected()) {
				basicInfoSubmit();
			}
			else if (vaccinesTab.isSelected()) {
				vaccinesSubmit();
			}
			else if (medicationsTab.isSelected()) {
				medicationsSubmit();
			}
			else if (conditionsTab.isSelected()) {
				conditionsSubmit();
			}
			else if (testsTab.isSelected()) {
				testsSubmit();
			}
		}
	}
	
	private boolean isTextFieldsValid(TextField[] textFields) {
		String[] textFieldValues = new String[textFields.length];
		
		for (int i = 0; i < textFields.length; i++) {
			textFieldValues[i] = textFields[i].getText();
			if (textFieldValues[i].isBlank() && !textFields[i].isDisabled()) {
				//show alert with error message, return
				Popups.errorAlert("Please fill in all fields.");
				return false;
			}
		}
		return true;
	}
	
	private void displayAnimalUpdateInfo() {
		List<Object> animalID = new ArrayList<>();
		animalID.add(animalUpdateId);
		
		Future<ResultSet> infoSubmit = executor.submit(
				new SqlQueryTask("SELECT * FROM Animal WHERE A_ID=?", animalID));
		
		try (ResultSet results = infoSubmit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			while (results.next()) {
				String fixedYN = results.getString("FixedYN");
				if (fixedYN.equals("Y")) {
					animalUpdateFixedCheckBox.setSelected(true);
				}
				
				String stage = results.getString("Stage");
				if (stage != null) {
					animalUpdateStageTextField.setText(stage);
				}
				
				String height = results.getString("Height");
				animalUpdateHeightTextField.setText(height);
				
				String weight = results.getString("Weight");
				animalUpdateWeightTextField.setText(weight);
				
				String infoSize = results.getString("Size");
				for (String size : ANIMAL_SIZES) {
					if (infoSize.equalsIgnoreCase(size)) {
						animalUpdateSizeChoiceBox.setValue(ANIMAL_SIZES[Arrays.asList(ANIMAL_SIZES).indexOf(size)]);
						break;
					}
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
		
		animalUpdateCatRadioButton.setDisable(false);
		animalUpdateDogRadioButton.setDisable(false);
		animalUpdateOtherRadioButton.setDisable(false);
		
		if (displayCatUpdateInfo()) {
			animalUpdateCatRadioButton.fire();
		}
		else if (displayDogUpdateInfo()) {
			animalUpdateDogRadioButton.fire();
		}
		else {
			animalUpdateOtherRadioButton.fire();
		}
		
		animalUpdateCatRadioButton.setDisable(true);
		animalUpdateDogRadioButton.setDisable(true);
		animalUpdateOtherRadioButton.setDisable(true);
	}
	
	private boolean displayCatUpdateInfo() {
		boolean isCat = false;
		List<Object> catID = new ArrayList<>();
		catID.add(animalUpdateId);
		
		Future<ResultSet> infoSubmit = executor.submit(
				new SqlQueryTask("SELECT * FROM Cat WHERE Cat_ID=?", catID));
		
		try (ResultSet results = infoSubmit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			if (results.first()) {
				String declawed = results.getString("DeclawedYN");
				if (declawed.equals("Y")) {
					animalUpdateCatDeclawedCheckBox.setSelected(true);
				}
				
				String workCat = results.getString("WorkCatYN");
				if (workCat.equals("Y")) {
					animalUpdateCatWorkingCheckBox.setSelected(true);
				}
				isCat = true;
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
		
		return isCat;
	}
	
	private boolean displayDogUpdateInfo() {
		boolean isDog = false;
		List<Object> dogID = new ArrayList<>();
		dogID.add(animalUpdateId);
		
		Future<ResultSet> infoSubmit = executor.submit(
				new SqlQueryTask("SELECT * FROM Dog WHERE Dog_ID=?", dogID));
		
		try (ResultSet results = infoSubmit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			if (results.first()) {
				String biteHistory = results.getString("BiteHistoryYN");
				if (biteHistory.equals("Y")) {
					animalUpdateDogBiteHistoryCheckBox.setSelected(true);
				}
				isDog = true;
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
		
		return isDog;
	}
	
	//Formats 10-digit phone number into form: (###) ###-####
	//assuming american numbers only
	public String buildPhone(String phoneNumber) {
	    
	    StringBuilder phoneString = new StringBuilder();
	    //0123456789
	    phoneString.append(phoneNumber);
	    phoneString.insert(0, '(');
	    phoneString.insert(4, ')');
	    phoneString.insert(5, " ");
	    phoneString.insert(9, "-");
	    
	    return phoneString.toString();
	}
	
	//<editor-fold desc="ID Generators">
	//generates a random id for animals/people within a range of 10000000-99999999
	//queries db to check that generated ids do not already exist in db
	private int generateAnimalID() {
		int id;
		int max = 99999999;
		int min = 1;

		Random r = new Random();

		while (true) {

		    id = r.nextInt((max - min) + 1) + min;

		    List<Object> idValue = new ArrayList<>();
		    idValue.add(id);
		    //query db to check. if id already exists, roll another one
		    final Future<ResultSet> idCheck = executor.submit(
				    new SqlQueryTask("SELECT A_ID FROM Animal WHERE A_ID = ?", idValue));

		    List<Integer>  existingID = new ArrayList<>();

		    try (ResultSet idResults = idCheck.get();
			     Statement stmt = idResults.getStatement();
			     Connection conn = stmt.getConnection()) {
				while (idResults.next()) {
					existingID.add(idResults.getInt(1));
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
		    if (existingID.isEmpty()) {
		    	break;
		    }
		}
		return id;
	}
	
	private int generatePersonID() {
		int id;
		int max = 99999999;
		int min = 1;

		Random r = new Random();

		while(true) {

		    id = r.nextInt((max - min) + 1) + min;

		    List<Object> idValue = new ArrayList<>();
		    idValue.add(id);
		    //query db to check. if id already exists, roll another one
		    final Future<ResultSet> idCheck = executor.submit(
				    new SqlQueryTask("SELECT P_ID FROM Person WHERE P_ID = ?", idValue));

		    List<Integer>  existingID = new ArrayList<>();

		    try (ResultSet idResults = idCheck.get();
			     Statement stmt = idResults.getStatement();
			     Connection conn = stmt.getConnection()) {
				while (idResults.next()) {
					existingID.add(idResults.getInt(1));
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
		    if (existingID.isEmpty()) {
		    	break;
		    }
		}
		return id;
	}
	
	private int generateRecordID() {
	    int id;
		int max = 999999999;
		int min = 1;

		Random r = new Random();

		while (true) {

		    id = r.nextInt((max - min) + 1) + min;

		    List<Object> idValue = new ArrayList<>();
		    idValue.add(id);
		    //query db to check. if id already exists, roll another one
		    final Future<ResultSet> idCheck = executor.submit(
				    new SqlQueryTask("SELECT Record_ID FROM Medical_Records WHERE Record_ID = ?", idValue));

		    List<Integer>  existingID = new ArrayList<>();

		    try (ResultSet idResults = idCheck.get();
			     Statement stmt = idResults.getStatement();
			     Connection conn = stmt.getConnection()) {
				while (idResults.next()) {
					existingID.add(idResults.getInt(1));
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
		    if (existingID.isEmpty()) {
		    	break;
		    }
		}
		return id;
	}
	
	private int generateInventoryID() {
	    int id;
		int max = 99999999;
		int min = 1;

		Random r = new Random();

		while (true) {

		    id = r.nextInt((max - min) + 1) + min;

		    List<Object> idValue = new ArrayList<>();
		    idValue.add(id);
		    //query db to check. if id already exists, roll another one
		    final Future<ResultSet> idCheck = executor.submit(
				    new SqlQueryTask("SELECT Inv_ID FROM Medical_Inventory WHERE Inv_ID = ?", idValue));

		    List<Integer>  existingID = new ArrayList<>();

		    try (ResultSet idResults = idCheck.get();
			     Statement stmt = idResults.getStatement();
			     Connection conn = stmt.getConnection()) {
				while (idResults.next()) {
					existingID.add(idResults.getInt(1));
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
		    if (existingID.isEmpty()) {
		    	break;
		    }
		}
		
		return id;
	}
	
	private int generateLocationID() {
	    int id;
		int max = 99999999;
		int min = 1;

		Random r = new Random();

		while (true) {

		    id = r.nextInt((max - min) + 1) + min;

		    List<Object> idValue = new ArrayList<>();
		    idValue.add(id);
		    //query db to check. if id already exists, roll another one
		    final Future<ResultSet> idCheck = executor.submit(
				    new SqlQueryTask("SELECT L_ID FROM Location WHERE L_ID = ?", idValue));

		    List<Integer>  existingID = new ArrayList<>();

		    try (ResultSet idResults = idCheck.get();
			     Statement stmt = idResults.getStatement();
			     Connection conn = stmt.getConnection()) {
				while (idResults.next()) {
					existingID.add(idResults.getInt(1));
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
		    if (existingID.isEmpty()) {
		    	break;
		    }
		}
		
		return id;
	}
	//</editor-fold>
	
	//<editor-fold desc="ID Getters">
	//queries DB for A_ID when given animal name. In case of multiple same names, display choice dialog
	//a return of 0 indicates failure to find animal in DB
	private int getAnimalID(String name) {
		int animalID = 0;
		name = name.replace("'", "’");
		
		List<Object> nameValues = new ArrayList<>();
		nameValues.add(name);
		
		final Future<ResultSet> nameSubmit = executor.submit(
				new SqlQueryTask("SELECT A_ID FROM Animal WHERE Name = ? ORDER BY A_ID", nameValues));
		
		List<Integer> idChoices = new ArrayList<>();
		
		try (ResultSet nameResults = nameSubmit.get();
		     Statement stmt = nameResults.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			while (nameResults.next()) {
				idChoices.add(nameResults.getInt(1));
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
		
		//display choice dialog for case where there are multiple same names
		if (idChoices.size() > 1) {
		    animalID = Popups.displayChoiceDialog(idChoices, "Animal ID");
		}
		else if (idChoices.size() == 1) {
		    animalID = idChoices.get(0);
		}
		else {
		    Popups.errorAlert("Animal not found in database");
		}
		return animalID;
	}
	
	//queries DB for P_ID given first name, middle initial, and last name
	//display choice dialog if multiple same full names exist
	private int getPersonID(String fname, String mi, String lname) {
	    int personID = 0;
	    fname = fname.replace("'", "’");
	    lname = lname.replace("'", "’");
	    
	    StringBuilder query = new StringBuilder("SELECT P_ID, FName, MI, LName FROM Person WHERE ");
	    List<String> queryParts = new ArrayList<>();
	    List<Object> values = new ArrayList<>();
		
	    if (!fname.equals("")) {
		    values.add(fname);
		    queryParts.add("FName=?");
	    }
	    if (!mi.equals("")) {
		    values.add(mi);
		    queryParts.add("MI=?");
	    }
	    if (!lname.equals("")) {
		    values.add(lname);
		    queryParts.add("LName=?");
	    }
		
	    //build the String for the query
	    query.append(queryParts.get(0));
	    for (int i = 1; i < queryParts.size(); i++) {
		    query.append(" AND ").append(queryParts.get(i));
	    }
	    
	    final Future<ResultSet> nameSubmit = executor.submit(
			    new SqlQueryTask(query.toString(), values));
	    
	    List<Pair<Integer, String>> idChoices = new ArrayList<>();
		
	    try (ResultSet nameResults = nameSubmit.get();
	         Statement stmt = nameResults.getStatement();
	         Connection conn = stmt.getConnection()
	    ) {
		    while (nameResults.next()) {
			    idChoices.add(new Pair<>(nameResults.getInt(1), nameResults.getString(2) + " " + nameResults.getString(3) + " " + nameResults.getString(4)));
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
		
		if (idChoices.size() > 1) {
			Pair<Integer, String> choice = Popups.displayChoiceDialog(idChoices, lname);
			
			personID = choice != null ? choice.getKey() : 0;
		}
		else if (idChoices.size() == 1) {
			//return the id
			personID = idChoices.get(0).getKey();
		}
		else {
			Popups.errorAlert("Person not found in database");
		}
		return personID;
	}
	
	//queries DB for Record_ID given the animal name associated with the animal ID recorded with the record
	private int getRecordID(String name) {
		name = name.replace("'", "’");
	    
		List<Object> animalName = new ArrayList<>();
		animalName.add(name);
		
		final Future<ResultSet> nameSubmit = executor.submit(
				new SqlQueryTask("SELECT Record_ID, RecA_ID, ExamDate FROM Medical_Records " +
						                 "WHERE RecA_ID IN (SELECT A_ID FROM Animal " +
						                 "WHERE Name = ?) ORDER BY Record_ID", animalName));
		
		List<Triplet<Integer, String, String>> choices = new ArrayList<>();
		
		try (ResultSet results = nameSubmit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			while(results.next()) {
				choices.add(new Triplet<Integer, String, String>(results.getInt(1), results.getString(2), results.getString(3)));
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
		
		//call the choice dialog to have the user select one of a set of options
		if (choices.size() > 1) {
		    Triplet<Integer, String, String> record = Popups.displayChoiceDialog(choices, "Record");
		    
		    return record != null ? record.getValue0() : 0;
		}
		else if (choices.size() == 1) {
		    return choices.get(0).getValue0();
		}
		else {
		    Popups.errorAlert("No record for that animal found.");
		    return 0;
		}	
	}
	
	private int getInventoryID(String type, String name) {
		//type is either Medication or Vaccine. append an s to get the associated table
		StringBuilder typeBuilder = new StringBuilder(type);
		type = typeBuilder.append('s').toString();
		
		if (type.equals("Medications")) {
			return getMedicationID(type, name);
		}
		else { //is a vaccine
			return getVaccineID(name);
		}
	}
	
	private int getMedicationID(String type, String name) {
		List<Object> typeValue = new ArrayList<>();
		typeValue.add(name);
		
		final Future<ResultSet> submit = executor.submit(
				new SqlQueryTask("SELECT Inv_ID, Name, Count FROM Medical_Inventory, " + type + " WHERE Inv_ID = M_ID AND Name = ?", typeValue));
		
		List<Triplet<Integer, String, Integer>> choices = new ArrayList<>();
		
		try (ResultSet results = submit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection();
		) {
			while (results.next()) {
				choices.add(new Triplet<>(results.getInt(1), results.getString(2), results.getInt(3)));
			}
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
		
		if (choices.size() > 1) {
			Triplet<Integer, String, Integer> medicine = Popups.displayChoiceDialog(choices, "Medicine");
			
			return medicine != null ? medicine.getValue0() : 0;
		}
		else if (choices.size() == 1) {         //if only one medication returned from query then return
			//return the id
			return choices.get(0).getValue0();
		}
		else {
			Popups.errorAlert("No medication with that name found.");
			return 0;
		}
	}
	
	private int getVaccineID(String name) {
		List<Object> nameValue = new ArrayList<>();
		nameValue.add(name);
		
		final Future<ResultSet> submit = executor.submit(
				new SqlQueryTask("SELECT Inv_ID, Name FROM Medical_Inventory WHERE Name = ?", nameValue));
		
		List<Pair<Integer, String>> choices = new ArrayList<>();
		
		try (ResultSet results = submit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection();
		) {
			while(results.next()) {
				choices.add(new Pair<>(results.getInt(1), results.getString(2)));
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
			Pair<Integer, String> vaccine = Popups.displayChoiceDialog(choices, "Vaccine");
			
			return vaccine != null ? vaccine.getKey() : 0;
		}
		else if (choices.size() == 1) {         //if only one animal returned from query then return
			//return the id
			return choices.get(0).getKey();
		}
		else {
			Popups.errorAlert("No vaccination with that name found.");
			return 0;
		}
	}
	
	private int getLocationID(String name) {
		int locationID = 0;
		name = name.replace("'", "’");
		
		List<Object> nameValues = new ArrayList<>();
		nameValues.add(name);
		
		final Future<ResultSet> nameSubmit = executor.submit(
				new SqlQueryTask("SELECT L_ID, Type FROM Location WHERE Name = ? ORDER BY L_ID", nameValues));
		
		List<Pair<Integer, String>> idChoices = new ArrayList<>();
		
		try (ResultSet nameResults = nameSubmit.get();
		     Statement stmt = nameResults.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			while (nameResults.next()) {
				idChoices.add(new Pair<>(nameResults.getInt(1), nameResults.getString(2)));
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
		
		//display choice dialog for case where there are multiple same names
		if (idChoices.size() > 1) {
			Pair<Integer, String> choice = Popups.displayChoiceDialog(idChoices, "Location ID");
			locationID = choice != null ? choice.getKey() : 0;
		}
		else if (idChoices.size() == 1) {
			locationID = idChoices.get(0).getKey();
		}
		else {
			Popups.errorAlert("Location not found in database");
		}
		
		return locationID;
	}
	//</editor-fold>
	
	//<editor-fold desc="Tab Submits">
	private void newPersonSubmit() {
		//generate id
		int personID = generatePersonID();
		//Location ID of place volunteer volunteers at
		int locationID = 0;
		
		//check that work phone and home phone are 10-digit
		if (newPersonWorkPhoneTextField.getText().length() != 10 || newPersonHomePhoneTextField.getText().length() != 10) {
		    Popups.errorAlert("Phone numbers must be ten digits.");
		    return;
		}
		
		//Construct P_ID text input dialog
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(null);
		
		//Get value from DatePicker
		LocalDate dob = newPersonDOBPicker.getValue();
		if (dob == null) {
		    Popups.errorAlert("Invalid date format.");
		    return;
		}
		
		//Store the checkboxes' states, check that at least one was selected
		final CheckBox[] checkBoxes = new CheckBox[]{newPersonStaffCheckBox,
							     newPersonVolunteerCheckBox,
							     newPersonFosterCheckBox,
		                                             newPersonAdopterCheckBox,
							     newPersonVetCheckBox};
		
		final String[] checkboxState = new String[5];
		
		boolean checkboxSelected = false;
		
		for (int i = 0; i < checkBoxes.length; i++) {
			//store checkbox state
			if(checkBoxes[i].isSelected()) {
				//checkbox is selected, change state to true
				checkboxState[i] = "1";
				checkboxSelected = true;
			}
			else {
				//checkbox is not selected, change state to false
				checkboxState[i] = "0";
			}
		}
		
		//Check subBox. At least one subBox must be selected
		
		final CheckBox[] subBox = {newPersonStaffGeneralSubBox,
		                           newPersonStaffAdministratorSubBox,
		                           newPersonStaffCoordinatorSubBox};
		
		final String[] subBoxState = new String[subBox.length];
		
		boolean subBoxSelected = false;
		
		for(int i=0;i<subBox.length;i++) {
		    if(subBox[i].isSelected() && !subBox[i].isDisable()) {
			subBoxState[i] = "1";
			subBoxSelected = true;
		    } else if (!subBox[i].isSelected() && !subBox[i].isDisable()) {
			subBoxState[i] = "0";
		    }
		}
		
		//No checkboxes were checked, display alert and return
		if(!checkboxSelected) {
			Popups.errorAlert("Please check at least one box.");
			return;
		}
		
		//Staff was selected and no subBox was selected, display alert and return
		if(!subBoxSelected && newPersonStaffCheckBox.isSelected()) {
			Popups.errorAlert("Please check at lest one association.");
			return;
		}
		
		//Get values from displayed text fields and choicebox, display alert if any are empty
		final TextField[] textFields = {newPersonFnameTextField,
		                                newPersonMiddleInitialTextField,
		                                newPersonLnameTextField,
		                                newPersonAddressTextField,
		                                newPersonCityTextField,
		                                newPersonZIPTextField,
		                                newPersonEmailTextField,
		                                newPersonHomePhoneTextField,
		                                newPersonWorkPhoneTextField,
		                                newPersonStaffSSNTextField,
		                                newPersonFosterPreferenceTextField};
		
		String[] textFieldValues = new String[textFields.length];
		
		for(int i = 0; i < textFields.length; i++) {
			textFieldValues[i] = textFields[i].getText();
			if ((textFieldValues[i].isBlank() && !textFields[i].isDisabled())
					&& textFields[i] != newPersonWorkPhoneTextField && textFields[i] != newPersonHomePhoneTextField) {
				//show alert with error message, return
				Popups.errorAlert("Please fill in all fields.");
				return;
			}	
			else if(textFields[i] == newPersonZIPTextField && newPersonZIPTextField.getText().length() < 5) { //zip gotta be 5 numbers
				Popups.errorAlert("ZIP must be 5 numbers.");
				return;
			}
			else if ((textFields[i] == newPersonEmailTextField) && (!newPersonEmailTextField.getText().toLowerCase().matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]"
															 + "+(?:.[a-z0-9!#$%&'*+/=?^_`{"
															 + "|}~-]+)*@(?:[a-z0-9](?:[a-z0"
															 + "-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"))) { //regex for proper email forma
				Popups.errorAlert("Email must be in a proper format.");
				return;
			}
			else if (((textFields[i] == newPersonWorkPhoneTextField) && (textFields[i].getText().length() < 10)) 
			      || ((textFields[i] == newPersonHomePhoneTextField) && (textFields[i].getText().length() < 10))) {
				Popups.errorAlert("Please fill in all fields.\nPhone numbers must be 10 digits.");
				return;
			}
		}
		
		//Get locationID, check that it was entered
		if (!newPersonVolunteerLocationTextField.getText().isBlank()) {
		    locationID = getLocationID(newPersonVolunteerLocationTextField.getText());
		    if (locationID == 0) {
			return;
		    }
		}
		
		//Get values from choiceboxes
		
		//volunteer choice box
		if(!newPersonVolunteerChoiceBox.isDisabled() && newPersonVolunteerChoiceBox.getValue().isBlank()) {
			Popups.errorAlert("Please fill in all fields.");
			return;
		}
		
		//state combobox
		if(newPersonStateComboBox.getValue().isBlank()) {
			Popups.errorAlert("Please fill in all fields.");
			return;
		}
		
		//Attempt to insert new data into database
		//Note: Ensure that data that is present in disabled fields is not included
		//A field is disabled if associated checkbox is not selected, so check checkboxes
		//Build sql statement
		StringBuilder sqlFrontBuilder = new StringBuilder("INSERT INTO Person (P_ID, FName, MI, LName, DOB, Address, Email, BannedYN, StaffFlag, VolFlag, FosterFlag, AdopterFlag, VetFlag");
		StringBuilder sqlBackBuilder = new StringBuilder(") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?");
		
		//collate address values
		String address = (newPersonAddressTextField.getText() + ", " + newPersonCityTextField.getText() + ", " +
				newPersonStateComboBox.getValue() + ", " + newPersonZIPTextField.getText());
		
		List<Object> personValues = new ArrayList<>();
		personValues.add(personID);
		personValues.add(newPersonFnameTextField.getText());
		personValues.add(newPersonMiddleInitialTextField.getText());
		personValues.add(newPersonLnameTextField.getText());
		personValues.add(dob);
		personValues.add(address); 
		personValues.add(newPersonEmailTextField.getText());
		personValues.add("0");
		
		Collections.addAll(personValues, checkboxState);
		
		if (checkBoxes[0].isSelected()) {
		    personValues.add(newPersonStaffSSNTextField.getText());
		    sqlFrontBuilder.append(", StaffSSN");
		    sqlBackBuilder.append(", ?");
		}
		if (checkBoxes[1].isSelected()) {
		    personValues.add(newPersonVolunteerChoiceBox.getValue());
		    sqlFrontBuilder.append(", VolLevel");
		    sqlBackBuilder.append(", ?");
		}
		if (checkBoxes[2].isSelected()) {
		    personValues.add(newPersonFosterPreferenceTextField.getText());
		    sqlFrontBuilder.append(", Preference");
		    sqlBackBuilder.append(", ?");
		}
		
		sqlBackBuilder.append(")");
		sqlFrontBuilder.append(sqlBackBuilder.toString());
		String sqlStatement = sqlFrontBuilder.toString();
		
		
		//insert values into db
		
		final Future<Integer> personSubmit = executor.submit(
				new SqlUpdateTask(sqlStatement, personValues));
		
		try {
		    Integer submitResult = personSubmit.get();
		    if (submitResult != null) {
			Popups.infoAlert("Person updated successfully, " + submitResult + " rows affected");
		    }
		    else {
			Popups.errorAlert("Database was unable to be updated");
		    }
		} 
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		addPhoneNumbers(personID);
		
		addVolunteerToLocation(personID, locationID);
		
		if (newPersonStaffCheckBox.isSelected()) {
		    addAssociations(subBoxState, personID);
		}
	}
	
	private void addPhoneNumbers(int personID) {
		//insert phone values into db
		String[] phoneNumbers = {buildPhone(newPersonHomePhoneTextField.getText()),
		                         buildPhone(newPersonWorkPhoneTextField.getText())};
		int len = phoneNumbers.length;
		if (phoneNumbers[0].equals(phoneNumbers[1])) { //Only do one loop
		    len--;
		}
		for (int i=0;i<len;i++) {
			List<Object> phoneValues = new ArrayList<>();
			phoneValues.add(personID);
			phoneValues.add(i);
			
			final Future<Integer> phoneSubmit = executor.submit(
					new SqlUpdateTask("INSERT INTO Phone_Numbers (Person_ID, PhoneNum) VALUES (?, ?)", phoneValues));
			
			try {
				Integer submitResult = phoneSubmit.get();
				if (submitResult != null) {
					Popups.infoAlert("Phone_Numbers updated successfully, " + submitResult + " rows affected");
				}
				else {
					Popups.errorAlert("Database was unable to be updated");
				}
			}
			catch (ExecutionException e) {
				e.printStackTrace();
				Popups.errorAlert(e.getMessage());
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				Popups.errorAlert(e.getMessage());
				Thread.currentThread().interrupt();
			}
		}
	}
	//state array must consist solely of 0s or 1s with length 3
	private void addAssociations(String[] state, int personID) {
	    for (int i=0;i<state.length;i++) {
		if (!state[i].equals("0")) {
		    
		    List<Object> associationValues = new ArrayList<>();
		    associationValues.add(associationNames[i]);
		    associationValues.add(personID);
		    
		    final Future<Integer> associationSubmit = executor.submit(
				    new SqlUpdateTask("INSERT INTO Associations (Association, AssocP_ID) VALUES (?, ?)", associationValues));

		    try {
			Integer submitResult = associationSubmit.get();
			    if (submitResult != null) {
				Popups.infoAlert("Associations updated successfully, " + submitResult + " rows affected");
			    }
			    else {
				Popups.errorAlert("Database was unable to be updated");
			    }
			}
			catch (ExecutionException e) {
				e.printStackTrace();
				Popups.errorAlert(e.getMessage());
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				Popups.errorAlert(e.getMessage());
				Thread.currentThread().interrupt();
			}
		}
	    }
	}
	
	private void addVolunteerToLocation(int personID, int locationID) {
		if (locationID != 0) {
			List<Object> locationValues = new ArrayList<>();
			locationValues.add(personID);
			locationValues.add(locationID);
			
			final Future<Integer> locationSubmit = executor.submit(
					new SqlUpdateTask("INSERT INTO Vol_At (Vol_ID, VolLoc_ID) VALUES (?, ?)", locationValues));
			
			try {
				Integer submitResult = locationSubmit.get();
				if (submitResult != null) {
					Popups.infoAlert("Vol_At updated successfully, " + submitResult + " rows affected");
				}
				else {
					Popups.errorAlert("Database was unable to be updated");
				}
			}
			catch (ExecutionException e) {
				e.printStackTrace();
				Popups.errorAlert(e.getMessage());
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				Popups.errorAlert(e.getMessage());
				Thread.currentThread().interrupt();
			}
		}
	}
	
	private void locationSubmit() {
		int locationID;
		int personID = 0;
		
		//Get values from displayed text fields and choicebox, display alert if any are empty
		final TextField[] textFields = {locationNameTextField, locationCapacityTextField};
		
		if (!isTextFieldsValid(textFields)) {
			return;
		}
		
		//Get values from contact fields, which may be null, but all are required together
		String firstName = locationFirstNameTextField.getText();
		String middleInitial = locationMiddleInitialTextField.getText();
		String lastName = locationLastNameTextField.getText();
		
		if ((!firstName.isBlank() && (middleInitial.isBlank() || lastName.isBlank()))
				|| (!middleInitial.isBlank() && (firstName.isBlank() || lastName.isBlank()))
				|| (!lastName.isBlank() && (firstName.isBlank() || middleInitial.isBlank()))
		) {
		    Popups.errorAlert("If entering a contact's name, all name fields are required.");
		    return;
		}
		
		String location = locationTypeChoiceBox.getValue();
		if (location.isBlank()) {
		    Popups.errorAlert("Please enter a location type.");
		    return;
		}
		
		//Generate location ID
		locationID = generateLocationID();
		
		//Get contact's person ID
		//Contact ID may be null
		if (!firstName.isBlank()) {
		    personID = getPersonID(firstName, middleInitial, lastName);
		    
		    if (personID == 0) {
			return;
		    }
		}
		//Build List
		List<Object> values = new ArrayList<>();
		values.add(locationID);
		values.add(locationNameTextField.getText());
		values.add(location);
		
		try {
			values.add(Integer.parseInt(locationCapacityTextField.getText()));
		}
		catch (NumberFormatException e) {
			Popups.errorAlert("Invalid Number for location capacity");
			return;
		}
		
		if (!firstName.isBlank()) {
		    values.add(personID);
		}
		
		StringBuilder sqlStatementBuilder = new StringBuilder("INSERT INTO Location (L_ID, Name, Type, Capacity");
		
		if (!firstName.isBlank()) {
		    sqlStatementBuilder.append(", Contact_ID) VALUES (?, ?, ?, ?, ?)");
		}
		else {
		    sqlStatementBuilder.append(") VALUES (?, ?, ?, ?)");
		}
		
		String sqlStatement = sqlStatementBuilder.toString();
		
		//insert
		final Future<Integer> submit = executor.submit(new SqlUpdateTask(sqlStatement, values));
		
		try {
		    Integer submitResult = submit.get();
		    if (submitResult != null) {
			Popups.infoAlert("Location updated successfully, " + submitResult + " rows affected");
		    }
		    else {
			Popups.errorAlert("Database was unable to be updated");
		    }
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}
	
	private void inventorySubmit() {
		//IDs
		int inventoryID;
		
		//Get values from displayed text fields, display alert if any are empty
		final TextField[] textFields = {inventoryNameTextField,
		                                inventoryCountTextField,
		                                inventoryMedicationAdminFreqTextField,
		                                inventoryMedicationLengthTextField,
		                                inventoryVaccineExpectedByTextField,
		                                inventoryVaccineRevacFreqTextField};
		
		if (!isTextFieldsValid(textFields)) {
			return;
		}
		
		//Generate inventoryID
		inventoryID = generateInventoryID();
		
		String type;
		//Build Medical_Inventory list
		List<Object> inventoryValues = new ArrayList<>();
		inventoryValues.add(inventoryID);
		inventoryValues.add(inventoryNameTextField.getText());
		
		try {
			inventoryValues.add(Integer.parseInt(inventoryCountTextField.getText()));
		}
		catch (NumberFormatException e) {
			Popups.errorAlert("Invalid Number in inventory count");
			return;
		}
		
		//Build either Medications or Vaccines list
		List<Object> typeValues = new ArrayList<>();
		if(inventoryMedicationRadioButton.isSelected()) {
		    type = "Medications";
		    typeValues.add(inventoryID);
		    typeValues.add(inventoryMedicationAdminFreqTextField.getText());
		    typeValues.add(inventoryMedicationLengthTextField.getText());
		}
		else { //is vaccines
		    type = "Vaccines";
		    typeValues.add(inventoryID);
		    typeValues.add(inventoryVaccineRevacFreqTextField.getText());
		    typeValues.add(inventoryVaccineExpectedByTextField.getText());
		}
		
		//insert inventory values into db
		
		final Future<Integer> inventorySubmit = executor.submit(
				new SqlUpdateTask("INSERT INTO Medical_Inventory (Inv_ID, Name, Count) VALUES (?, ?, ?)", inventoryValues));
		
		try {
		    Integer submitResult = inventorySubmit.get();
		    if (submitResult != null) {
			Popups.infoAlert("Medical_Inventory updated successfully, " + submitResult + " rows affected");
		    }
		    else {
			Popups.errorAlert("Database was unable to be updated");
		    }
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		//insert type values into db
		final Future<Integer> typeSubmit = executor.submit(
				new SqlUpdateTask("INSERT INTO " + type + " VALUES (?, ?, ?)", typeValues));
		
		try {
		    Integer submitResult = typeSubmit.get();
		    if (submitResult != null) {
			Popups.infoAlert("Vaccines/Medications updated successfully, " + submitResult + " rows affected");
		    }
		    else {
			Popups.errorAlert("Database was unable to be updated");
		    }
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}
	
	private void animalIntakeSubmit() {
		boolean isSuccessful = false;
		
		//Generate ID
		int id = generateAnimalID();
		
		//Get values from displayed text fields, display alert if any are empty
		//Note: First two indices of array store Intake values, last index stores Other species
		final TextField[] textFields = {animalIntakeOtherSpeciesTextField,
		                                animalIntakeStatusTextField,
		                                animalIntakeReasonTextField,
		                                animalIntakeNameTextField,
		                                animalIntakeStageTextField,
		                                animalIntakeBreedTextField,
		                                animalIntakeHeightTextField,
		                                animalIntakeWeightTextField};
		
		if (!isTextFieldsValid(textFields)) {
			return;
		}
		
		isSuccessful = addNewAnimal(id);
		
		if (isSuccessful) {
			List<Object> values = new ArrayList<>();
			
			values.add(id);
			values.add(animalIntakeDatePicker.getValue());
			values.add(animalIntakeStatusTextField.getText());
			values.add(animalIntakeReasonTextField.getText());
			
			Future<Integer> submit = executor.submit(
					new SqlUpdateTask("INSERT INTO Intake (IntakeA_ID, Date, Status, Reason) VALUES (?, ?, ?, ?) ", values));
			
			try  {
				Integer submitResult = submit.get();
				if (submitResult != null) {
					Popups.infoAlert("Intake updated successfully, " + submitResult + " rows affected");
				}
				else {
					Popups.errorAlert("Database was unable to be updated");
				}
			}
			catch (ExecutionException e) {
				e.printStackTrace();
				Popups.errorAlert(e.getMessage());
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				Popups.errorAlert(e.getMessage());
				Thread.currentThread().interrupt();
			}
		}
	}
	
	private boolean addNewAnimal(int id) {
		List<Object> values = new ArrayList<>();
		StringBuilder sqlStatementBuilder = new StringBuilder("INSERT INTO Animal (A_ID, Name, DOB, Sex, Breed, Height, Weight, Size, Stage, FixedYN");
		
		values.add(id);
		values.add(animalIntakeNameTextField.getText());
		
		LocalDate dob = animalIntakeDOBPicker.getValue();
		if (dob == null) {
			Popups.errorAlert("Invalid date format");
			return false;
		}
		values.add(dob);
		
		values.add(animalIntakeMaleRadioButton.isSelected() ? "M" : "F");
		values.add(animalIntakeBreedTextField.getText());
		
		try {
			values.add(Integer.parseInt(animalIntakeHeightTextField.getText()));
			values.add(Integer.parseInt(animalIntakeWeightTextField.getText()));
			
			values.add(animalIntakeSizeChoiceBox.getValue());
			values.add(animalIntakeStageTextField.getText());
			values.add(animalIntakeFixedCheckBox.isSelected() ? "Y" : "N");
			
			if (!animalIntakeParentTextField.getText().isBlank()) {
				int parent = Integer.parseInt(animalIntakeParentTextField.getText());
				
				if (Utils.isAnimalIdValid(parent)) {
					values.add(parent);
					sqlStatementBuilder.append(", Parent_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				}
				else {
					sqlStatementBuilder.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				}
			}
			else {
				sqlStatementBuilder.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			}
		}
		catch (NumberFormatException e) {
			Popups.errorAlert("Invalid number for height, weight or parent");
			return false;
		}
		
		Future<Integer> submit = executor.submit(
				new SqlUpdateTask(sqlStatementBuilder.toString(), values));
		
		try  {
			Integer submitResult = submit.get();
			if (submitResult != null) {
				Popups.infoAlert("Animal updated successfully, " + submitResult + " rows affected");
			}
			else {
				Popups.errorAlert("Database was unable to be updated");
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		if (animalIntakeCatRadioButton.isSelected()) {
			return addNewCat(id);
		}
		else if (animalIntakeDogRadioButton.isSelected()) {
			return addNewDog(id);
		}
		else {
			return addNewOther(id);
		}
	}
	
	private boolean addNewCat(int id) {
		boolean success = false;
		List<Object> values = new ArrayList<>();
		
		values.add(id);
		values.add(animalIntakeCatDeclawedCheckBox.isSelected() ? "Y" : "N");
		values.add(animalIntakeCatWorkingCheckBox.isSelected() ? "Y" : "N");
		
		Future<Integer> submit = executor.submit(
				new SqlUpdateTask("INSERT INTO Cat (Cat_ID, DeclawedYN, WorkCatYN) VALUES (?, ?, ?) ", values));
		
		try  {
			Integer submitResult = submit.get();
			if (submitResult != null) {
				Popups.infoAlert("Cat updated successfully, " + submitResult + " rows affected");
				success = true;
			}
			else {
				Popups.errorAlert("Database was unable to be updated");
				success = false;
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		return success;
	}
	
	private boolean addNewDog(int id) {
		boolean success = false;
		List<Object> values = new ArrayList<>();
		
		values.add(id);
		values.add(animalIntakeDogBiteCheckBox.isSelected() ? "Y" : "N");
		
		Future<Integer> submit = executor.submit(
				new SqlUpdateTask("INSERT INTO Dog (Dog_ID, BiteHistoryYN) VALUES (?, ?) ", values));
		
		try  {
			Integer submitResult = submit.get();
			if (submitResult != null) {
				Popups.infoAlert("Dog updated successfully, " + submitResult + " rows affected");
				success = true;
			}
			else {
				Popups.errorAlert("Database was unable to be updated");
				success = false;
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		return success;
	}
	
	private boolean addNewOther(int id) {
		boolean success = false;
		List<Object> values = new ArrayList<>();
		
		values.add(id);
		values.add(animalIntakeOtherSpeciesTextField.getText());
		
		Future<Integer> submit = executor.submit(
				new SqlUpdateTask("INSERT INTO Other (Other_ID, Species) VALUES (?, ?) ", values));
		
		try  {
			Integer submitResult = submit.get();
			if (submitResult != null) {
				Popups.infoAlert("Other updated successfully, " + submitResult + " rows affected");
				success = true;
			}
			else {
				Popups.errorAlert("Database was unable to be updated");
				success = false;
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		return success;
	}
	
	private void animalUpdateSubmit() {
		List<Object> values = new ArrayList<>();
		
		values.add(animalUpdateStageTextField.getText());
		try {
			values.add(Integer.parseInt(animalUpdateHeightTextField.getText()));
			values.add(Integer.parseInt(animalUpdateWeightTextField.getText()));
		}
		catch (NumberFormatException e) {
			Popups.errorAlert("Invalid number for height or weight");
			return;
		}
		values.add(animalUpdateSizeChoiceBox.getValue());
		values.add(animalUpdateFixedCheckBox.isSelected() ? "Y" : "N");
		values.add(animalUpdateId);
		
		Future<Integer> submit = executor.submit(
				new SqlUpdateTask("UPDATE Animal SET Stage=?, Height=?, Weight=?, Size=?, FixedYN=? WHERE A_ID=?", values));
		
		try  {
			Integer submitResult = submit.get();
			if (submitResult != null) {
				Popups.infoAlert("Animal updated successfully, " + submitResult + " rows affected");
			}
			else {
				Popups.errorAlert("Database was unable to be updated");
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		if (animalUpdateCatRadioButton.isSelected()) {
			animalUpdateCatSubmit();
		}
		else {
			animalUpdateDogSubmit();
		}
	}
	
	private void animalUpdateCatSubmit() {
		List<Object> values = new ArrayList<>();
		
		values.add(animalUpdateCatDeclawedCheckBox.isSelected() ? "Y" : "N");
		values.add(animalUpdateCatWorkingCheckBox.isSelected() ? "Y" : "N");
		values.add(animalUpdateId);
		
		Future<Integer> submit = executor.submit(
				new SqlUpdateTask("UPDATE Cat SET DeclawedYN=?, WorkCatYN=? WHERE Cat_ID=?", values));
		
		try  {
			Integer submitResult = submit.get();
			if (submitResult != null) {
				Popups.infoAlert("Cat updated successfully, " + submitResult + " rows affected");
			}
			else {
				Popups.errorAlert("Database was unable to be updated");
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}
	
	private void animalUpdateDogSubmit() {
		List<Object> values = new ArrayList<>();
		
		values.add(animalUpdateDogBiteHistoryCheckBox.isSelected() ? "Y" : "N");
		values.add(animalUpdateId);
		
		Future<Integer> submit = executor.submit(
				new SqlUpdateTask("UPDATE Dog SET BiteHistoryYN=? WHERE Dog_ID=?", values));
		
		try  {
			Integer submitResult = submit.get();
			if (submitResult != null) {
				Popups.infoAlert("Dog updated successfully, " + submitResult + " rows affected");
			}
			else {
				Popups.errorAlert("Database was unable to be updated");
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}
	
	private void animalFosterSubmit() {
		List<Object> values = new ArrayList<>();
		int personID;
		int animalID;
		int locationID;
		LocalDate today = LocalDate.now();
		
		TextField[] textFields = {animalFosterPersonTextField,
		                          animalFosterAnimalTextField,
		                          animalFosterStartReasonTextField,
		                          animalFosterEndReasonTextField};
		
		//validate that all fields are not empty
		if (!isTextFieldsValid(textFields)) {
			return;
		}
		
		//get the animal number
		animalID = getAnimalID(animalFosterAnimalTextField.getText());
		
		if (animalID == 0) {
			Popups.errorAlert("No Animal with that name");
			return;
		}
		values.add(animalID);
		
		//get the person number
		try {
			personID = Integer.parseInt(animalFosterPersonTextField.getText());
		}
		catch (NumberFormatException e) {
			Popups.errorAlert("Enter a number into the Person text field");
			return;
		}
		
		//make sure foster is valid and not on quarantine
		if (!Utils.isValidFoster(personID)) {
			return;
		}
		values.add(personID);
		
		
		//check if animal is currently fostered
		boolean isFostered = isAnimalFostered(animalID);
		
		if (animalFosterStartReasonRadioButton.isSelected()) {
			if (isFostered) {
				Popups.errorAlert("Animal Already Fostered, Please enter an End Reason and submit before changing their location");
				return;
			}
			
			values.add(today); //startDate
			values.add(animalFosterStartReasonTextField.getText()); //startReason
			
			if (!insertFosterLog(values)) {
				return;
			}
		}
		else {
			if (!isFostered) {
				Popups.errorAlert("Animal is not Fostered");
				return;
			}
			
			values.add(0, animalFosterEndReasonTextField.getText()); //endReason
			values.add(0, today); //endDate
			
			if (!updateFosterLog(values)) {
				return;
			}
		}
		
		//getLocationID
		locationID = getFosterLocationID(personID);
		
		//update pet locations
		updatePetLocation(animalID, locationID);
	}
	
	private boolean insertFosterLog(List<Object> values) {
		boolean isSuccessful = false;
		
		Future<Integer> submit = executor.submit(
				new SqlUpdateTask("INSERT INTO Foster_Log (FosterA_ID, Foster_ID, StartDate, StartReason) " +
						                  "VALUES (?, ?, ?, ?)", values));
		
		try {
			Integer submitResult = submit.get();
			if (submitResult != null) {
				Popups.infoAlert("Database updated successfully, " + submitResult + " rows affected");
				isSuccessful = true;
			}
			else {
				Popups.errorAlert("Database was unable to be updated");
				isSuccessful = false;
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		return isSuccessful;
	}
	
	private boolean updateFosterLog(List<Object> values) {
		boolean isSuccessful = false;
		
		Future<Integer> submit = executor.submit(
				new SqlUpdateTask("UPDATE Foster_Log SET EndDate=?, EndReason=? " +
						                  "WHERE FosterA_ID=? AND Foster_ID=? AND EndDate IS NULL", values));
		
		try {
			Integer submitResult = submit.get();
			if (submitResult != null) {
				Popups.infoAlert("Database updated successfully, " + submitResult + " rows affected");
				isSuccessful = true;
			}
			else {
				Popups.errorAlert("Database was unable to be updated");
				isSuccessful = false;
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		return isSuccessful;
	}
	
	//this should never actually return 0, bc we already check earlier that the person is a foster,
	// and all fosters should have a location associated with them
	private int getFosterLocationID(int fosterID) {
		int locationID = 0;
		List<Object> values = new ArrayList<>();
		values.add(fosterID);
		
		Future<ResultSet> submit = executor.submit(
				new SqlQueryTask("SELECT L_ID FROM Location WHERE Contact_ID=?", values));
		
		try (ResultSet results = submit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			if (results.first()) {
				locationID = results.getInt(1);
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
		return locationID;
	}
	
	private boolean isAnimalFostered(int animalID) {
		boolean isFostered = false;
		List<Object> values = new ArrayList<>();
		values.add(animalID);
		
		Future<ResultSet> submit = executor.submit(
				new SqlQueryTask("SELECT FosterA_ID, EndDate FROM Foster_Log WHERE FosterA_ID=? AND EndDate IS NULL", values));
		
		try (ResultSet results = submit.get();
		     Statement stmt = results.getStatement();
		     Connection conn = stmt.getConnection()
		) {
			if (results.first()) {
				isFostered = true;
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
		return isFostered;
	}
	
	private void updatePetLocation(int animalID, int locationID) {
		List<Object> values = new ArrayList<>();
		values.add(locationID);
		values.add(animalID);
		
		Future<Integer> submit = executor.submit(
				new SqlUpdateTask("UPDATE Pet_Locations SET PetL_ID=? WHERE PetLocA_ID=?", values));
		
		try {
			Integer submitResult = submit.get();
			if (submitResult != null) {
				Popups.infoAlert("Database updated successfully, " + submitResult + " rows affected");
			}
			else {
				Popups.errorAlert("Database was unable to be updated");
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}
	
	private void animalOutcomeSubmit(ActionEvent event) {
		List<Object> values = new ArrayList<>();
		int animalID;
		
		//get values from text fields
		final TextField[] textFields = {animalOutcomeAnimalNameTextField,
		                                animalOutcomeOTextField,
		                                animalOutcomeOReasonTextField};
		
		if (!isTextFieldsValid(textFields)) {
			return;
		}
		
		LocalDate date = animalOutcomeODateDatePicker.getValue();
		
		if (date == null) {
			Popups.errorAlert("Invalid date format.");
			return;
		}
		
		//query DB for A_ID. If it does not exist, return 0
		animalID = getAnimalID(animalOutcomeAnimalNameTextField.getText());
		
		if (animalID == 0) {
			return; //failed to find valid animal, per error message displayed by method
		}
		
		values.add(animalOutcomeOTextField.getText());
		values.add(animalOutcomeOReasonTextField.getText());
		values.add(date);
		values.add(animalID);
		
		//submit data
		final Future<Integer> submit = executor.submit(
				new SqlUpdateTask("UPDATE Animal SET Outcome=?, OutcomeReason=?, OutcomeDate=? WHERE A_ID=?", values));
		
		try {
		    Integer submitResult = submit.get();
		    if (submitResult != null) {
			Popups.infoAlert("Outcome updated successfully, " + submitResult + " rows affected");
		    }
		    else {
			Popups.errorAlert("Database was unable to be updated");
		    }
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}
	
	//Two fields may be null in basic info: notes and review date
	private void basicInfoSubmit() {
		//Animal/Record ID
		int animalID;
		int recordID;
		
		//Get values from text fields
		final TextField[] textFields = {basicInfoAnimalNameTextField,
		                                basicInfoTypeTextField};
		
		if (!isTextFieldsValid(textFields)) {
			return;
		}
		
		//Get value from exam date, which cannot be null
		LocalDate examDate = basicInfoExamDateDatePicker.getValue();
		if (examDate == null) {
			Popups.errorAlert("Invalid date format.");
			return;
		}
		
		//Get value from review date, which may be null
		LocalDate reviewDate = basicInfoReviewDateDatePicker.getValue();
		if (reviewDate == null) {
			if (!basicInfoReviewDateDatePicker.getEditor().getText().isBlank()) {
				Popups.errorAlert("Invalid date format.");
				return;
			}
		}
		else if (reviewDate.compareTo(examDate) < 0) {
			Popups.errorAlert("Review date cannot be before exam/surgery date");
			return;
		}
		
		//Get value from choicebox, may not be null but we check it above
		String typeValue = basicInfoTypeTextField.getText();
		
		//Insert data into db
		//Get AnimalID
		animalID = getAnimalID(basicInfoAnimalNameTextField.getText());
		if (animalID == 0) {
			return; //failed to find valid animal
		}
		
		//Generate RecordID
		recordID = generateRecordID();
		
		//Create lists, one for Medical_Records, one for Notes
		//Construct SQL statement based on field values
		StringBuilder sqlStatementBuilder = new StringBuilder("INSERT INTO Medical_Records (Record_ID, RecA_ID, Type, ExamDate");
		
		List<Object> recordValues = new ArrayList<>();
		recordValues.add(recordID);
		recordValues.add(animalID);
		recordValues.add(typeValue);
		recordValues.add(examDate);
		
		if (reviewDate != null) {
			recordValues.add(reviewDate);
			sqlStatementBuilder.append(", ReviewDate) VALUES (?, ?, ?, ?, ?)");
		}
		else {
			sqlStatementBuilder.append(") VALUES (?, ?, ?, ?)");
		}
		
		String sqlStatement = sqlStatementBuilder.toString();
		
		final Future<Integer> submit = executor.submit(new SqlUpdateTask(sqlStatement, recordValues));
		
		try {
			Integer submitResult = submit.get();
			
			if (submitResult != null) {
				Popups.infoAlert("Medical_Records updated successfully, " + submitResult + " rows affected");
			}
			else {
				Popups.infoAlert("Database was unable to be updated");
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		basicInfoNotesSubmit(animalID, recordID);
	}
	
	private void basicInfoNotesSubmit(int animalID, int recordID) {
		//Get value from notes, which may be null
		String notesValue = basicInfoNotesTextArea.getText();
		
		if (!notesValue.isBlank()) {
			List<Object> values = new ArrayList<>();
			values.add(animalID);
			values.add(recordID);
			values.add(notesValue);
			
			final Future<Integer> notesSubmit = executor.submit(
					new SqlUpdateTask("INSERT INTO Notes VALUES (?, ?, ?)", values));
			
			try {
				Integer submitResult = notesSubmit.get();
				
				if (submitResult != null) {
					Popups.infoAlert("Notes updated successfully, " + submitResult + " rows affected");
				}
				else {
					Popups.errorAlert("Database was unable to be updated");
				}
				
			}
			catch (ExecutionException e) {
				e.printStackTrace();
				Popups.errorAlert(e.getMessage());
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				Popups.errorAlert(e.getMessage());
				Thread.currentThread().interrupt();
			}
		}
	}
	
	private void vaccinesSubmit() {
		//IDs
		int animalID;
		int recordID;
		int vetID;
		int vacID;
		
		//Get values from text fields
		final TextField[] textFields = {vaccinesNameTextField,
		                                vaccinesAnimalNameTextField,
		                                vaccinesFirstNameTextField,
		                                vaccinesMiddleInitialTextField,
		                                vaccinesLastNameTextField};
		
		if (!isTextFieldsValid(textFields)) {
			return;
		}
		
		//Get value from Date Picker
		LocalDate vacDate = vaccinesVaccDateDatePicker.getValue();
		if (vacDate == null) {
			Popups.errorAlert("Invalid date format");
			return;
		}
		
		//Get value from text area
		String descriptionValue = vaccinesDescriptionTextArea.getText();
		if (descriptionValue.isBlank()) {
			Popups.errorAlert("Please fill in all fields");
			return;
		}
		
		//Get animal ID
		animalID = getAnimalID(vaccinesAnimalNameTextField.getText());
		if (animalID == 0) {
			return;
		}
		
		//Get record ID
		recordID = getRecordID(vaccinesAnimalNameTextField.getText());
		if (recordID == 0) {
			return;
		}
		
		//Get vet ID
		vetID = getPersonID(vaccinesFirstNameTextField.getText(), vaccinesMiddleInitialTextField.getText(), vaccinesLastNameTextField.getText());
		if (vetID == 0) {
			return;
		}
		
		//Get vacc ID
		vacID = getInventoryID("Vaccine", vaccinesNameTextField.getText());
		if (vacID == 0) {
			return;
		}
		
		//Build list
		List<Object> values = new ArrayList<>();
		values.add(recordID);
		values.add(vacID);
		values.add(animalID);
		values.add(descriptionValue);
		values.add(vacDate);
		values.add(vetID);
		
		final Future<Integer> submit = executor.submit(
				new SqlUpdateTask("INSERT INTO Vaccines_Given (VacR_ID, Vaccine_ID, VacA_ID, Description, VacDate, VacVet_ID) VALUES (?, ?, ?, ?, ?, ?)", values));
		
		try {
			Integer submitResult = submit.get();
			
			if (submitResult != null) {
				Popups.infoAlert("Vaccines_Given updated successfully, " + submitResult + " rows affected");
			}
			else {
				Popups.errorAlert("Database was unable to be updated");
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}
	
	private void medicationsSubmit() {
		//IDs
		int animalID;
		int recordID;
		int inventoryID;
		int vetID;
		
		//Get values from text fields
		final TextField[] textFields = {medicationsNameTextField,
		                                medicationsAnimalNameTextField,
		                                medicationsFirstNameTextField,
		                                medicationsMiddleInitialTextField,
		                                medicationsLastNameTextField,
						medicationsDosageTextField};
		
		if (!isTextFieldsValid(textFields)) {
			return;
		}
		
		//Get values from DatePickers
		LocalDate givenDate = medicationsGivenDateDatePicker.getValue();
		LocalDate reviewDate = medicationsReviewDateDatePicker.getValue();
		if (givenDate == null || reviewDate == null) {
			Popups.errorAlert("Invalid date format.");
			return;
		}
		else if (reviewDate.compareTo(givenDate) < 0) {
			Popups.errorAlert("Review date cannot be before Given date.");
			return;
		}
		
		
		//Get animalID
		animalID = getAnimalID(medicationsAnimalNameTextField.getText());
		if (animalID == 0) {
			return;
		}
		
		//Get recordID
		recordID = getRecordID(medicationsAnimalNameTextField.getText());
		if (recordID == 0) {
			return;
		}
		
		//Get vetID
		vetID = getPersonID(medicationsFirstNameTextField.getText(), medicationsMiddleInitialTextField.getText(), medicationsLastNameTextField.getText());
		if (vetID == 0) {
			return;
		}
		
		//Get inventoryID
		inventoryID = getInventoryID("Medication", medicationsNameTextField.getText());
		if (inventoryID == 0) {
			return;
		}
		
		//Build list
		List<Object> values = new ArrayList<>();
		values.add(recordID);
		values.add(inventoryID);
		values.add(animalID);
		values.add(medicationsDosageTextField.getText());
		values.add(givenDate);
		values.add(reviewDate);
		values.add(vetID);
		
		//add values into db
		final Future<Integer> submit = executor.submit(
				new SqlUpdateTask("INSERT INTO Medication_Given (MedR_ID, Medication_ID, MedA_ID, Dose, GiveDate, ReviewDate, MedVet_ID) VALUES (?, ?, ?, ?, ?, ?, ?)", values));
		
		try {
			Integer submitResult = submit.get();
			
			if (submitResult != null) {
				Popups.infoAlert("Database updated successfully, " + submitResult + " rows affected");
			}
			else {
				Popups.errorAlert("Database was unable to be updated");
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}
	
	private void conditionsSubmit() {
		//IDs
		int animalID;
		int recordID;
		
		//Get values from text fields
		final TextField[] textFields = {conditionsAnimalNameTextField,
		                                conditionsConditionNameTextField,
		                                conditionsBodyPartTextField};
		
		if (!isTextFieldsValid(textFields)) {
			return;
		}
		
		//Get value from DatePicker
		LocalDate notedDate = conditionsNotedDateDatePicker.getValue();
		if (notedDate == null) {
			Popups.errorAlert("Invalid date format.");
			return;
		}
		
		//get animalID
		animalID = getAnimalID(conditionsAnimalNameTextField.getText());
		if (animalID == 0) {
			return;
		}
		
		//get recordID
		recordID = getRecordID(conditionsAnimalNameTextField.getText());
		if (recordID == 0) {
			return;
		}
		
		//build list
		List<Object> values = new ArrayList<>();
		values.add(animalID);
		values.add(recordID);
		values.add(conditionsConditionNameTextField.getText());
		values.add(notedDate);
		values.add(conditionsBodyPartTextField.getText());
		
		final Future<Integer> submit = executor.submit(
				new SqlUpdateTask("INSERT INTO Conditions (CondA_ID, CondR_ID, CondName, NotedDate, BodyPart) VALUES (?, ?, ?, ?, ?)", values));
		
		try {
			Integer submitResult = submit.get();
			
			if (submitResult != null) {
				Popups.infoAlert("Conditions updated successfully, " + submitResult + " rows affected");
			}
			else {
				Popups.errorAlert("Database was unable to be updated");
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}
	
	private void testsSubmit() {
		//IDs
		int animalID;
		int recordID;
		
		//Get values from text fields that cannot be null
		final TextField[] textFields = {testsAnimalNameTextField, testsTestNameTextField};
		
		if (!isTextFieldsValid(textFields)) {
			return;
		}
		
		//Get value from text field that may be null
		String resultValue = testsResultTextField.getText();
		
		//Get value from datepickers, test date is not null
		LocalDate testDate = testsTestDateDatePicker.getValue();
		if (testDate == null) {
			Popups.errorAlert("Invalid date format.");
			return;
		}
		
		LocalDate resultDate = testsResultDateDatePicker.getValue();
		LocalDate retestDate = testsRetestDateDatePicker.getValue();
		
		//If a result is given, then resultdate is also required, and vice versa, so check
		if ((resultDate != null && resultValue.isBlank()) || (resultDate == null && !resultValue.isBlank())) {
			Popups.errorAlert("If providing a result, please enter a value for both result fields.");
			return;
		}
		
		//Get animalID
		animalID = getAnimalID(testsAnimalNameTextField.getText());
		if (animalID == 0) {
			return;
		}
		
		//Get recordID
		recordID = getRecordID(testsAnimalNameTextField.getText());
		if (recordID == 0) {
			return;
		}
		
		//Build list, prepare sql statement
		StringBuilder sqlFrontBuilder = new StringBuilder("INSERT INTO Tests (TestA_ID, TestR_ID, TestName, TestDate");
		StringBuilder sqlBackBuilder = new StringBuilder(") VALUES (?, ?, ?, ?");
		
		List<Object> values = new ArrayList<>();
		values.add(animalID);
		values.add(recordID);
		values.add(testsTestNameTextField.getText());
		values.add(testDate);
		if (!resultValue.isBlank()) {
			values.add(resultValue);
			values.add(resultDate);
			sqlFrontBuilder.append(", Result, ResultDate");
			sqlBackBuilder.append(", ?, ?");
		}
		if (retestDate != null) {
			values.add(retestDate);
			sqlFrontBuilder.append(", RetestDate");
			sqlBackBuilder.append(", ?");
		}
		
		sqlFrontBuilder.append(sqlBackBuilder.toString()).append(")");
		
		String sqlStatement = sqlFrontBuilder.toString();
		
		final Future<Integer> submit = executor.submit(new SqlUpdateTask(sqlStatement, values));
		
		try {
			Integer submitResult = submit.get();
			
			if (submitResult != null) {
				Popups.infoAlert("Tests updated successfully, " + submitResult + " rows affected");
			}
			else {
				Popups.errorAlert("Database was unable to be updated");
			}
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}
	//</editor-fold>

    private void updatePersonSubmit() {
	int personID = updatePersonId;
	
	//Get values from all text fields and submit into update statement
	final CheckBox[] checkBoxes = {updatePersonBannedYNCheckBox,
				       updatePersonStaffCheckBox,
				       updatePersonVeterinarianCheckBox,
				       updatePersonFosterCheckBox,
				       updatePersonAdopterCheckBox,
				       updatePersonVolunteerCheckBox,
				       };
	
	final String[] checkboxState = new String[checkBoxes.length];
	
	boolean checkboxSelected = false;
		
		for (int i = 0; i < checkBoxes.length; i++) {
			//store checkbox state
			if((checkBoxes[i] == updatePersonBannedYNCheckBox)
				&& checkBoxes[i].isSelected()) {
			    checkboxState[i] = "Y";
			}
			else if((checkBoxes[i] == updatePersonBannedYNCheckBox)
				&& !checkBoxes[i].isSelected()) {
			    checkboxState[i] = "N";
			}
			else if(checkBoxes[i].isSelected() && !checkBoxes[i].isDisable()) {
				//checkbox is selected, change state to true
				checkboxState[i] = "1";
				checkboxSelected = true;
			}
			else {
				//checkbox is not selected, change state to false
				checkboxState[i] = "0";
			}
		}
	
	//Check subBoxes
	CheckBox[] subBox = {updatePersonStaffAdministratorSubBox,
	                     updatePersonStaffCoordinatorSubBox,
	                     updatePersonStaffGeneralSubBox};

	String[] subBoxState = new String[subBox.length];
	
	for (int i=0;i<subBox.length;i++) {
	    if (subBox[i].isSelected()) {
		subBoxState[i] = "1";
	    }
	    else {
		subBoxState[i] = "0";
	    }
	}
	//No checkboxes were checked, display alert and return
	if(!checkboxSelected) {
		Popups.errorAlert("Please check at least one box.");
		return;
	}
	
	//Validate text fields
	final TextField[] textFields = {updatePersonEmailTextField,
					updatePersonAddressTextField,
					updatePersonCityTextField,
					updatePersonZIPTextField,
					updatePersonFosterPreferencesTextField};
	
	    for (TextField textField : textFields) {
		    if ((textField == newPersonEmailTextField)
				    && (!newPersonEmailTextField.getText().matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:"
						                                                   + ".[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
						                                                   + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])"
						                                                   + "?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"))) { //regex for proper email format
			
			    Popups.errorAlert("Email must be in a proper format.");
			    return;
		    }
		    else if (!textField.isDisable() && textField.getText().isEmpty()) {
			    Popups.errorAlert("Please fill in all required fields.");
			    return;
		    }
	    }
	//Validate phone number
	if (!updatePersonPhoneTextField.getText().isBlank() && updatePersonPhoneTextField.getText().length() < 10) {
		Popups.errorAlert("Phone number must be 10 digits");
		return;
	}
	
	//Validate choice boxes/combo boxes
	if (updatePersonStateComboBox.getValue().isEmpty()) {
	    Popups.errorAlert("Please fill in all fields");
	    return;
	}
	
	if (!updatePersonVolunteerLevelChoiceBox.isDisable()
		&& updatePersonVolunteerLevelChoiceBox.getValue().isEmpty()) {
	    Popups.errorAlert("Please fill in all fields.");
	    return;
	}
	
	//Update person with current values
	String address= updatePersonAddressTextField.getText() + ", " + updatePersonCityTextField.getText() + ", "
		      + updatePersonStateComboBox.getValue() + ", " + updatePersonZIPTextField.getText(); //collate address strings
	
	StringBuilder sqlBuilder = new StringBuilder("UPDATE Person SET Address=?, Email=?,"
		+ " BannedYN=?, StaffFlag=?, VetFlag=?, FosterFlag=?, AdopterFlag=?, VolFlag=?");
	
	List<Object> values = new ArrayList<>();
	values.add(address);
	values.add(updatePersonEmailTextField.getText());
	//add flags
	    Collections.addAll(values, checkboxState);
	
	//preference/vol level
	if (updatePersonFosterCheckBox.isSelected()) {
	    sqlBuilder.append(", Preference=?");
	    values.add(updatePersonFosterPreferencesTextField.getText());
	}
	
	if (updatePersonVolunteerCheckBox.isSelected()) {
	    sqlBuilder.append(", VolLevel=?");
	    values.add(updatePersonVolunteerLevelChoiceBox.getValue());
	}
	
	values.add(personID);
	sqlBuilder.append(" WHERE P_ID=?");
	String sqlStatement = sqlBuilder.toString();
	
	final Future<Integer> personSubmit = executor.submit(
			new SqlUpdateTask(sqlStatement, values));
	
	try {
	    Integer submitResult = personSubmit.get();
	    if (submitResult != null) {
		    Popups.infoAlert("Person updated successfully, " + submitResult + " rows affected");
	    }
	    else {
		    Popups.errorAlert("Database was unable to be updated");
	    }
	    }
	    catch (ExecutionException e) {
		    e.printStackTrace();
		    Popups.errorAlert(e.getMessage());
	    }
	    catch (InterruptedException e) {
		    e.printStackTrace();
		    Popups.errorAlert(e.getMessage());
		    Thread.currentThread().interrupt();
	    }
	
	//update associations
	//if new staff, then insert new value
	    if (updatePersonStaffCheckBox.isSelected()) {
		    for (int i=0;i<3;i++) {
			    if (!(subBoxState[i].equals(updateAssociationSelected[i])) && !(subBoxState[i].equals("0") && updateAssociationSelected[i].equals("1"))) {
				
				    List<Object> associationsValues = new ArrayList<>();
				    associationsValues.add(associationNames[i]);
				    associationsValues.add(personID);
				
				
				    final Future<Integer> associationSubmit = executor.submit(
						    new SqlUpdateTask("INSERT INTO Associations (Association, AssocP_ID) VALUES (?, ?)", associationsValues));
				
				    try {
					    Integer submitResult = associationSubmit.get();
					    if (submitResult != null) {
						    Popups.infoAlert("Associations updated successfully, " + submitResult + " rows affected");
					    }
					    else {
						    Popups.errorAlert("Database was unable to be updated");
					    }
				    }
				    catch (ExecutionException e) {
					    e.printStackTrace();
					    Popups.errorAlert(e.getMessage());
				    }
				    catch (InterruptedException e) {
					    e.printStackTrace();
					    Popups.errorAlert(e.getMessage());
					    Thread.currentThread().interrupt();
				    }
			    }
		    }
	    }
	//insert new phone
	if (!updatePersonPhoneTextField.getText().isEmpty()) {
	    //build phone
	    String phoneNum = buildPhone(updatePersonPhoneTextField.getText());
	    
	    List<Object> phoneValues = new ArrayList<>();
	    phoneValues.add(personID);
	    phoneValues.add(phoneNum);
	    
	    final Future<Integer> phoneSubmit = executor.submit(
			    new SqlUpdateTask("INSERT INTO Phone_Numbers (Person_ID, PhoneNum) VALUES (?, ?)", phoneValues));
	    
	    try {
		Integer submitResult = phoneSubmit.get();
		    if (submitResult != null) {
			Popups.infoAlert("Phone_Numbers updated successfully, " + submitResult + " rows affected");
		    }
		    else {
			Popups.errorAlert("Database was unable to be updated");
		    }
		}
		catch (ExecutionException e) {
		    e.printStackTrace();
		    Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}
	
	//insert vol locations if field has text
	if (updatePersonVolunteerCheckBox.isSelected() && !updatePersonVolunteerLocationTextField.getText().isEmpty()) {
	    //get location id from name
	    int locationID = getLocationID(updatePersonVolunteerLocationTextField.getText());
	    if (locationID == 0) {
		return;
	    }
	    
	    List<Object> volunteerValues = new ArrayList<>();
	    volunteerValues.add(personID);
	    volunteerValues.add(locationID);
	    
	    final Future<Integer> volunteerSubmit = executor.submit(
			    new SqlUpdateTask("INSERT INTO Vol_At (Vol_ID, VolLoc_ID) VALUES (?, ?)", volunteerValues));
	    
	    try {
		Integer submitResult = volunteerSubmit.get();
		if (submitResult != null) {
			Popups.infoAlert("Vol_At updated successfully, " + submitResult + " rows affected");
		}
		else {
			Popups.errorAlert("Database was unable to be updated");
		    }
		}
		catch (ExecutionException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			Popups.errorAlert(e.getMessage());
			Thread.currentThread().interrupt();
		    }
		}
	    }
    
    private void holdSubmit(ActionEvent event) {
	int animalID;
	
	//hold end reason may be null
	TextField[] textFields = {holdReasonTextField,
				  holdAnimalNameTextField};
	
	if (!isTextFieldsValid(textFields)) {
	    return;
	}
	
	//fields are valid, get animalID
	animalID = getAnimalID(holdAnimalNameTextField.getText());
	if (animalID == 0) {
	    return;
	}
	
	//get values from datepickers, hold end may be null
	LocalDate startDate = holdStartDateDatePicker.getValue();
	LocalDate reviewDate = holdReviewDateDatePicker.getValue();
	LocalDate endDate = holdEndDateDatePicker.getValue();
	
	if (startDate == null || reviewDate == null) {
	    Popups.errorAlert("Please enter a date value.");
	}
	
	//enddate and endreason require each other
	if (((endDate != null) && (holdEndReasonTextField.getText().isBlank()))
	  || ((endDate == null) && (!holdEndReasonTextField.getText().isBlank()))) {
	    Popups.errorAlert("Hold end date requires a hold end reason.");
	    return;
	}
	
	
	if (reviewDate.isBefore(startDate)) {
	    Popups.errorAlert("Start Date cannot be after End Date/Review Date");
	    return;
	}
	
	if((endDate != null)) {
	    if (endDate.isBefore(startDate)) {
		Popups.errorAlert("Start Date cannot be after End Date/Review Date");
		return;
	    }
	}
	
	//insert values into db
	List<Object> values = new ArrayList<>();
	StringBuilder sqlFrontBuilder = new StringBuilder("INSERT INTO Hold (HoldA_ID, StartDate, HoldReason, ReviewDate");
	StringBuilder sqlBackBuilder = new StringBuilder(") VALUES (?, ?, ?, ?");
	values.add(animalID);
	values.add(startDate);
	values.add(holdReasonTextField.getText());
	values.add(reviewDate);
	
	
	if (endDate != null) {
	    sqlFrontBuilder.append(", EndDate, ReleaseReason");
	    sqlBackBuilder.append(", ?, ?");
	    values.add(endDate);
	    values.add(holdEndReasonTextField.getText());
	}
	
	sqlBackBuilder.append(")");
	sqlFrontBuilder.append(sqlBackBuilder.toString());
	
	String sqlStatement = sqlFrontBuilder.toString();
	System.out.println(sqlStatement);
	
	final Future<Integer> submit = executor.submit(
			new SqlUpdateTask(sqlStatement, values));
	
	try {
	    Integer submitResult = submit.get();
	    if (submitResult != null) {
		Popups.infoAlert("Hold updated successfully, " + submitResult + " rows affected");
	    }
	    else {
		    Popups.errorAlert("Database was unable to be updated");
		}
	    }
	    catch (ExecutionException e) {
		    e.printStackTrace();
		    Popups.errorAlert(e.getMessage());
	    }
	    catch (InterruptedException e) {
		    e.printStackTrace();
		    Popups.errorAlert(e.getMessage());
		    Thread.currentThread().interrupt();
	    }

    }
}
