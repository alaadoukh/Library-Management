package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import classes.Lecteur;
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


public class ReadersController implements Initializable {
	
	private Stage stage;
	 private Scene scene;
	 private Parent root;
	 
	 /*********************   Readers Fields & Attributes   *********************/
	 
	 @FXML
		private TextField lastNameField;
	 @FXML
		private TextField firstNameField;
	 @FXML
		private TextField mobileField;
	 @FXML
		private TextField cinField;
	 @FXML
		private TextField creditField;
	 @FXML
		private TextField taxField;
	 @FXML
		private DatePicker aboDate;
	 @FXML
		private CheckBox fideleCheckBox;
	 @FXML
		private TextField prefField;
	 @FXML
		private TextField mailField;
	 	
	 	
	 @FXML
		private TextField searchField;
	 @FXML
	 	private Button addButton;
	 	
	 	/*********************   Readers Table   *********************/
	 	
	 @FXML
	 	private TableView<Lecteur> readersTable = new TableView<>();
	 @FXML
	 	private TableColumn<Lecteur, Long> cinColumn = new TableColumn<>();
	 @FXML
	 	private TableColumn<Lecteur, String> lastNameColumn = new TableColumn<>();
	 @FXML
	 	private TableColumn<Lecteur, String> firstNameColumn = new TableColumn<>();
	 @FXML
	 	private TableColumn<Lecteur, Long> mobileColumn = new TableColumn<>();
	 @FXML
	 	private TableColumn<Lecteur, Long> creditColumn = new TableColumn<>();
	 @FXML
	 	private TableColumn<Lecteur, LocalDate> aboColumn = new TableColumn<>();
	 @FXML
	 	private TableColumn<Lecteur, Long> taxColumn = new TableColumn<>();
	 @FXML
	 	private TableColumn<Lecteur, String> mailColumn = new TableColumn<>();
	 @FXML
	 	private TableColumn<Lecteur, String> prefColumn = new TableColumn<>();
	 
	 /*********************   Initialize   *********************/
	 public void fideleCheck(ActionEvent event) {
			boolean checked = fideleCheckBox.isSelected();
				prefField.setDisable(!checked);
				mailField.setDisable(!checked);
			 if(!fideleCheckBox.isSelected()) {
				 prefField.setText(null);
				 mailField.setText(null);
	 } }
	 
	 
	 @Override
		public void initialize(URL url, ResourceBundle resourceBundle) {
			fideleCheckBox.setOnAction(this::fideleCheck);
			cinColumn.setCellValueFactory(new PropertyValueFactory<Lecteur, Long>("cin"));
			lastNameColumn.setCellValueFactory(new PropertyValueFactory<Lecteur, String>("Nom"));
			firstNameColumn.setCellValueFactory(new PropertyValueFactory<Lecteur, String>("Prenom"));
			mobileColumn.setCellValueFactory(new PropertyValueFactory<Lecteur, Long>("numTel"));
			creditColumn.setCellValueFactory(new PropertyValueFactory<Lecteur, Long>("Credit"));
			aboColumn.setCellValueFactory(new PropertyValueFactory<Lecteur, LocalDate>("date_abonnement"));
			taxColumn.setCellValueFactory(new PropertyValueFactory<Lecteur, Long>("Frais"));
			mailColumn.setCellValueFactory(new PropertyValueFactory<Lecteur, String>("adresse_email"));
			prefColumn.setCellValueFactory(new PropertyValueFactory<Lecteur, String>("Preference"));
			showReaders();
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
		//errorMessage.setText("");
		int selectedRow = readersTable.getSelectionModel().getSelectedIndex();
		if(selectedRow < 0) {return 0;}
		Lecteur selLecteur = readersTable.getItems().get(selectedRow);
		cinField.setText(Long.toString(selLecteur.getCin()));
		lastNameField.setText(selLecteur.getNom());
		firstNameField.setText(selLecteur.getPrenom());
		mobileField.setText(Long.toString(selLecteur.getNumTel()));
		creditField.setText(Long.toString((long) selLecteur.getCredit()));
		taxField.setText(Long.toString((long) selLecteur.getFrais()));
		aboDate.setValue(selLecteur.getDate_abonnement());
		prefField.setText(selLecteur.getPreference());
		mailField.setText(selLecteur.getAdresse_email());
		if(prefField.getText() != null) {
			fideleCheckBox.setSelected(true);
			prefField.setDisable(false);
			mailField.setDisable(false);
		}else {
			fideleCheckBox.setSelected(false);
			prefField.setDisable(true);
			mailField.setDisable(true);
		}
		return 1; //Success
	}
	
	public void refresh(ActionEvent event) {
		//errorMessage.setText("");
		showReaders();
		cinField.setText(null);
		lastNameField.setText(null);
		firstNameField.setText(null);
		mobileField.setText(null);
		creditField.setText("0");
		taxField.setText("20");
		aboDate.setValue(null);
		prefField.setText(null);
		mailField.setText(null);
		fideleCheckBox.setSelected(false);
		prefField.setDisable(true);
		mailField.setDisable(true);
	}
	
	public void clear(ActionEvent event) {
		//errorMessage.setText("");
		searchField.setText(null);
		showReaders();
	}
	
/*********************   Verification Functions   *********************/
	
	public void fieldsFilledCorrectly() throws TypingException {
		//errorMessage.setText("");
		Alert alerte = new Alert(AlertType.WARNING);
		alerte.setTitle("Saisie");
		if(firstNameField.getText() == "" || lastNameField.getText() == "") {
			throw new TypingException("Saisir le nom complet du lecteur","",alerte);
		}else if( mobileField.getText() == "") {
			throw new TypingException("Saisir le numéro de telephone","",alerte);
		}
		try {
			Long.parseLong(mobileField.getText());
		}catch(Exception e) {
			throw new TypingException("Numéro de telephone invalide","",alerte);
		}
		try {
			Long.parseLong(cinField.getText());
		}catch(Exception e) {
			throw new TypingException("CIN invalide","",alerte);
		}
		try {
			Long.parseLong(creditField.getText());
		}catch(Exception e) {
			throw new TypingException("Crédit du lecteur non valide","",alerte);
		}
		try {
			Long.parseLong(taxField.getText());
		}catch(Exception e) {
			throw new TypingException("Frais d'abonnement non valide","",alerte);
		}
		if(aboDate.getValue() == null) {
			throw new TypingException("Valider une date de création d'abonnement","",alerte);
		}
		if(fideleCheckBox.isSelected()) {
			if(prefField.getText() == null) {
				throw new TypingException("Saisir la preference","Un lecteur fidèle doit avoir une preference",alerte);
			}
			if(mailField.getText() == null) {
				throw new TypingException("Saisir l'adresse email","Un lecteur fidèle doit avoir une adresse email",alerte);
			}
		}
	}
	
	/*********************   Database Manipulation   *********************/
	
	public int addReaderToDatabase(ActionEvent event) {
		try {
			fieldsFilledCorrectly();
			Lecteur reader = new Lecteur(Long.parseLong(cinField.getText()), lastNameField.getText(), firstNameField.getText(), Long.parseLong(mobileField.getText()), Long.parseLong(creditField.getText()), aboDate.getValue(), Long.parseLong(taxField.getText()), prefField.getText(), mailField.getText());
			Connection myConnection = new DatabaseConnection().getConnection();
		 	String insertQuery = "insert into lecteur (cin,nom,prenom,numtel,credit,date_abonnement,frais,email,preference) values (?,?,?,?,?,?,?,?,?);";
			PreparedStatement pst = myConnection.prepareStatement(insertQuery);
			pst.setLong(1, reader.getCin());
			pst.setString(2, reader.getNom() );
			pst.setString(3, reader.getPrenom() );
			pst.setLong(4, reader.getNumTel() );
			pst.setLong(5, (long) reader.getCredit() );
			pst.setString(6, reader.getDate_abonnement().toString());
			pst.setLong(7, (long) reader.getFrais() );
			pst.setString(8, reader.getPreference() );
			pst.setString(9, reader.getAdresse_email() );
			pst.executeUpdate();
			myConnection.close();
			Alert exists = new Alert(AlertType.INFORMATION);
			exists.setTitle("Information");
			exists.setHeaderText("Le lecteur a été ajouté avec succès");
			exists.setContentText("Le lecteur est maintenant disponible dans la base de données");
			exists.show();

			showReaders();
			
			
		}catch(TypingException et) {
			return 0;
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
			Alert exists = new Alert(AlertType.WARNING);
			exists.setTitle("Information");
			exists.setHeaderText("Le numéro de la carte d'identité du lecteur existe déjà");
			exists.setContentText("Cin du lecteur doit être unique");
			exists.show();
		} 
		return 1;
	}
	
	public int modifyReader(ActionEvent event) {
		//errorMessage.setText("");
		try {
			fieldsFilledCorrectly();
		} catch (TypingException et) {
			return 0;
		} 
		try {
			Connection myConnection = new DatabaseConnection().getConnection();
		 	String insertQuery = "update lecteur set nom=?, prenom=?, numtel=?, credit=?, date_abonnement=?, frais=?, email=?, preference=? where cin=?;";
			PreparedStatement pst = myConnection.prepareStatement(insertQuery);
			pst.setString(1, lastNameField.getText() );
			pst.setString(2, firstNameField.getText() );
			pst.setLong(3, Long.parseLong(mobileField.getText()) );
			pst.setLong(4, Long.parseLong(creditField.getText()) );
			//pst.setDate(5,(Date) Date.from(aboDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
			pst.setString(5, aboDate.getValue().toString());
			pst.setLong(6, Long.parseLong(taxField.getText()) );
			pst.setString(7, prefField.getText() );
			pst.setString(8, mailField.getText());
			pst.setLong(9, Long.parseLong(cinField.getText()));
			pst.executeUpdate();
			myConnection.close();
			showReaders();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		} 
	return 1;
	}
	
	
	public void deleteReaderFromDatabase(ActionEvent event) {
		//errorMessage.setText("");
		try {
			Connection myConnection = new DatabaseConnection().getConnection();
		 	String deleteQuery = "delete from lecteur where cin=?;";
			PreparedStatement pst = myConnection.prepareStatement(deleteQuery);
			pst.setLong(1, Long.parseLong(cinField.getText()) );
			//Update Borrowings
			String brQuery = "delete from emprunter where cin=?;";
			PreparedStatement brpst = myConnection.prepareStatement(brQuery);
			brpst.setLong(1, Long.parseLong(cinField.getText()) );
			//Deletion Confirmation
			Alert ask = new Alert(Alert.AlertType.CONFIRMATION);
			ask.setTitle("Suppression");
			ask.setHeaderText("Suppression du lecteur");
			ask.setContentText("Le lecteur sera supprimé de la base de données");
			Optional<ButtonType> result = ask.showAndWait();
			if(result.get() == ButtonType.OK) {
				pst.executeUpdate();
				brpst.executeUpdate();
			}
			myConnection.close();
			showReaders();
		}catch(Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alerte");
			alert.setHeaderText("Suppression invalide");
			alert.setContentText("Veuillez cliquer sur le lecteur que vous souhaitez supprimer");
			alert.show();
		} 
	}
	
	public void showReaders() {
		try {
			ObservableList<Lecteur> lecteurs = readersTable.getItems();
			lecteurs.removeAll(lecteurs);
			Connection myConnection = new DatabaseConnection().getConnection();
			String rq = "select * from lecteur;";
			Statement st = myConnection.createStatement(); ;
			ResultSet rs =st.executeQuery(rq);
			while(rs.next()) {
				lecteurs.add(new Lecteur(rs.getLong("CIN"),rs.getString("Nom"),rs.getString("Prenom"),rs.getLong("NUMTEL"),rs.getLong("CREDIT"),LocalDate.parse(rs.getString("DATE_ABONNEMENT")),rs.getLong("FRAIS"),rs.getString("EMAIL"),rs.getString("PREFERENCE")));
			}
			readersTable.setItems(lecteurs);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void filterReaders(javafx.scene.input.KeyEvent event) {
		String typed = searchField.getText();
		try {
			ObservableList<Lecteur> lecteurs = readersTable.getItems();
			lecteurs.removeAll(lecteurs);
			Connection myConnection = new DatabaseConnection().getConnection();
			String rq = "select * from lecteur where cin like '%"+typed+"%'";
			rq = rq + "or nom like '%"+typed+"%'";
			rq = rq + "or prenom like '%"+typed+"%'";
			rq = rq + "or numtel like '%"+typed+"%'";
			rq = rq + "or credit like '%"+typed+"%'";
			rq = rq + "or date_abonnement like '%"+typed+"%'";
			rq = rq + "or frais like '%"+typed+"%'";
			rq = rq + "or preference like '%"+typed+"%'";
			rq = rq + "or email like '%"+typed+"%'";
			Statement st = myConnection.createStatement(); ;
			ResultSet rs =st.executeQuery(rq);
			while(rs.next()) {
				lecteurs.add(new Lecteur(rs.getLong("CIN"),rs.getString("Nom"),rs.getString("Prenom"),rs.getLong("NUMTEL"),rs.getLong("CREDIT"),LocalDate.parse(rs.getString("DATE_ABONNEMENT")),rs.getLong("FRAIS"),rs.getString("EMAIL"),rs.getString("PREFERENCE")));			}
			readersTable.setItems(lecteurs);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}	
	}
	
	
// End of ReadersController
}
