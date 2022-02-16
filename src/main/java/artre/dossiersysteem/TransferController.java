package artre.dossiersysteem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

import artre.dossiersysteem.FileSystem.ReadFile;
import artre.dossiersysteem.FileSystem.WriteFile;
import artre.dossiersysteem.Models.Client;
import artre.dossiersysteem.Models.Document;
import artre.dossiersysteem.Models.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class TransferController implements Initializable {
	private File file = null;
	private Document document = new Document();
	private ReadFile readFile = new ReadFile();
	private WriteFile writeFile = new WriteFile();

	@FXML
	private Label fileNameLbl, warningLbl;
	@FXML
	private TextField clientNrTxt;
	@FXML
	private ChoiceBox<String> cbWorker;

	public void initialize(URL arg0, ResourceBundle arg1) {
		for (Users.User user : Users.User.values()) {
			if (!user.toString().equals(App.userEnum)) {
				cbWorker.getItems().add(user.toString());
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
	private void selectFile() {
		FileChooser fileChooser = new FileChooser();
		file = fileChooser.showOpenDialog(null);
		if (file != null) {
			fileNameLbl.setText(file.getName());
		}
	}

	private String encodeFileToBase64Binary() {
		byte[] fileContent = null;
		try {
			fileContent = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(fileContent);
	}

	private String getDateString() {
		ZonedDateTime dateTime = ZonedDateTime.now();
		return dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

	@FXML
	private void confirmTransfer() {
		if (!clientNrTxt.getText().trim().isEmpty() && clientNrTxt.getText().matches("[0-9]+")) {
			if (file != null) {
				if (!cbWorker.getSelectionModel().isEmpty()) {
					Client client = readFile.readMainJsonFile(clientNrTxt.getText());
					if (client != null) {
						if (client.getPrimaryEmployee().equals(App.userEnum)) {
							document.setDocName(file.getName());
							document.setDescription(
									"Handtekening cliënt voor bevestiging overdraging naar andere therapeut.");
							document.setUploadDate(getDateString());
							document.setContent(encodeFileToBase64Binary());
							document.setDocType(file.getName().substring(file.getName().lastIndexOf('.') + 1));

							String docFile = writeFile.WriteJsonFile(client.getClientdata().getClientNr(), document,
									"doc");
							List<String> docList = client.getDocuments();
							if (docList == null) {
								docList = new ArrayList<String>();
							}
							docList.add(docFile);
							client.setDocuments(docList);

							client.setLastEditDate(getDateString());
							List<String> employeeList = new ArrayList<String>();
							employeeList.add(cbWorker.getSelectionModel().getSelectedItem());
							client.setSecondaryEmployees(employeeList);
							client.setPrimaryEmployee(App.userEnum);
							writeFile.WriteMainJsonFile(client.getClientdata().getClientNr(), client);
							
							warningLbl.setText("Cliënt dossier overgedragen!");
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
						warningLbl.setText("Geen cliënt dossier gevonden!");
						warningLbl.setVisible(true);
					}
				} else {
					// TODO: Error message no new employee selected
					System.out.println("No new employee selected");
					warningLbl.setText("Geen nieuwe medewerker gekozen!");
					warningLbl.setVisible(true);
				}
			} else {
				// TODO: Error message file not selected
				System.out.println("File not selected");
				warningLbl.setText("Geen bestand geselecteerd!");
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
