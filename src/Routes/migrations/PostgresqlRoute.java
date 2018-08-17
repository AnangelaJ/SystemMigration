package Routes.migrations;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import dbConnect.migrations.DBPostgresql;
import platforms.migration.Postgresql;
import utilities.migrations.DataBases;

/**
 * Servlet implementation class PostgresqlRoute
 */
@WebServlet("/PostgreSql")
public class PostgresqlRoute extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostgresqlRoute() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String method = request.getParameter("method");
		JSONArray jsonA = new JSONArray();
		JSONObject jsonO = new JSONObject();
		Postgresql pg = new Postgresql();
		String table = null;
		
		switch(method) {
			case "getInfoColumns":
				table = request.getParameter("table").trim();
				System.out.println("Table: " + table);
				jsonA = pg.getColumns(table);
				if(jsonA != null) {
					jsonO.put("status", 200);
					jsonO.put("infoColumns", jsonA);
				}else {
					jsonO.put("status", 500);
				}
				break;
			case "getTables":
				jsonA = pg.getTable();
				if(jsonA != null) {
					jsonO.put("status", 200);
					jsonO.put("tables", jsonA);
				}else {
					jsonO.put("status", 500);
				}
				
				break;
			case "getData":
				table = request.getParameter("table").trim();
				jsonA = pg.Data(table);
				if(jsonA != null) {
					jsonO.put("status", 200);
					jsonO.put("data", jsonA);
				}else {
					jsonO.put("status", 500);
				}
				System.out.println("Json: " + String.valueOf(jsonO));
				out.print(jsonO);
				break;
			case "getDataBases":
				DataBases db = new DataBases();
				jsonA = db.ListDBPg(); 
				if(jsonA != null) {
					jsonO.put("databases", jsonA);
					jsonO.put("status", 200);
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Internal Server error");
				}
				break;
			case "desconnect":
				if(DBPostgresql.desconnect()) {
					jsonO.put("status", 200);
				}else {
					jsonO.put("status", 500);
				}
				break;
			default:
				jsonO.put("status", 404);
				jsonO.put("message", "Page not found");
				break;
		}
		
		out.print(jsonO);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject reqbody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		PrintWriter out = response.getWriter();
		String method = request.getParameter("method");
		JSONObject jsonO = new JSONObject();
		System.out.println("Metodo: " + method);
		
		switch(method) {
			case "connectProp":
				String url = reqbody.getString("url");
				System.out.println(url);
				DBPostgresql conn = new DBPostgresql(url);
				if(conn.connect() != null) {
					jsonO.put("status", 200);
					jsonO.put("message", "Connection successfully");
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Connection successfully");
				}
				out.print(jsonO);
				break;
			case "connectParam":
				String host = reqbody.getString("host");
				String port = String.valueOf(reqbody.getInt("port"));
				String db = reqbody.getString("db");
				String user = reqbody.getString("user");
				String pass = reqbody.getString("password");				
				
				DBPostgresql connParam = new DBPostgresql(host, port, db, user, pass);
				if(connParam.connect() != null) {
					jsonO.put("status", 200);
					jsonO.put("message", "Connection successfully");
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Connection successfully");
				}
				out.print(jsonO);
				break;
			default:
				jsonO.put("status", 404);
				jsonO.put("message", "Page not found");
				break;
		}
	}

}
