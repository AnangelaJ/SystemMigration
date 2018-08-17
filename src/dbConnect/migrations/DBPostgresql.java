package dbConnect.migrations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import utilities.migrations.ConfigProperties;

public class DBPostgresql {
	
	private String host = "";
	private String port = "";
	private String database = "";
	private String user = "";
	private String password = "";
	public static Connection connPostgresql = null;
	
	public DBPostgresql(String host, String port, String database, String user, String password) {
		this.host = host.trim();
		this.port = port.trim();
		this.database = database.trim();
		this.user = user.trim();
		this.password = password.trim();
	}
	
	public DBPostgresql(String url) {
		ConfigProperties prop = new ConfigProperties(url);
		this.host = prop.parameter("host");
		this.port = prop.parameter("port");
		this.database = prop.parameter("db");
		this.user = prop.parameter("user");
		this.password = prop.parameter("pass");
	}

	public DBPostgresql() {
		ConfigProperties prop = new ConfigProperties("C:\\Properties\\PostgresqlDefault.properties");
		this.host = prop.parameter("host");
		this.port = prop.parameter("port");
		this.database = prop.parameter("db");
		this.user = prop.parameter("user");
		this.password = prop.parameter("pass");
	}
	
	public Connection connect() {
		if(connPostgresql == null) {
			return connectPostgresql();
		}
		return connPostgresql;
	}
	
	private Connection connectPostgresql() {
		if(connPostgresql == null) {
			String stringConnection = "jdbc:postgresql://" + this.host + ":" + this.port + "/" + this.database;
			System.out.println(stringConnection);
			
			try {
				Class.forName("org.postgresql.Driver").newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				connPostgresql = DriverManager.getConnection(stringConnection, this.user, this.password);
				System.out.println("Se conectó");
			} catch (SQLException ex) {
				System.out.println("Error de conexión con la base de datos");
				ex.printStackTrace();
			}
		}
		return connPostgresql;
	}
	
	public static boolean desconnect() {
		try {
			if(connPostgresql != null) {
				connPostgresql.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
