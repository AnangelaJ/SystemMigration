package platforms.migration;

import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
import java.util.ArrayList;
//import java.util.ArrayList;
//import java.util.Iterator;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import jxl.Sheet;
import jxl.Workbook;
//import jxl.read.biff.BiffException;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.read.biff.BiffException;
import utilities.migrations.ConfigProperties;


public class Excel {
	
	private String url = null;
	
	public Excel(String url) {
		this.url = url;
//		System.out.println(url);
	}
	
	public Excel() {
		ConfigProperties prop = new ConfigProperties("C:\\Properties\\Excel.properties");
		this.url = prop.parameter("url");
	}
	
	public boolean connect() {
		if(this.url == null) {
			return false;
		}else {
			return true;
		}
	}
	
	public JSONArray Data(String table) {
		if(this.url != null) {
			Workbook wb = null;
			Sheet sheet = null;
			String columns [] = null;
			JSONArray jsonA = null;
			JSONObject jsonO = null;
			ArrayList <String[]> ResultA = new ArrayList<String[]>();
			
			try {
				System.out.println(this.url);
				wb = Workbook.getWorkbook(new File(this.url));
				
				jsonA = new JSONArray();
				sheet = wb.getSheet(table);
				columns = new String [sheet.getColumns()];
				for(int i = 0; i < sheet.getColumns(); i++) {
					columns[i] = sheet.getCell(i, 0).getContents();
					System.out.println("Formatoh: " + sheet.getCell(i,0).getCellFormat().getFormat());	
				}
				
				for(int i = 1; i < sheet.getRows(); i++) {
					String aux [] = new String [sheet.getColumns()];
					for(int j = 0; j < sheet.getColumns(); j++) {
						aux[j] = sheet.getCell(j, i).getContents();
					}
					ResultA.add(aux);
				}
				
				
				for(int i = 0; i < ResultA.size(); i++) {
					jsonO = new JSONObject();
					for (int j = 0 ; j < sheet.getColumns(); j++) {
						System.out.println(ResultA.get(i)[j]);
						jsonO.put(columns[j], ResultA.get(i)[j]);
					}
					jsonA.put(jsonO);
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
	
	public JSONArray getColumns(String table) {
		if(this.url != null) {
			Workbook wb = null;
			Sheet sheet = null;
			String columns [] = null;
			String columnsType [] = null;
			JSONArray jsonA = null;
			JSONObject jsonO = null;
//			ArrayList <String[]> ResultA = new ArrayList<String[]>();
			
			try {
				System.out.println(this.url);
				wb = Workbook.getWorkbook(new File(this.url));
				
				jsonA = new JSONArray();
				jsonO = new JSONObject();
				sheet = wb.getSheet(table);
				columns = new String [sheet.getColumns()];
				columnsType = new String [sheet.getColumns()];
				for(int i = 0; i < sheet.getColumns(); i++) {
					columns[i] = sheet.getCell(i, 0).getContents();
					columnsType[i] = String.valueOf(sheet.getCell(i, 1).getType());
					System.out.println("Formatoh: " + sheet.getCell(i, 0).getType());	
					jsonO.put(columns[i], columnsType[i]);
				}
				
				jsonA.put(jsonO);
				
				
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
	
	public JSONArray getTables() {
		if(this.url != null) {
			Workbook wb = null;
			String tables [];
			JSONArray jsonA = null;
			try {
				System.out.println(this.url);
				wb = Workbook.getWorkbook(new File(this.url));
				
				jsonA = new JSONArray();
				
				tables = new String [wb.getNumberOfSheets()];
				
				tables = wb.getSheetNames();
				jsonA.put(tables);				
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
