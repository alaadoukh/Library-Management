package classes;

public class EnigmePoliciere extends Livre {
	String descriptif_crime;
	String nom_detective_prive;
	String nom_victime;
	
	public EnigmePoliciere() {
		super();
		
	}
	public EnigmePoliciere(String t, String a, long c,String descriptif_crime,String nom_detective_prive, String nom_victime) {
		super(t, a, c);
		this.descriptif_crime = descriptif_crime;
		this.nom_detective_prive = nom_detective_prive;
		this.nom_victime = nom_victime;
	}
	public EnigmePoliciere(String t, String a, String descriptif_crime,String nom_detective_prive, String nom_victime) {
		super(t, a);
		this.descriptif_crime = descriptif_crime;
		this.nom_detective_prive = nom_detective_prive;
		this.nom_victime = nom_victime;
	}
	@Override
	public String toString() {
		return "EnigmePoliciere [descriptif_crime=" + descriptif_crime + ", nom_detective_prive=" + nom_detective_prive
				+ ", nom_victime=" + nom_victime + ", getTitre()=" + getTitre() + ", getAuteur()=" + getAuteur() + "]";
	}
	
}
