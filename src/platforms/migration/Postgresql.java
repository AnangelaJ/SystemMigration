package platforms.migration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import dbConnect.migrations.DBPostgresql;

public class Postgresql {

	private Connection conn = null;
	
	public Postgresql() {
		if(DBPostgresql.connPostgresql != null) {
			conn = DBPostgresql.connPostgresql;
		}
	}
	
	public JSONArray getTable() {
		if(conn != null) {
//			JSONObject jsonO = null;
			JSONArray jsonA = null;
			ResultSet rs = null;
			DatabaseMetaData dbmd = null;
//			String primaryKey = null;
			ArrayList<String> tables = new ArrayList<String>();
			try {
				jsonA = new JSONArray();
				dbmd = conn.getMetaData();
				rs = dbmd.getTables(null, "public", "%", null);
				
				while(rs.next()) {
					tables.add(rs.getObject(3).toString());
//					System.out.println("table: " + tables.toString());
//					ResultSet rsp = dbmd.getPrimaryKeys(null, null, rs.getObject(3).toString());
//					if(rsp.next()) {
//						primaryKey = rsp.getObject(4).toString();
//						rsp.close();
//					}
				}
				
				jsonA.put(tables);
//				jsonO.put("PrimaryKey", primaryKey);
				return jsonA;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}finally {
				try {
					if(rs != null) {
						rs.close();
						tables.clear();
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}else {
			return null;
		}
	}
	
	public JSONArray getColumns(String table) {
		if(conn != null) {
			System.out.println("INFOCOLUMNS");
			JSONObject jsonO = null;
			JSONArray jsonA = null;
			ResultSet rs = null;
			DatabaseMetaData dbmd = null;
			try {
				jsonA = new JSONArray();
				dbmd = conn.getMetaData();
				rs = dbmd.getColumns(null, null, table, null);
				while(rs.next()) {
					jsonO = new JSONObject();
					jsonO.put("columnName", rs.getString(4));
					jsonO.put("ColumnType", rs.getString(6));
					jsonA.put(jsonO);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}finally {
				try {
					rs.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
			return jsonA;
		}else {
			return null;
		}
	}
	
	public JSONArray Data(String table) {
		if(conn != null) {
//			JSONObject jsonO = null;
			JSONArray jsonA = null;
			Statement stmt = null;
			ResultSet rs = null;
			String columns [] = null;
			String aux [] = null;
			String query = "select * from " + table;
//			JSONArray jsonAux = null;
				try {
					jsonA = new JSONArray();
					stmt = conn.createStatement();
					System.out.println(query);
					rs = stmt.executeQuery(query);
					ResultSetMetaData rsmd = rs.getMetaData();
					if(rs != null) {
						ArrayList<String[]> ResultA = new ArrayList<String[]>();
						columns = new String [rsmd.getColumnCount()];
						for(int i = 0; i < rsmd.getColumnCount(); i++) {
							columns[i] = rsmd.getColumnName(i+1);
						} 
						
						while(rs.next()) {
							aux = new String [rsmd.getColumnCount()];
							for (int i = 0; i < rsmd.getColumnCount(); i++) {
								aux[i] = rs.getString(columns[i]);
							}
							ResultA.add(aux);						
						}
						
						for(int i = 0; i < ResultA.size(); i++) {
//							jsonO = new JSONObject();
							for (int j = 0 ; j < rsmd.getColumnCount(); j++) {
								System.out.println(ResultA.get(i)[j]);
								String [] jsonAux = new String [2];
//								jsonAux = new JSONArray();
//								jsonO.put(columns[j], ResultA.get(i)[j]);
//								jsonAux.put(columns[j]);
//								jsonAux.put(ResultA.get(i)[j]);
								jsonAux[0] = columns[j];
								jsonAux[1] = ResultA.get(i)[j];
								jsonA.put(jsonAux);  
							}
//							jsonA.put(jsonO);
//							jsonA.put(jsonAux);
							System.out.println(jsonA);
						}  
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					return null;
				}
				return jsonA;
			}else {
				return null;
			}
	}
}
