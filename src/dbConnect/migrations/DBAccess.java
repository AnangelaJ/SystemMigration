package dbConnect.migrations;

import java.sql.Connection;
import java.sql.DriverManager;

import utilities.migrations.ConfigProperties;

public class DBAccess {
	
	private String url = "";
	public static Connection connAccess = null;
	
	public DBAccess(String url) {
		this.url = "jdbc:ucanaccess://";
		this.url += url;
	}
	
	public DBAccess() {
		ConfigProperties prop = new ConfigProperties("C:\\Properties\\Access.properties");
		this.url = "jdbc:ucanaccess://";
		this.url += prop.parameter("url").trim();
	}
	
	public Connection connect() {
		if(connAccess == null) {
			return connectAccess();
		}
		return connAccess;
	}
	
	private Connection connectAccess() {
		if(connAccess == null) {
			String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
			try {
				Class.forName(driver);
				System.out.println(this.url);
				connAccess = DriverManager.getConnection(this.url);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return connAccess;
	}
	
	public static boolean desconnect() {
		try {
			if(connAccess != null) {
				connAccess.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
