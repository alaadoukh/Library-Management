package classes;

import java.time.LocalDate;

public class Emprunt {
	private long cinEmprunt;
	private long codeEmprunt;
	private LocalDate dateEmprunt;
	
	
	public Emprunt(long cinEmprunt, long codeEmprunt, LocalDate dateEmprunt) {
		super();
		this.cinEmprunt = cinEmprunt;
		this.codeEmprunt = codeEmprunt;
		this.dateEmprunt = dateEmprunt;
	}
	
	
	public long getCinEmprunt() {
		return cinEmprunt;
	}
	public void setCinEmprunt(long cinEmprunt) {
		this.cinEmprunt = cinEmprunt;
	}
	public long getCodeEmprunt() {
		return codeEmprunt;
	}
	public void setCodeEmprunt(long codeEmprunt) {
		this.codeEmprunt = codeEmprunt;
	}
	public LocalDate getDateEmprunt() {
		return dateEmprunt;
	}
	public void setDateEmprunt(LocalDate dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}
}
