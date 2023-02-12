package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class Controller {
	
	private Stage stage;
	 private Scene scene;
	 private Parent root;
	 

	
	/*********************   Switching between Scenes   *********************/
	 
	public void switchToBooks(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Books.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}
	
	public void switchToReaders(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Readers.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}
	
	public void switchToBorrow(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Borrow.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}

	
// End of Controller
}
