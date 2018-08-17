package utilities.migrations;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONArray;

import dbConnect.migrations.DBMySql;
import dbConnect.migrations.DBPostgresql;

public class DataBases {
	
	public DataBases() {}
	
	public JSONArray ListDBPg(){
		ArrayList<String> ResultA = new ArrayList<String>();
		JSONArray jsonA = new JSONArray();
		Connection conn = null;
		DBPostgresql pg = new DBPostgresql();
		conn = pg.connect();
		
		if(conn != null) {
			ResultSet rs = null;
			Statement stmt= null;
			
			try {
				stmt = conn.createStatement();
			    rs = stmt.executeQuery("SELECT datname FROM pg_database");
				while (rs.next()) {
					ResultA.add(rs.getString(1));
				}
				jsonA.put(ResultA);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			} finally {
				try {
					if(rs != null) {
						stmt.close();
						rs.close();
						conn.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
					return null;
				}
			}
			return jsonA;
		}else {
			return null;
		}
	}
	
	public JSONArray ListDBMySql(){
		ArrayList<String> ResultA = new ArrayList<String>();
		JSONArray jsonA = new JSONArray();
		Connection conn = null;
		DBMySql mysql = new DBMySql();
		conn = mysql.connect();
		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SHOW DATABASES;");
			
			while(rs.next()) {
				ResultA.add(rs.getString(1));
			}
			jsonA.put(ResultA);
			return jsonA;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(rs != null) {
					rs.close();
					stmt.close();
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				return null;
			}
		}
	}
	
	public JSONArray ListDBExcel() {
		JSONArray jsonA = new JSONArray();
		String extension = ".xls";

		FilenameFilter filter = new FilenameFilter() {
		    @Override
		    public boolean accept(File file, String name) {
			    if (name.endsWith(extension)) {
			        return true;
			    } else {
			        return false;
			    }
		    }
		};

		File directorio = new File("C:\\DataBases");
		String[] db = directorio.list(filter);
		System.out.println(String.valueOf(db));
		jsonA.put(db);
		System.out.println(jsonA.length());
		if(jsonA.length() > 0) {
			return jsonA;
		}else {
			return null;
		}
	}
	
	public JSONArray ListDBAccess() {
		JSONArray jsonA = new JSONArray();
		String extension = ".accdb";

		FilenameFilter filter = new FilenameFilter() {
		    @Override
		    public boolean accept(File file, String name) {
			    if (name.endsWith(extension)) {
			        return true;
			    } else {
			        return false;
			    }
		    }
		};

		File directorio = new File("C:\\DataBases");
		String[] db = directorio.list(filter);
		System.out.println(String.valueOf(db));
		jsonA.put(db);
		System.out.println(jsonA.length());
		if(jsonA.length() > 0) {
			return jsonA;
		}else {
			return null;
		}
	}
	
	public JSONArray ListDBTxt() {
		JSONArray jsonA = new JSONArray();
		String extension = ".txt";

		FilenameFilter filter = new FilenameFilter() {
		    @Override
		    public boolean accept(File file, String name) {
			    if (name.endsWith(extension)) {
			        return true;
			    } else {
			        return false;
			    }
		    }
		};

		File directorio = new File("C:\\DataBases");
		String[] db = directorio.list(filter);
		System.out.println(String.valueOf(db));
		jsonA.put(db);
		System.out.println(jsonA.length());
		if(jsonA.length() > 0) {
			return jsonA;
		}else {
			return null;
		}
	}
}
