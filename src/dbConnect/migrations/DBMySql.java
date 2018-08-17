package dbConnect.migrations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import utilities.migrations.ConfigProperties;

public class DBMySql {

	private String host = "";
	private String port = "";
	private String database = "";
	private String user = "";
	private String password = "";
	public static Connection connMysql = null;
	
	public DBMySql(String host, String port, String database, String user, String password) {
		this.host = host.trim();
		this.port = port.trim();
		this.database = database.trim();
		this.user = user.trim();
		this.password = password.trim();
	}

	public DBMySql(String url) {
		ConfigProperties prop = new ConfigProperties(url);
		this.host = prop.parameter("host");
		this.port = prop.parameter("port");
		this.database = prop.parameter("db");
		this.user = prop.parameter("user");
		this.password = prop.parameter("pass");
	}
	
	public DBMySql() {
		ConfigProperties prop = new ConfigProperties("C:\\Properties\\MySqlDefault.properties");
		this.host = prop.parameter("host");
		this.port = prop.parameter("port");
		this.database = prop.parameter("db");
		this.user = prop.parameter("user");
		this.password = prop.parameter("pass");
	}
	
	public Connection connect() {
		if(connMysql == null) {
			return connectMysql();
		}else {
			return connMysql;
		}
	}
	
	private Connection connectMysql() {
		String stringConnection = "";
		if(connMysql == null) {
			if(this.database == "") {
				stringConnection = "jdbc:mysql://" + this.host + ":" + this.port;
			}else {
				stringConnection = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;
			}
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println(stringConnection);
				connMysql = (Connection) DriverManager.getConnection(stringConnection, this.user, this.password);
				System.out.println("Se conectó");
			} catch (SQLException e) {
				System.out.println("Error de conexión con la base de datos");
				e.printStackTrace();
				return null;
			} catch(ClassNotFoundException ex){
				ex.printStackTrace();
				return null;
			}
		}
		
		return connMysql;
	}
	
	public static boolean desconnect() {
		try {
			if(connMysql != null) {
				connMysql.close();
				connMysql = null;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
