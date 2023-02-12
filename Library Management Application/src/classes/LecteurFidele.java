package classes;

public class LecteurFidele extends Lecteur{
	private String adresse_email;
	private String preference;
	
	public LecteurFidele() {
		super();
	}
	public LecteurFidele(long cin, long numTel, String nom, String prenom, Abonnement a, String adresse, String preference) {
		super(cin, numTel, nom, prenom, a);
		this.setAdresse_email(adresse);
		this.setPreference(preference);
	}
	public LecteurFidele(long cin, long numTel, String nom, String prenom, String adresse, String preference) {
		super(cin, numTel, nom, prenom);
		this.setAdresse_email(adresse);
		this.setPreference(preference);
	}
	public LecteurFidele(long numTel, String nom, String prenom, String adresse, String preference) {
		super(numTel, nom, prenom);
		this.setAdresse_email(adresse);
		this.setPreference(preference);
	}
	public LecteurFidele(long numTel, String nom, String prenom) {
		super(numTel, nom, prenom);
	}
	public LecteurFidele(long cin, long numTel, String nom, String prenom) {
		super(cin,numTel,nom,prenom);
	}
	@Override
	double frais_abonnement() {
		return super.frais_abonnement()*0.85;
	}
	@Override
	public String toString() {
		return "LecteurFidele [ nom : "+ nom + "prenom : "+prenom + " ]";
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
	
	
}
