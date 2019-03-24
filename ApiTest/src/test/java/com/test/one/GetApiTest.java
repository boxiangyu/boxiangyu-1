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
		host = properties.getProperty("HOST");//ȡ��properties�ļ��е�HOSTֵ
		url = host +"/api/users?page=2";
	}
		
	@Test
	public void getApiTest() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(url);
		
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		assertEquals(statusCode, RESPNSE_STATUS_CODE_200, "response status code is not 200");
		//����Ӧ���ݴ洢���ַ�������httpresponse�������getEntity������ȡ����Ӧ�����еı����������ݣ�Ȼ��EntityUtilsֱ�ӵ��þ�̬����toString
		//�ѱ�����������ת��Ϊutf-8������ַ���
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		System.out.println(responseString);
	}

}
