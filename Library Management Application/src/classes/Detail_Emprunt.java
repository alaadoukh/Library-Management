package classes;

import java.time.LocalDate;

public class Detail_Emprunt {
	private Livre livre;
	private LocalDate date_emprunt;
	
	public Detail_Emprunt(Livre l, LocalDate d) {
		livre = l;
		date_emprunt = d;
	}
	
	public Livre getLivre() {
		return livre;
	}
	public void setLivre(Livre livre) {
		this.livre = livre;
	}
	public LocalDate getDate_emprunt() {
		return date_emprunt;
	}
	public void setDate_emprunt(LocalDate date_emprunt) {
		this.date_emprunt = date_emprunt;
	}
}
