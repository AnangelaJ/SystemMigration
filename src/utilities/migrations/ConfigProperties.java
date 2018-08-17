package utilities.migrations;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {
	
	private String url = null;
	private static Properties prop = null;
	
	public ConfigProperties(String url) {
		this.url = url;
		prop = new Properties();
		
		try {
			prop.load(new FileReader(this.url));
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public String parameter(String name) {
		if(prop != null) {
			return prop.getProperty(name);
		}else {
			return null;
		}
	}

}
