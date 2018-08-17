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

import platforms.migration.Txt;
import utilities.migrations.DataBases;

/**
 * Servlet implementation class TxtRoute
 */
@WebServlet("/Txt")
public class TxtRoute extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TxtRoute() {
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
		Txt txt = new Txt();
		
		switch(method) {
			case "getData":
				jsonA = txt.Data();
				if(jsonA != null) {
					jsonO.put("data", jsonA);
					jsonO.put("status", 200);
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Internal Server error");
				}
				break;
			case "getInfoColumns":
				jsonA = txt.getColumns();
				if(jsonA != null) {
					jsonO.put("infoColumns", jsonA);
					jsonO.put("status", 200);
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Internal Server error");
				}
				break;
			case "getTables":
				jsonA = txt.getTable();
				if(jsonA != null) {
					jsonO.put("table", jsonA);
					jsonO.put("status", 200);
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Internal Server error");
				}
				break;
			case "getDataBases":
				DataBases db = new DataBases();
				jsonA = db.ListDBTxt();
				if(jsonA != null) {
					jsonO.put("databases", jsonA);
					jsonO.put("status", 200);
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Internal Server error");
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
		String url = null;
		Txt txt;
		switch(method) {
			case "connectParam":
				url = reqbody.getString("url");
				String folder = reqbody.getString("folder");
				txt = new Txt(url, folder);
				if(txt.connect()) {
					jsonO.put("status", 200);
					jsonO.put("message", "Connection successfully");
				}else {
					jsonO.put("status", 500);
					jsonO.put("message", "Connection bad");
				}
				break;
			case "connectProp":
				url = reqbody.getString("url");
				txt = new Txt(url);
				if(txt.connect()) {
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
