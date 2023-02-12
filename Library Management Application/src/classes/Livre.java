package classes;

public class Livre {
	//Attributs
	private long code ;
    private String titre, auteur ;
    private static long n ;//initialisé par default a zero 
    private long ISBN;
    private String categorie;
   
  
    public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public long getISBN() {
		return ISBN;
	}
	public void setISBN(long iSBN) {
		ISBN = iSBN;
	}
  public long getCode() {
	return code;
}
public void setCode(long code) {
	this.code = code;
}
public String getTitre() {
	return titre;
}
public void setTitre(String titre) {
	this.titre = titre;
}
public String getAuteur() {
	return auteur;
}
public void setAuteur(String auteur) {
	this.auteur = auteur;
}
public static long getN() {
	return n;
}
public static void setN(long n) {
	Livre.n = n;
}
//Constructeurs
public Livre(){
    n++ ;
    code = n ;
}
public Livre(String t, String a){
    titre = t; // livre( String titre ..)  ---> this.titre=titre;
    auteur = a;
    n++ ;
    code = n ;// code=++n;
}
public Livre(String t, String a, long c){
      titre = t; // livre( String titre ..)  ---> this.titre=titre;
      auteur = a;
      ISBN = c;
      n++ ;
      code = n ;// code=++n;
  }
  	public Livre(String t, String a, long code, long isbn, String c) {
  		titre = t; 
        auteur = a;
        this.code = code;
        this.ISBN = isbn;
        this.categorie = c;
}
//Méthodes
  public String toString(){
      String ch;
      ch="Titre : "+titre+"\n Auteur : "+auteur+"\n Code : "+code;
      return(ch);
  }
  public int compare(Livre livre1){
      return(this.titre.compareTo(livre1.titre));
  }
  static int compare(Livre l1,Livre l2){
      return(l1.titre.compareTo(l2.titre));
  }
}  
