package platforms.migration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import utilities.migrations.ConfigProperties;

public class Txt {

	private static String url = null;
	private static String folder = null;
	private File file = null;
	
	
	public Txt() {}
	
	public Txt(String url, String folder) {
		Txt.url = url;
		Txt.folder = folder;
	}
	
	public Txt(String url) {
		ConfigProperties prop = new ConfigProperties(url);
		Txt.url = prop.parameter("url");
		Txt.folder = prop.parameter("folder");
	}
	
	public boolean connect() {
		if(Txt.url == null && Txt.folder == null) {
			return false;
		}else {
			return true;
		}
	}
	
	//Reestructurar este metodo
	public JSONArray Data() {
		JSONArray jsonA = null;
		JSONObject jsonO = null;
		
		if(Txt.url != null) {
			ArrayList<String []> ResultA = new ArrayList<String []>();
			FileReader fr = null;
			BufferedReader br = null;
			try {
				file = new File(Txt.url);
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				
				String lines;
				String [] columns = null;
				int count = 0;
				int col = 0;
				while((lines = br.readLine())!=null) {
					count ++;
					String fields [];
					fields = lines.split(",");
					for(int i = 0; i < fields.length; i++) {
						fields[i] = fields[i].replace("\t", "");
						fields[i] = fields[i].trim();
					}
					if(count == 1) {
						col = fields.length;
						columns = new String[col];
						columns = fields;
					}else {						
						ResultA.add(fields);
					}
				}
				
				jsonA = new JSONArray();
				
				for(int i = 1; i < ResultA.size(); i++) {
					jsonO = new JSONObject();
					for (int j = 0 ; j < col; j++) {
						System.out.println(ResultA.get(i)[j]);
						jsonO.put(columns[j], ResultA.get(i)[j]);
					}
					jsonA.put(jsonO);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}finally {
				if(br != null) {
					try {
						br.close();
						fr.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}		
			return jsonA;
		}else {
			return null;
		}
	}
	
	public JSONArray getColumns() {
		JSONArray jsonA = null;
		JSONObject jsonO = null;
		
		if(Txt.url != null) {
			FileReader fr = null;
			BufferedReader br = null;
			try {
				file = new File(Txt.url);
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				
				String lines;
				String [] columns = null;
				String [] columnsType = null;
				int count = 0;
				int col = 0;
				while((lines = br.readLine())!=null) {
					count ++;
					String fields [];
					fields = lines.split(",");
					for(int i = 0; i < fields.length; i++) {
						fields[i] = fields[i].replace("\t", "");
						fields[i] = fields[i].trim();
					}
					if(count == 1) {
						col = fields.length;
						columns = new String[col];
						columns = fields;
					}else if(count == 2){	
						System.out.println("Holi");
						col = fields.length;
						columnsType = new String[col];
						columnsType = fields;
					}else {
						break;
					}
				}
				
				jsonA = new JSONArray();
				jsonO = new JSONObject();
				System.out.println("Columns: " + columns[0]);
				System.out.println("ColumnsType: " + columnsType[0]);
				for(int i = 0; i < 4; i++) {
					jsonO.put(columns[i], columnsType[i]);
				}
				
				jsonA.put(jsonO);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}finally {
				if(br != null) {
					try {
						br.close();
						fr.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}		
			return jsonA;
		}else {
			return null;
		}

	}
	
	public JSONArray getTable() {
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

		File directorio = new File("C:\\DataBases\\" + Txt.folder);
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
