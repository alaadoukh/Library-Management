package classes;

import java.time.LocalDate;
import java.time.Period;

public class Lecteur {
	private long cin, numTel;
	private String nom, prenom;
	
	private float credit;
	private LocalDate date_abonnement;
	private float frais;
	private String adresse_email;
	private String preference;
	
	
	
	public Lecteur(long cin, String nom, String prenom, long numTel,  float credit, LocalDate date_abonnement,
			float frais, String adresse_email, String preference) {
		super();
		this.cin = cin;
		this.nom = nom;
		this.prenom = prenom;
		this.numTel = numTel;
		this.credit = credit;
		this.date_abonnement = date_abonnement;
		this.frais = frais;
		this.adresse_email = adresse_email;
		this.preference = preference;
	}
	
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public LocalDate getDate_abonnement() {
		return date_abonnement;
	}
	public void setDate_abonnement(LocalDate date_abonnement) {
		this.date_abonnement = date_abonnement;
	}
	public float getFrais() {
		return frais;
	}
	public void setFrais(float frais) {
		this.frais = frais;
	}
	
	
	
	
	public String getAdresse_email() {
		return adresse_email;
	}
	public void setAdresse_email(String adresse_email) {
		this.adresse_email = adresse_email;
	}
	public String getPreference() {
		return preference;
	}
	public void setPreference(String preference) {
		this.preference = preference;
	}
	public long getCin() {
		return this.cin;
	}
	public void setCin(long cin) {
		this.cin = cin;
	}
	public long getNumTel() {
		return numTel;
	}
	public void setNumTel(long numTel) {
		this.numTel = numTel;
	}

	public Lecteur() {
		super();
	}
	public Lecteur(long cin, long numTel, String nom, String prenom) {
		super();
		this.cin = cin;
		this.numTel = numTel;
		this.nom = nom;
		this.prenom = prenom;
	}
	public Lecteur(long numTel, String nom, String prenom) {
		super();
		this.numTel = numTel;
		this.nom = nom;
		this.prenom = prenom;
	}
	@Override
	public String toString() {
		return "Lecteur [cin=" + cin + ", numTel=" + numTel + ", nom=" + nom + ", prenom=" + prenom + "]";
	}
	
	boolean abonnement_epuise() {
		Period p = Period.between(this.getDate_abonnement(), LocalDate.now());
		if(p.getYears()>=1) {
			return true;
		}
		return false;
	}
	void annuler_abonnement() {
		//this.abonnement = null;
	}
	
	double frais_abonnement() {
		return this.getFrais();
	}
	public float getCredit() {
		return credit;
	}
	public void setCredit(float credit) {
		this.credit = credit;
	}
}
