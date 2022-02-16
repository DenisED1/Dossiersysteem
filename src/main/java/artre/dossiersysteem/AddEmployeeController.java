package artre.dossiersysteem;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import artre.dossiersysteem.FileSystem.ReadFile;
import artre.dossiersysteem.FileSystem.WriteFile;
import artre.dossiersysteem.Models.Client;
import artre.dossiersysteem.Models.Users;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AddEmployeeController implements Initializable {
	private ReadFile readFile = new ReadFile();
	private WriteFile writeFile = new WriteFile();

	@FXML
	private Label warningLbl;
	@FXML
	private TextField clientNrTxt;
	@FXML
	private ListView<String> lvEmployee, lvAdded;

	public void initialize(URL arg0, ResourceBundle arg1) {
		for (Users.User user : Users.User.values()) {
			if (!user.toString().equals(App.userEnum)) {
				lvEmployee.getItems().add(user.toString());
			}
		}
	}

	@FXML
	private void switchToTransfer() throws IOException {
		App.setRoot("Transfer");
	}

	@FXML
	private void switchToTakeover() throws IOException {
		App.setRoot("Takeover");
	}

	@FXML
	private void switchToAddEmployee() throws IOException {
		App.setRoot("AddEmployee");
	}

	@FXML
	private void switchToSelectClient() throws IOException {
		App.setRoot("SelectClient");
	}

	@FXML
	private void addEmployee() {
		lvAdded.getItems().add(lvEmployee.getSelectionModel().getSelectedItem());
	}

	@FXML
	private void removeEmployee() {
		lvAdded.getItems().remove(lvAdded.getSelectionModel().getSelectedItem());
	}

	private String getDateString() {
		ZonedDateTime dateTime = ZonedDateTime.now();
		return dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

	@FXML
	private void confirmAddEmployee() {
		if (!clientNrTxt.getText().trim().isEmpty() && clientNrTxt.getText().matches("[0-9]+")) {
			if (!lvAdded.getItems().isEmpty()) {
				for (String employee : lvAdded.getItems()) {
					Client client = readFile.readMainJsonFile(clientNrTxt.getText());
					if (client != null) {
						if (client.getPrimaryEmployee().equals(App.userEnum)) {
							List<String> employeeList = client.getSecondaryEmployees();
							employeeList.add(employee);
							client.setSecondaryEmployees(employeeList);
							client.setLastEditDate(getDateString());
							writeFile.WriteMainJsonFile(client.getClientdata().getClientNr(), client);
							
							warningLbl.setText("Medewerkers gekoppeld!");
							warningLbl.setVisible(true);
						} else {
							// TODO: Error message not your client
							System.out.println("Not your client");
							warningLbl.setText("Geen toegang tot deze cliënt!");
							warningLbl.setVisible(true);
						}
					} else {
						// TODO: Error message no client found
						System.out.println("No client found");
						warningLbl.setText("Geen cliënt gevonden!");
						warningLbl.setVisible(true);
					}
				}
			} else {
				// TODO: Error message no employees added
				System.out.println("No employees added");
				warningLbl.setText("Geen medewerkers toegevoegd!");
				warningLbl.setVisible(true);
			}
		} else {
			// TODO: Error message clientNr not filled
			System.out.println("ClientNr not filled");
			warningLbl.setText("Cliënt nummer is niet ingevuld!");
			warningLbl.setVisible(true);
		}
	}
}
