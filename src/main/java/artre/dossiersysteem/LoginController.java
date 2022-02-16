package artre.dossiersysteem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import artre.dossiersysteem.FileSystem.ReadFile;
import artre.dossiersysteem.Models.Client;
import artre.dossiersysteem.Models.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	@FXML
	private Label warningLbl;
	@FXML
	private TextField usernameTxt;
	@FXML
	private PasswordField passwordTxt;
	
	@FXML
	private void login() throws IOException {
		checkDirectorys();
		for (Users.User user : Users.User.values()) {
	        if (user.name().equals(usernameTxt.getText())) {
	        	System.out.println("Login!");
	            App.userEnum = usernameTxt.getText();
	            App.setRoot("SelectClient");
	        } else {
	        	warningLbl.setText("Gebruikersnaam of wachtwoord niet correct!");
	        	warningLbl.setVisible(true);
	        }
		}
	}
	
	private void checkDirectorys() {
		String path = System.getProperty("user.home") + File.separator + "ArtreDossierSystem";
		File customDir = new File(path);
		if (customDir.exists() || customDir.mkdirs()) {
		    // Path either exists or was created
			App.homePath = path + File.separator;
			System.out.println("Path bestaat of is aangemaakt");
		} else {
		    // The path could not be created for some reason
			System.out.println("Path kon niet gemaakt worden");
		}
	}
}
