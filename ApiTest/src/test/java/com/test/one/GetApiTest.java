package com.test.one;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jason.base.TestBase;
import com.jason.restclient.RestClient;

public class GetApiTest extends TestBase{
	TestBase testBase;
	String host;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	
	@BeforeClass
	public void setUp() {
		//testBase = new TestBase();
		host = properties.getProperty("HOST");//取得properties文件中的HOST值
		url = host +"/api/users?page=2";
	}
		
	@Test
	public void getApiTest() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(url);
		
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		assertEquals(statusCode, RESPNSE_STATUS_CODE_200, "response status code is not 200");
		//把响应内容存储在字符串对象。httpresponse对象调用getEntity方法，取得响应报文中的报文主体内容，然后EntityUtils直接调用静态方法toString
		//把报文主体内容转换为utf-8编码的字符串
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		System.out.println(responseString);
	}

}
