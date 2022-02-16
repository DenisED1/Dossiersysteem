package artre.dossiersysteem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import artre.dossiersysteem.FileSystem.ReadFile;
import artre.dossiersysteem.FileSystem.WriteFile;
import artre.dossiersysteem.Models.Client;
import artre.dossiersysteem.Models.Document;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class TakeoverController {
	private File file = null;
	private Document document = new Document();
	private ReadFile readFile = new ReadFile();
	private WriteFile writeFile = new WriteFile();

	@FXML
	private Label fileNameLbl, warningLbl;
	@FXML
	private TextField clientNrTxt;

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
	private void confirmTakeover() {
		if (!clientNrTxt.getText().trim().isEmpty() && clientNrTxt.getText().matches("[0-9]+")) {
			if (file != null) {
				Client client = readFile.readMainJsonFile(clientNrTxt.getText());
				if (client != null) {
					document.setDocName(file.getName());
					document.setDescription("Handtekening cliënt voor bevestiging overname naar andere therapeut.");
					document.setUploadDate(getDateString());
					document.setContent(encodeFileToBase64Binary());
					document.setDocType(file.getName().substring(file.getName().lastIndexOf('.') + 1));

					String docFile = writeFile.WriteJsonFile(client.getClientdata().getClientNr(), document, "doc");
					List<String> docList = client.getDocuments();
					if (docList == null) {
						docList = new ArrayList<String>();
					}
					docList.add(docFile);
					client.setDocuments(docList);

					client.setLastEditDate(getDateString());
					List<String> employeeList = new ArrayList<String>();
					employeeList.add(App.userEnum);
					client.setSecondaryEmployees(employeeList);
					client.setPrimaryEmployee(App.userEnum);
					writeFile.WriteMainJsonFile(client.getClientdata().getClientNr(), client);
					
					warningLbl.setText("Dossier overgenomen!");
					warningLbl.setVisible(true);
				} else {
					// TODO: Error message no client found
					System.out.println("No client found");
					warningLbl.setText("Geen dossier gevonden met gegeven cliënt nummer!");
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
