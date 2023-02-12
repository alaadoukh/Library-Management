package classes;

public class Romance extends Livre {
	String descriptif_histoire;
	String nom_personnage_principal;
	
	public Romance() {
		super();
		
	}
	public Romance(String t, String a, long c, String descriptif_histoire, String nom_personnage_principal) {
		super(t, a, c);
		this.descriptif_histoire = descriptif_histoire;
		this.nom_personnage_principal = nom_personnage_principal;
	}
	public Romance(String t, String a, String descriptif_histoire, String nom_personnage_principal) {
		super(t, a);
		this.descriptif_histoire = descriptif_histoire;
		this.nom_personnage_principal = nom_personnage_principal;
	}
	@Override
	public String toString() {
		return "Romance [descriptif_histoire=" + descriptif_histoire + ", nom_personnage_principal="
				+ nom_personnage_principal + ", getTitre()=" + getTitre() + ", getAuteur()=" + getAuteur() + "]";
	}
}
