package artre.dossiersysteem;

import java.io.IOException;

import javafx.fxml.FXML;

public class ManagementController {
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
}
