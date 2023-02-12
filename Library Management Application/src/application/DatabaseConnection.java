package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	
	private Connection connection;
	private String url = "jdbc:mysql://localhost:3306/bibliotheque";
	private String password = "1234";
	
	public DatabaseConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Connecting...");
			this.connection = DriverManager.getConnection(url,"root",password);
			System.out.println("Connected to DATABASE");
			} 
		catch ( Exception e ) {
			System.out.println("Connection Problem !");
			System.out.println(e.getMessage());
			}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPassword() {
		return password;
	}

	/*public static void main(String[] args) {
		DatabaseConnection c = new DatabaseConnection()	;}
	*/
}
