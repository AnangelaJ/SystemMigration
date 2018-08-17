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

import dbConnect.migrations.DBAccess;
import platforms.migration.Access;
import utilities.migrations.DataBases;

/**
 * Servlet implementation class AccessRoute
 */
@WebServlet("/Access")
public class AccessRoute extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccessRoute() {
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
		Access access = new Access();
		String table = null;
		
		switch(method) {
			case "getInfoColumns":
				table = request.getParameter("table").trim();
				System.out.println("Table: " + table);
				jsonA = access.getColumns(table);
				if(jsonA != null) {
					jsonO.put("status", 200);
					jsonO.put("infoColumns", jsonA);
				}else {
					jsonO.put("status", 500);
				}
				break;
			case "getTables":
				jsonA = access.getTable();
				if(jsonA != null) {
					jsonO.put("status", 200);
					jsonO.put("tables", jsonA);
				}else {
					jsonO.put("status", 500);
				}
				
				break;
			case "getDataBases":
				DataBases db = new DataBases();
				jsonA = db.ListDBAccess();
				if(jsonA != null) {
					jsonO = new JSONObject();
					jsonO.put("databases", jsonA);
					jsonO.put("status", 200);
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Internal Server error");
				}
				System.out.println("JSONO: " + String.valueOf(jsonO));
				break;
			case "getData":
				table = request.getParameter("table").trim();
				jsonA = access.Data(table);
				if(jsonA != null) {
					jsonO.put("status", 200);
					jsonO.put("data", jsonA);
				}else {
					jsonO.put("status", 500);
				}
				break;
			case "desconnect":
				if(DBAccess.desconnect()) {
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
				DBAccess conn = new DBAccess();
				if(conn.connect() != null) {
					jsonO.put("status", 200);
					jsonO.put("message", "Connection successfully");
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Connection bad");
				}
				break;
			case "connectParam":
				String url = reqbody.getString("url");				
				
				DBAccess connParam = new DBAccess(url);
				if(connParam.connect() != null) {
					jsonO.put("status", 200);
					jsonO.put("message", "Connection successfully");
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Connection bad");
				}
				break;
			default:
				jsonO.put("status", 404);
				jsonO.put("message", "Page not found");
				break;
		}
		out.print(jsonO);
	}

}
