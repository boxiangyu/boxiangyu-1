package com.jason.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
	public Properties properties;
	public int RESPNSE_STATUS_CODE_200 = 200;
	public int RESPNSE_STATUS_CODE_201 = 201;
	public int RESPNSE_STATUS_CODE_404 = 404;
	public int RESPNSE_STATUS_CODE_500 = 500;
	public TestBase() {
		properties = new Properties();
		try {
			FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/jason/config/config.properties");
			try {
				properties.load(fileInputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
