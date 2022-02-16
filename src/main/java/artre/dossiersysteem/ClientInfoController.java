package artre.dossiersysteem;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import artre.dossiersysteem.FileSystem.ReadFile;
import artre.dossiersysteem.FileSystem.WriteFile;
import artre.dossiersysteem.Models.Client;
import artre.dossiersysteem.Models.Clientdata;
import artre.dossiersysteem.Models.Document;
import artre.dossiersysteem.Models.Goal;
import artre.dossiersysteem.Models.Log;
import artre.dossiersysteem.Models.LogList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientInfoController {
	private WriteFile writeFile = new WriteFile();
	private ReadFile readFile = new ReadFile();

	private Client client = new Client();
	private Clientdata clientdata;
	private LogList logList = new LogList();

	@FXML
	private Label titleClientNr, titleClientName, warningLbl;
	@FXML
	private TextField clientNrTxt, firstNameTxt, lastNameTxt, adressTxt, locationTxt, postalcodeTxt;
	@FXML
	private TextArea goalTxt;
	@FXML
	private Accordion logsAccordion, docsAccordion;

	@FXML
	private void switchToSelectClient() throws IOException {
		App.setRoot("SelectClient");
	}

	@FXML
	private void switchToEmptyClientInfo() throws IOException {
		App.setRoot("ClientInfo");
	}
	
	@FXML
	private void switchToManagement() throws IOException {
		App.setRoot("Management");
	}

	private String getDateString() {
		ZonedDateTime dateTime = ZonedDateTime.now();
		return dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}
	
	private String showableDateString(String dateString) {
		ZonedDateTime dateTime = ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		String newDateString = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm:ss"));
		return newDateString;
	}

	private boolean checkHasClientNr() {
		if (!clientNrTxt.getText().trim().isEmpty()) {
			if (clientNrTxt.getText().matches("[0-9]+")) {
				return true;
			} else {
				// TODO geef waarschuwing dat client nummer alleen nummers mag bevatten
				System.out.println("Cliënt nummer mag alleen nummers bevatten!");
				warningLbl.setText("Cliënt nummer mag alleen nummers bevatten!");
				warningLbl.setVisible(true);
			}
		} else {
			// TODO geef waarschuwing dat client nummer niet leeg mag zijn
			System.out.println("Cliënt nummer mag niet leeg zijn!");
			warningLbl.setText("Cliënt nummer mag niet leeg zijn!");
			warningLbl.setVisible(true);
		}
		return false;
	}

	private void saveClient() {
		client.setLastEditDate(getDateString());
		writeFile.WriteMainJsonFile(clientdata.getClientNr(), client);
		
		warningLbl.setText("Cliënt dossier opgeslagen!");
		warningLbl.setVisible(true);
	}

	@FXML
	private void saveClientdata() throws IOException {
		if (checkHasClientNr()) {
			clientdata = new Clientdata();
			clientdata.setClientNr(clientNrTxt.getText());
			clientdata.setFirstName(firstNameTxt.getText());
			clientdata.setLastName(lastNameTxt.getText());
			clientdata.setAdress(adressTxt.getText());
			clientdata.setLocation(locationTxt.getText());
			clientdata.setPostalcode(postalcodeTxt.getText());
			clientdata.setLastEditDate(getDateString());

			titleClientNr.setText(clientdata.getClientNr());
			titleClientName.setText(clientdata.getFirstName() + " " + clientdata.getLastName());

			List<String> employeeList = new ArrayList<String>();
			employeeList.add(App.userEnum);
			client.setSecondaryEmployees(employeeList);
			client.setPrimaryEmployee(App.userEnum);
			client.setClientdata(clientdata);
			saveClient();
		}
	}

	@FXML
	private void saveGoaltext() throws IOException {
		if (checkHasClientNr()) {
			Goal goal = new Goal();
			goal.setGoalText(goalTxt.getText());
			goal.setLastEditDate(getDateString());

			client.setGoalsFile(writeFile.WriteJsonFile(clientdata.getClientNr(), goal, "goal"));
			saveClient();
		}
	}

	@FXML
	private void openUploadDocumentScene() {
        try {
        	FXMLLoader loader = new FXMLLoader(App.class.getResource("UploadDocument.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Upload Document");
            
            UploadDocumentController controller = loader.getController();
            controller.setClient(client);
            
            stage.setScene(new Scene(root, 450.0, 419.0));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}

	private void loadClient() throws IOException {
		if (client.getClientdata() != null) {
			loadClientdata();
		}
		if (client.getGoalsFile() != null) {
			loadGoal();
		}
		if (client.getLogsFile() != null) {
			loadLogs();
		}
		if (client.getDocuments() != null) {
			loadDocuments();
		}
	}

	@FXML
	private void loadClientdata() throws IOException {
		System.out.println("Load Clientdata!");
		clientdata = client.getClientdata();
		// Load Contact details
		clientNrTxt.setText(clientdata.getClientNr());
		firstNameTxt.setText(clientdata.getFirstName());
		lastNameTxt.setText(clientdata.getLastName());
		adressTxt.setText(clientdata.getAdress());
		locationTxt.setText(clientdata.getLocation());
		postalcodeTxt.setText(clientdata.getPostalcode());

		titleClientNr.setText(clientdata.getClientNr());
		titleClientName.setText(clientdata.getFirstName() + " " + clientdata.getLastName());
	}

	@FXML
	private void loadGoal() {
		Goal goal = readFile.readGoalJsonFile(client.getGoalsFile());
		goalTxt.setText(goal.getGoalText());
	}

	@FXML
	private void newLog() {
		addLog(null);
	}
	
	@FXML
	private void addLog(Log log) {
		String title = "Nieuwe Log";
		
		AnchorPane newPanelContent = new AnchorPane();
		final TextArea area = new TextArea();
		area.setPrefColumnCount(15);
		area.setPrefHeight(300);
		area.setPrefWidth(500);

		Button button = new Button();
		button.setText("Opslaan");

		newPanelContent.getChildren().add(area);
		newPanelContent.setTopAnchor(area, 20.0);
		newPanelContent.getChildren().add(button);
		
		if(log != null) {
			title = showableDateString(log.getLastEditDate());
			area.setText(log.getLogText());
		}
		
		final TitledPane pane = new TitledPane(title, newPanelContent);
		logsAccordion.getPanes().add(pane);
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (checkHasClientNr()) {
					String date = getDateString();
					Log log = new Log(area.getText(), date);
					logList.addToLogList(log);
					logList.setLastEditDate(date);
					client.setLogsFile(writeFile.WriteJsonFile(clientdata.getClientNr(), logList, "logs"));
					saveClient();
					pane.setText(showableDateString(date));
				}
			}
		};
		button.setOnAction(event);
	}

	@FXML
	private void loadLogs() {
		LogList logList = readFile.readLogsJsonFile(client.getLogsFile());
		for(Log log : logList.getLogList()) {
			addLog(log);
		}
	}

	@FXML
	private void loadDocuments() {
		for(String docFile : client.getDocuments()) {
			Document document = readFile.readDocJsonFile(docFile);
			addDocument(document);
		}
	}
	
	@FXML
	private void addDocument(final Document doc) {
		AnchorPane newPanelContent = new AnchorPane();
		TextArea area = new TextArea();
		area.setPrefColumnCount(15);
		area.setPrefHeight(300);
		area.setPrefWidth(500);
		area.setEditable(false);
		area.setText(doc.getDescription());

		Button button = new Button();
		button.setText("Download");

		newPanelContent.getChildren().add(area);
		newPanelContent.setTopAnchor(area, 20.0);
		newPanelContent.getChildren().add(button);
		
		TitledPane pane = new TitledPane(doc.getDocName() + "    |    " + showableDateString(doc.getUploadDate()), newPanelContent);
		docsAccordion.getPanes().add(pane);
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Download Document!");
				writeFile.downloadDocument(doc);
			}
		};
		button.setOnAction(event);
	}

	public void setClient(Client client) {
		this.client = client;
		try {
			loadClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
