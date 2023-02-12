package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import classes.Livre;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;


public class BooksController implements Initializable {
	
	private Stage stage;
	 private Scene scene;
	 private Parent root;
	 
	 /*********************   Books Fields & Attributes   *********************/
	 
	 @FXML
		private TextField titleField;
	 @FXML
		private TextField authorField;
	 @FXML
		private TextField codeField;
	 @FXML
		private TextField isbnField;
	 @FXML
		private ChoiceBox<String> categoryBox = new ChoiceBox<String>();
	 	private String[] categories = {"Enigme Policiere","Romance","Sciences Fiction","Autre"} ;
	 	private String chosenCategory;
	 	
	 @FXML
	 	private Label errorMessage;
	 	
	 @FXML
		private TextField searchField;
	 	
	 	/*********************   Books Table   *********************/
	 	
	 @FXML
	 	private TableView<Livre> booksTable = new TableView<>();
	 @FXML
	 	private TableColumn<Livre, String> titleColumn = new TableColumn<>();
	 @FXML
	 	private TableColumn<Livre, String> authorColumn = new TableColumn<>();
	 @FXML
	 	private TableColumn<Livre, Long> codeColumn = new TableColumn<>();
	 @FXML
	 	private TableColumn<Livre, Long> isbnColumn = new TableColumn<>();
	 @FXML
	 	private TableColumn<Livre, String> categoryColumn = new TableColumn<>();
	 
	 
	 /*********************   Initialize   *********************/
	 public String getCategory(ActionEvent event) {
			chosenCategory = categoryBox.getValue();
			return chosenCategory;
		}
	 
	 @Override
		public void initialize(URL url, ResourceBundle resourceBundle) {
		 //Books
		 	categoryBox.getItems().addAll(categories);
			categoryBox.setOnAction(this::getCategory);
			titleColumn.setCellValueFactory(new PropertyValueFactory<Livre, String>("Titre"));
			authorColumn.setCellValueFactory(new PropertyValueFactory<Livre, String>("Auteur"));
			codeColumn.setCellValueFactory(new PropertyValueFactory<Livre, Long>("Code"));
			isbnColumn.setCellValueFactory(new PropertyValueFactory<Livre, Long>("ISBN"));
			categoryColumn.setCellValueFactory(new PropertyValueFactory<Livre, String>("Categorie"));
			showBooks();
		}
	
	/*********************   Switching Scene ( Back to Home )   *********************/
	 
	public void backToHome(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Home.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}
	
	/*********************   On Action Functions   *********************/
	
	public int mouseSelectedRow() {
		errorMessage.setText("");
		int selectedRow = booksTable.getSelectionModel().getSelectedIndex();
		if(selectedRow < 0) {return 0;}
		Livre selLivre = booksTable.getItems().get(selectedRow);
		titleField.setText(selLivre.getTitre());
		authorField.setText(selLivre.getAuteur());
		codeField.setText(Long.toString(selLivre.getCode()));
		isbnField.setText(Long.toString(selLivre.getISBN()));
		categoryBox.setValue(selLivre.getCategorie());
		
		return 1; //Success
	}
	
	public void refresh(ActionEvent event) {
		errorMessage.setText("");
		showBooks();
		titleField.setText("");
		authorField.setText("");
		codeField.setText("");
		isbnField.setText("");
		categoryBox.setValue(null);;
	}
	
	public void clear(ActionEvent event) {
		errorMessage.setText("");
		searchField.setText(null);
		showBooks();
	}
	
	/*********************   Verification Functions   *********************/
	
	public void fieldsFilledCorrectly() throws TypingException {
		errorMessage.setText("");
		if(titleField.getText()=="") {
			throw new TypingException("Saisir le titre",errorMessage);
		}else if( authorField.getText() == "") {
			throw new TypingException("Saisir l'auteur",errorMessage);
		}
		try {
			Long.parseLong(codeField.getText());
		}catch(Exception e) {
			throw new TypingException("Code invalide",errorMessage);
		}
		try {
			Long.parseLong(isbnField.getText());
		}catch(Exception e) {
			throw new TypingException("ISBN invalide",errorMessage);
		}
		try {
			chosenCategory.getBytes();
		}catch(Exception e) {
			throw new TypingException("Choisir une categorie",errorMessage);
		}
	}
	
	/*********************   Database Manipulation   *********************/
	
	public int addBookToDatabase(ActionEvent event) {
		try {
			fieldsFilledCorrectly();
			Livre book = new Livre(titleField.getText(),authorField.getText(),Long.parseLong(codeField.getText()),Long.parseLong(isbnField.getText()), chosenCategory);
			Connection myConnection = new DatabaseConnection().getConnection();
		 	String insertQuery = "insert into livre (titre,auteur,code,ISBN,categorie) values (?,?,?,?,?);";
			PreparedStatement pst = myConnection.prepareStatement(insertQuery);
			pst.setString(1, book.getTitre() );
			pst.setString(2,book.getAuteur() );
			pst.setLong(3, book.getCode() );
			pst.setLong(4, book.getISBN() );
			pst.setString(5, book.getCategorie() );
			pst.executeUpdate();
			myConnection.close();
			showBooks();
			Alert exists = new Alert(AlertType.INFORMATION);
			exists.setTitle("Information");
			exists.setHeaderText("Le livre a été ajouté avec succès");
			exists.setContentText("Le livre est maintenant disponible dans la base de données");
			exists.show();
			
		}catch(TypingException et) {
			return 0;
		}
		catch(Exception e) {
			Alert exists = new Alert(AlertType.WARNING);
			exists.setTitle("Information");
			exists.setHeaderText("Le code du livre existe déjà");
			exists.setContentText("Le code d'un livre doit être unique");
			exists.show();
		} 
		return 1;
	}
	
	public int modifyBook(ActionEvent event) {
		errorMessage.setText("");
		try {
			fieldsFilledCorrectly();
		} catch (TypingException et) {
			return 0;
		}
		try {
			Connection myConnection = new DatabaseConnection().getConnection();
		 	String insertQuery = "update livre set titre=?, auteur=?, ISBN=?, categorie=? where code=?;";
			PreparedStatement pst = myConnection.prepareStatement(insertQuery);
			pst.setString(1, titleField.getText() );
			pst.setString(2,authorField.getText() );
			pst.setLong(3, Long.parseLong(isbnField.getText()));
			pst.setString(4, getCategory(event) );
			pst.setLong(5, Long.parseLong(codeField.getText()) );
			pst.executeUpdate();
			myConnection.close();
			showBooks();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		} 
	return 1;
	}
	
	public void deleteBookFromDatabase(ActionEvent event) {
		errorMessage.setText("");
		try {
			Connection myConnection = new DatabaseConnection().getConnection();
		 	String deleteQuery = "delete from livre where code=?;";
			PreparedStatement pst = myConnection.prepareStatement(deleteQuery);
			pst.setLong(1, Long.parseLong(codeField.getText()) );
			//Update Borrowings
			String brQuery = "delete from emprunter where code=?;";
			PreparedStatement brpst = myConnection.prepareStatement(brQuery);
			brpst.setLong(1, Long.parseLong(codeField.getText()) );
			//Deletion Confirmation
			Alert ask = new Alert(Alert.AlertType.CONFIRMATION);
			ask.setTitle("Suppression");
			ask.setHeaderText("Suppression du livre");
			ask.setContentText("Le livre sera supprimé de la base de données");
			Optional<ButtonType> result = ask.showAndWait();
			if(result.get() == ButtonType.OK) {
				pst.executeUpdate();
				brpst.executeUpdate();
			}
			myConnection.close();
			showBooks();
		}catch(Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alerte");
			alert.setHeaderText("Suppression invalide");
			alert.setContentText("Veuillez cliquer sur le livre que vous souhaitez supprimer");
			alert.show();
		} 
	}
	
	public void showBooks() {
		try {
			ObservableList<Livre> livres = booksTable.getItems();
			livres.removeAll(livres);
			Connection myConnection = new DatabaseConnection().getConnection();
			String rq = "select * from livre;";
			Statement st = myConnection.createStatement(); ;
			ResultSet rs =st.executeQuery(rq);
			while(rs.next()) {
				livres.add(new Livre(rs.getString("Titre"),rs.getString("Auteur"),rs.getLong("Code"),rs.getLong("ISBN"),rs.getString("Categorie") ));
			}
			booksTable.setItems(livres);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void filterBooks(javafx.scene.input.KeyEvent event) {
		String typed = searchField.getText();
		try {
			ObservableList<Livre> livres = booksTable.getItems();
			livres.removeAll(livres);
			Connection myConnection = new DatabaseConnection().getConnection();
			String rq = "select * from livre where titre like '%"+typed+"%'";
			rq = rq + "or auteur like '%"+typed+"%'";
			rq = rq + "or code like '%"+typed+"%'";
			rq = rq + "or ISBN like '%"+typed+"%'";
			rq = rq + "or categorie like '%"+typed+"%'";
			Statement st = myConnection.createStatement(); ;
			ResultSet rs =st.executeQuery(rq);
			while(rs.next()) {
				livres.add(new Livre(rs.getString("Titre"),rs.getString("Auteur"),rs.getLong("Code"),rs.getLong("ISBN"),rs.getString("Categorie") ));
			}
			booksTable.setItems(livres);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}	
	}
	
	
	
// End of BooksController
}
