package com.test.one;

import static org.testng.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	String path,path2;
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
		headermap = new HashMap<String, String>();
		headermap.put("Content-Type", "application/json");
		restClient = new RestClient();
		path2 = "E://result.xlsx";
		map = new HashMap<String, List<String>>();
		
	}

	@DataProvider(name = "apiData")
	public Object[][] apiData() throws Exception {
		return ExcelUtil1.readExcelData(path, 0);
	}

	@Test(dataProvider="apiData")
	public void f(String num, String type, String url, String request,String expectedResult, String expectedCode, String Code2, String response, String result) throws IOException {
		if (type.equals("POST")) {
			closeableHttpResponse = restClient.post(url, request, headermap);
		}else {
			closeableHttpResponse = restClient.get(url);
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
		ExcelUtil1.writeExcel(list, path2);
		System.out.println("测试结束");
	}

}
