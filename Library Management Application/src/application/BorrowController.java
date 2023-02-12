package application;

import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import classes.Emprunt;
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


public class BorrowController implements Initializable {
	
	private Stage stage;
	 private Scene scene;
	 private Parent root;
	 
	 /*********************   Readers Fields & Attributes   *********************/
	 
	 @FXML
		private TextField cinField;
	 @FXML
		private TextField ReaderField; //Auto-complete
	 @FXML
		private TextField codeField;
	 @FXML
		private TextField BookField; //Auto-complete
	 @FXML
		private DatePicker dateField;
	 	
	 	
	 @FXML
		private TextField searchField;
	 	
	 	/*********************   Readers Table   *********************/
	 	
	 @FXML
	 	private TableView<Emprunt> borrwingsTable = new TableView<>();
	 @FXML
	 	private TableColumn<Emprunt, Long> cinColumn = new TableColumn<>();
	 @FXML
	 	private TableColumn<Emprunt, Long> codeColumn = new TableColumn<>();
	 @FXML
	 	private TableColumn<Emprunt, LocalDate> dateColumn = new TableColumn<>();

	 
	 /*********************   Initialize   *********************/
	 
	 @Override
		public void initialize(URL url, ResourceBundle resourceBundle) {
			cinColumn.setCellValueFactory(new PropertyValueFactory<Emprunt, Long>("cinEmprunt"));
			codeColumn.setCellValueFactory(new PropertyValueFactory<Emprunt, Long>("codeEmprunt"));
			dateColumn.setCellValueFactory(new PropertyValueFactory<Emprunt, LocalDate>("dateEmprunt"));
			showBorrowings();
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
		int selectedRow = borrwingsTable.getSelectionModel().getSelectedIndex();
		if(selectedRow < 0) {return 0;}
		Emprunt selEmprunt = borrwingsTable.getItems().get(selectedRow);
		cinField.setText(Long.toString(selEmprunt.getCinEmprunt()));
			//ReaderField.setText(null);
			fetchCin(null);
		codeField.setText(Long.toString(selEmprunt.getCodeEmprunt()));
			//BookField.setText(null);
			fetchCode(null);
		dateField.setValue(selEmprunt.getDateEmprunt());
		return 1; //Success
	}
	
	public void refresh(ActionEvent event) {
		showBorrowings();
		cinField.setText(null);
		ReaderField.setText("Nom du lecteur correspondant");
		codeField.setText(null);
		BookField.setText("Nom du livre correspondant");
		dateField.setValue(null);
	}
	
	public void clear(ActionEvent event) {
		searchField.setText(null);
		showBorrowings();
	}
	
/*********************   Verification Functions   *********************/
	
	public void fieldsFilledCorrectly() throws TypingException {
		Alert alerte = new Alert(AlertType.WARNING);
		alerte.setTitle("Saisie");
		if(fetchCin(null) == false) {
			throw new TypingException("Lecteur inexistant","",alerte);
		}
		if(fetchCode(null) == false) {
			throw new TypingException("Livre inexistant","",alerte);
		}
		System.out.println(bookState(cinField.getText()));
		if( bookState(codeField.getText()).compareTo("emprunté") == 0 ) {
			throw new TypingException("Livre emprunté","Veuillez choisir un autre livre",alerte);
		}
		if(dateField.getValue() == null) {
			throw new TypingException("Valider une date d'emprunt","",alerte);
		}
	}
	
	/*********************   Fetching Data from Database & Auto-Complete  *********************/
	
	public boolean fetchCin(KeyEvent event) {
		String cin = cinField.getText();
		try {
			Connection myConnection = new DatabaseConnection().getConnection();
			String rq = "select * from lecteur where cin = '"+cin+"'";
			Statement st = myConnection.createStatement(); ;
			ResultSet rs =st.executeQuery(rq);
			while(rs.next()) {
					//rs.getLong("CIN");
				ReaderField.setText(rs.getString("PRENOM")+" "+rs.getString("NOM"));
				return true;
				}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		ReaderField.setText("Aucun lecteur correspondant");
		return false;
	}
	
	public boolean fetchCode(KeyEvent event) {
		String code = codeField.getText();
		try {
			Connection myConnection = new DatabaseConnection().getConnection();
			String rq = "select * from livre where code = '"+code+"'";
			Statement st = myConnection.createStatement(); ;
			ResultSet rs =st.executeQuery(rq);
			while(rs.next()) {
					//rs.getLong("CODE");
				BookField.setText(rs.getString("TITRE")+" ("+bookState(code)+")");
				return true;
				}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		BookField.setText("Aucun livre correspondant");
		return false;
	}
	
	public String bookState(String code) {
		try {
			Connection myConnection = new DatabaseConnection().getConnection();
			String rq = "select * from emprunter where code = '"+code+"'";
			Statement st = myConnection.createStatement(); ;
			ResultSet rs =st.executeQuery(rq);
			while(rs.next()) {
				return "emprunté";
				}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "non emprunté";
	}
	
	
	
	/*********************   Database Manipulation   *********************/
	
	public int addBorrowToDatabase(ActionEvent event) {
		try {
			fieldsFilledCorrectly();
			Emprunt emprunt = new Emprunt(Long.parseLong(cinField.getText()), Long.parseLong(codeField.getText()), dateField.getValue() );
			Connection myConnection = new DatabaseConnection().getConnection();
		 	String insertQuery = "insert into emprunter (cin,code,date_emprunt) values (?,?,?);";
			PreparedStatement pst = myConnection.prepareStatement(insertQuery);
			pst.setLong(1, emprunt.getCinEmprunt());
			pst.setLong(2, emprunt.getCodeEmprunt());
			pst.setString(3, emprunt.getDateEmprunt().toString());
			pst.executeUpdate();
			myConnection.close();
			Alert exists = new Alert(AlertType.INFORMATION);
			exists.setTitle("Information");
			exists.setHeaderText("L'emprunt a été ajouté avec succès");
			exists.setContentText("L'emprunt est maintenant disponible dans la base de données");
			exists.show();

			showBorrowings();
			
			
		}catch(TypingException et) {
			return 0;
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
			Alert exists = new Alert(AlertType.WARNING);
			exists.setTitle("Information");
			exists.setHeaderText("erreur");
			exists.setContentText("");
			exists.show();
		} 
		return 1;
	}
	
	public int modifyBorrow(ActionEvent event) {
		try {
			Alert alerte = new Alert(AlertType.WARNING);
			alerte.setTitle("Saisie");
			if(fetchCin(null) == false) {
				throw new TypingException("Lecteur inexistant","",alerte);
			}
			if(fetchCode(null) == false) {
				throw new TypingException("Livre inexistant","",alerte);
			}
		} catch (TypingException et) {
			return 0;
		} 
		try {
			Connection myConnection = new DatabaseConnection().getConnection();
		 	String insertQuery = "update emprunter set date_emprunt=? where cin=? and code=?;";
			PreparedStatement pst = myConnection.prepareStatement(insertQuery);
			pst.setLong(2, Long.parseLong(cinField.getText()) );
			pst.setLong(3, Long.parseLong(codeField.getText()) );
			pst.setString(1, dateField.getValue().toString());
			pst.executeUpdate();
			myConnection.close();
			showBorrowings();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		} 
	return 1;
	}
	
	
	public void deleteBorrowFromDatabase(ActionEvent event) {
		try {
			Connection myConnection = new DatabaseConnection().getConnection();
		 	String deleteQuery = "delete from emprunter where code=?;";
			PreparedStatement pst = myConnection.prepareStatement(deleteQuery);
			//pst.setLong(1, Long.parseLong(cinField.getText()) );
			pst.setLong(1, Long.parseLong(codeField.getText()) );
			//Deletion Confirmation
			Alert ask = new Alert(Alert.AlertType.CONFIRMATION);
			ask.setTitle("Suppression");
			ask.setHeaderText("Suppression d'emprunt");
			ask.setContentText("L'emprunt sera supprimé de la base de données");
			Optional<ButtonType> result = ask.showAndWait();
			if(result.get() == ButtonType.OK) {
				pst.executeUpdate();
			}
			myConnection.close();
			showBorrowings();
		}catch(Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alerte");
			alert.setHeaderText("Suppression invalide");
			alert.setContentText("Veuillez cliquer sur l'emprunt que vous souhaitez supprimer");
			alert.show();
		} 
	} 
	
	public void showBorrowings() {
		try {
			ObservableList<Emprunt> emprunts = borrwingsTable.getItems();
			emprunts.removeAll(emprunts);
			Connection myConnection = new DatabaseConnection().getConnection();
			String rq = "select * from emprunter;";
			Statement st = myConnection.createStatement(); ;
			ResultSet rs =st.executeQuery(rq);
			while(rs.next()) {
				emprunts.add(new Emprunt(rs.getLong("CIN"),rs.getLong("CODE"),LocalDate.parse(rs.getString("DATE_EMPRUNT"))));
			}
			borrwingsTable.setItems(emprunts);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void filter(javafx.scene.input.KeyEvent event) {
		String typed = searchField.getText();
		try {
			ObservableList<Emprunt> emprunts = borrwingsTable.getItems();
			emprunts.removeAll(emprunts);
			Connection myConnection = new DatabaseConnection().getConnection();
			String rq = "select * from emprunter where cin like '%"+typed+"%'";
			rq = rq + "or code like '%"+typed+"%'";
			rq = rq + "or date_emprunt like '%"+typed+"%'";
			Statement st = myConnection.createStatement(); ;
			ResultSet rs =st.executeQuery(rq);
			while(rs.next()) {
				emprunts.add(new Emprunt(rs.getLong("CIN"),rs.getLong("CODE"),LocalDate.parse(rs.getString("DATE_EMPRUNT"))));			
				}
			borrwingsTable.setItems(emprunts);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}	
	}
	
	
// End of BorrowController
}

