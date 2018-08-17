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

import platforms.migration.Excel;
import utilities.migrations.DataBases;

/**
 * Servlet implementation class ExcelRoute
 */
@WebServlet("/Excel")
public class ExcelRoute extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExcelRoute() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String method = request.getParameter("method");
		JSONArray jsonA = new JSONArray();
		JSONObject jsonO = new JSONObject();
		Excel excel = new Excel();
		String table;
		
		switch(method) {
			case "getData":
				table = request.getParameter("table");
				jsonA = excel.Data(table);
				if(jsonA != null) {
					jsonO.put("data", jsonA);
					jsonO.put("status", 200);
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Internal Server error");
				}
				out.print(jsonO);
				break;
				
			case "getInfoColumns":
				table = request.getParameter("table").trim();
				jsonA = excel.getColumns(table);
				if(jsonA != null) {
					jsonO.put("infoColumns", jsonA);
					jsonO.put("status", 200);
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Internal Server error");
				}
				out.print(jsonO);
				break;
			
			case "getTables":
				jsonA = excel.getTables();
				if(jsonA != null) {
					jsonO.put("tables", jsonA);
					jsonO.put("status", 200);
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Internal Server error");
				}
				out.print(jsonO);
				break;
				
			case "getDataBases":
				DataBases db = new DataBases();
				jsonA = db.ListDBExcel();
				if(jsonA != null) {
					jsonO.put("databases", jsonA);
					jsonO.put("status", 200);
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Internal Server error");
				}
				out.print(jsonO);
				break;
			default:
				jsonO.put("status", 404);
				jsonO.put("message", "Page not found");
				break;
		}

		
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
		Excel excel;
		switch(method) {
			case "connectParam":
				String url = reqbody.getString("url");
				excel = new Excel(url);
				if(excel.connect()) {
					jsonO.put("status", 200);
					jsonO.put("message", "Connection successfully");
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Connection bad");
				}
				break;
			case "connectProp":
				excel = new Excel();
				if(excel.connect()) {
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
