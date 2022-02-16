package artre.dossiersysteem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    public static int sceneWidth = 1000;
    public static int sceneHeight = 700;
    public static String userEnum;
    public static String homePath;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Login"), sceneWidth, sceneHeight);
        stage.setTitle("DossierSysteem Artre");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    public static void setSceneRoot(Parent root) {
		scene.setRoot(root);
		
	}
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static FXMLLoader loaderFXML (String fxml) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    	return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }

}