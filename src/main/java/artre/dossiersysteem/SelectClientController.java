package artre.dossiersysteem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import artre.dossiersysteem.FileSystem.ReadFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import artre.dossiersysteem.Models.Client;
import artre.dossiersysteem.Models.Clientdata;
import artre.dossiersysteem.Models.SelectClientData;

public class SelectClientController implements Initializable {
	private ReadFile readFile = new ReadFile();
	private List<Client> clients = new ArrayList<Client>();

	@FXML
	private TextField searchClientNrTxt;
	@FXML
	private TableView<SelectClientData> selectClientTable;
	@FXML
	private Label searchError;

	public void initialize(URL arg0, ResourceBundle arg1) {
		searchError.setVisible(false);
		loadClients("");
	}

	@FXML
	private void searchClient() {
		searchError.setVisible(false);
		if (searchClientNrTxt.getText().matches("[0-9]+") || searchClientNrTxt.getText().trim().isEmpty()) {
			loadClients(searchClientNrTxt.getText());
		} else {
			searchError.setVisible(true);
		}
	}

	@FXML
	private void loadClients(String searchParam) {
		selectClientTable.getItems().clear();
		selectClientTable.getColumns().clear();
		selectClientTable.refresh();

		clients = readFile.readAllClients(searchParam);

		ObservableList<SelectClientData> data = FXCollections.observableArrayList();
		for (Client client : clients) {
			if (client.getSecondaryEmployees() != null) {
				for (String employee : client.getSecondaryEmployees()) {
					if (employee.equals(App.userEnum)) {
						Clientdata clientdata = client.getClientdata();
						data.add(new SelectClientData(clientdata.getClientNr(), clientdata.getFirstName(),
								clientdata.getLastName()));
					}
				}
			}
		}
		
		if (data != null) {
			TableColumn<SelectClientData, String> col_clientNr = new TableColumn<SelectClientData, String>(
					"Cliënt Nummer");
			col_clientNr.setCellValueFactory(new PropertyValueFactory<SelectClientData, String>("clientNr"));

			TableColumn<SelectClientData, String> col_firstName = new TableColumn<SelectClientData, String>("Voornaam");
			col_firstName.setCellValueFactory(new PropertyValueFactory<SelectClientData, String>("firstName"));

			TableColumn<SelectClientData, String> col_lastName = new TableColumn<SelectClientData, String>(
					"Achternaam");
			col_lastName.setCellValueFactory(new PropertyValueFactory<SelectClientData, String>("lastName"));

			selectClientTable.setOnMousePressed(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
						Node node = ((Node) event.getTarget()).getParent();
						TableRow row = null;
						if (node instanceof TableRow) {
							row = (TableRow) node;
						} else if (node.getParent() instanceof TableRow) {
							row = (TableRow) node.getParent();
						}
						if (row != null) {
							SelectClientData rowData = (SelectClientData) row.getItem();
							try {
								openClientInfo(rowData.getClientNr());
							} catch (IOException e) {
								e.printStackTrace();
							}

						}
					}
				}
			});

			selectClientTable.setItems(data);
			selectClientTable.getColumns().addAll(col_clientNr, col_firstName, col_lastName);
		}
	}

	@FXML
	private void switchToClientInfo() throws IOException {
		App.setRoot("ClientInfo");
	}

	@FXML
	private void switchToManagement() throws IOException {
		App.setRoot("Management");
	}

	@FXML
	private void openClientInfo(String clientNr) throws IOException {
		Client client = new Client();
		client = readFile.readMainJsonFile(clientNr);
		try {
			FXMLLoader loader = App.loaderFXML("ClientInfo");
			Parent root = loader.load();

			ClientInfoController controller = loader.getController();
			controller.setClient(client);

			App.setSceneRoot(root);
		} catch (IOException e) {
			System.err.println(String.format("Error: %s", e.getMessage()));
		}
	}
}