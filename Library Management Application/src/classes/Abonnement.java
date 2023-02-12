package classes;

import java.time.LocalDate;
import java.util.ArrayList;

public class Abonnement {
	private ArrayList<Detail_Emprunt> liste_livresEmrpruntes= new ArrayList<>();
	double frais_abonnement;
	private LocalDate date_creation;
	
	public Abonnement(double frais_abonnement, LocalDate date_creation) {
		super();
		this.frais_abonnement = frais_abonnement;
		this.date_creation = date_creation;
	}

	public Abonnement() {
		super();
	}

	public Abonnement(ArrayList<Detail_Emprunt> listeEmprunts, LocalDate of) {
		this.liste_livresEmrpruntes = listeEmprunts;
		this.date_creation = of;
	}

	public ArrayList<Detail_Emprunt> getListe_livresEmrpruntes() {
		return liste_livresEmrpruntes;
	}

	public void setListe_livresEmrpruntes(ArrayList<Detail_Emprunt> liste_abonnement) {
		this.liste_livresEmrpruntes = liste_abonnement;
	}

	public double getFrais_abonnement() {
		return frais_abonnement;
	}

	public void setFrais_abonnement(double frais_abonnement) {
		this.frais_abonnement = frais_abonnement;
	}

	public LocalDate getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(LocalDate date_creation) {
		this.date_creation = date_creation;
	}
	
	
	
}
