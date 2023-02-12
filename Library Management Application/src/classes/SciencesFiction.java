package classes;

public class SciencesFiction extends Livre {
	int annee;
	String espace_histoire;
	
	public SciencesFiction() {
		super();
	}
	public SciencesFiction(String t, String a, long c, int annee, String espace_histoire) {
		super(t, a, c);
		this.annee = annee;
		this.espace_histoire = espace_histoire;
	}
	public SciencesFiction(String t, String a, int annee, String espace_histoire) {
		super(t, a);
		this.annee = annee;
		this.espace_histoire = espace_histoire;
	}
	@Override
	public String toString() {
		return "SciencesFiction [annee=" + annee + ", espace_histoire=" + espace_histoire + ", getTitre()=" + getTitre()
				+ ", getAuteur()=" + getAuteur() + "]";
	}
}
