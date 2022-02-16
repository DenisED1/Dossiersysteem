package artre.dossiersysteem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import artre.dossiersysteem.FileSystem.WriteFile;
import artre.dossiersysteem.Models.Client;
import artre.dossiersysteem.Models.Document;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UploadDocumentController {
	private Document document = new Document();
	private WriteFile writeFile = new WriteFile();
	private Client client = new Client();
	private File file = null;
	private Stage stage;

	@FXML
	private TextField fileName;
	@FXML
	private TextArea fileDescription;
	@FXML
	private Label selectedFileName;
	@FXML
	public Button uploadButton;

	private String getDateString() {
		ZonedDateTime dateTime = ZonedDateTime.now();
		return dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

	@FXML
	private void selectDocument() {
		FileChooser fileChooser = new FileChooser();
		file = fileChooser.showOpenDialog(null);
		if (file != null) {
			selectedFileName.setText(file.getName());
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
	
	@FXML
	private void uploadDocument(ActionEvent event) {
		if (file != null) {
			if (fileName.getText().trim().isEmpty()) {
				document.setDocName(file.getName());
			} else {
				document.setDocName(fileName.getText());
			}
			if (!fileDescription.getText().trim().isEmpty()) {
				document.setDescription(fileDescription.getText());
			}
			document.setUploadDate(getDateString());
			document.setContent(encodeFileToBase64Binary());
			document.setDocType(file.getName().substring(file.getName().lastIndexOf('.') + 1));
			
			String docFile = writeFile.WriteJsonFile(client.getClientdata().getClientNr(), document, "doc");
			List<String> docList = client.getDocuments();
			if(docList == null) {
				docList = new ArrayList<String>();
			}
			docList.add(docFile);
			client.setDocuments(docList);
			
			client.setLastEditDate(getDateString());
			writeFile.WriteMainJsonFile(client.getClientdata().getClientNr(), client);
			
			stage = (Stage) uploadButton.getScene().getWindow();
			openClientInfo();
		} else {
			// TODO: Waarschuwing dat er geen bestand is gekozen
			System.out.println("Kies een bestand!");
		}
	}
	private void openClientInfo() {
		try {
			FXMLLoader loader = App.loaderFXML("ClientInfo");
			Parent root = loader.load();

			ClientInfoController controller = loader.getController();
			controller.setClient(client);

			stage.close();
			App.setSceneRoot(root);
		} catch (IOException e) {
			System.err.println(String.format("Error: %s", e.getMessage()));
		}
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
}
