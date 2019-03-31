package com.test.one;

import static org.testng.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jason.base.TestBase;
import com.jason.restclient.RestClient;
import com.jason.utils.ExcelUtil1;

public class GetApiTest2 extends TestBase {
	String path,path2,host;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	HashMap<String, String> headermap;
	String string;
	String result;
	Map<String,List<String>> map;
	List<List> list;
	Boolean flag1,flag2=false;

	@BeforeClass
	public void beforeClass() {
		path = properties.getProperty("TESTCASE_PATH");
		host = properties.getProperty("HOST");
		headermap = new HashMap<String, String>();
		headermap.put("Content-Type", "application/json");
		restClient = new RestClient();
		path2 = properties.getProperty("RESULT_PATH");
		map = new HashMap<String, List<String>>();
		
	}

	@DataProvider(name = "apiData")
	public Object[][] apiData() throws Exception {
		return ExcelUtil1.readExcelData(path, 0);
	}

	@Test(dataProvider="apiData")
	public void f(String num, String type, String url, String request,String expectedResult, String expectedCode, String Code2, String response, String result) throws IOException {
		if (type.equals("POST")|| type.equals("post")) {
			closeableHttpResponse = restClient.post(host+url, request, headermap);
		}else if(type.equals("GET")|| type.equals("get")){
			closeableHttpResponse = restClient.get(host+url);
		}
		int actualCode = closeableHttpResponse.getStatusLine().getStatusCode();
		List<String> stringList = new ArrayList<String>();
		stringList.add(num);
		stringList.add(type);
		stringList.add(url);
		stringList.add(request);
		stringList.add(expectedResult);
		stringList.add(expectedCode);
		stringList.add(String.valueOf(actualCode));
		Assert.assertEquals(actualCode, Integer.parseInt(expectedCode), "response status code is not 201");
		if (actualCode == Integer.parseInt(expectedCode)) {
			flag1 = true;
		}else {
			flag1 = false;
		}
		string = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		stringList.add(string);
		flag2 = false;//初始化flag为false
		if(string.contains(expectedResult)) {
			flag2 = true;
		}else {
			flag2 = false;
		}
		
		if (flag1&&flag2) {
			result = "PASS";
		}else {
			result= "FAIL";
		}
		stringList.add(result);
		map.put(num, stringList);
	}

	@AfterClass
	public void afterClass() throws FileNotFoundException {
		list = ExcelUtil1.convertMapToList(map);
		Date date = new Date();
		String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		String resultPath = path2+"/"+filename+".xlsx";
		ExcelUtil1.writeExcel(list, resultPath);
		System.out.println("测试结束");
	}

}
