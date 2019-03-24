package com.jason.restclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {
	//1��GET����(������ͷ)
	public CloseableHttpResponse get(String url) throws ClientProtocolException,IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();//����һ���ɹرյ�httpclient����
		HttpGet httpGet = new HttpGet(url);//����һ��httpget���������
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);//ִ������
		return closeableHttpResponse;
	}
	
	//2��GET����������ͷ��
	public CloseableHttpResponse get(String url,HashMap<String, String> headermap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		for (Map.Entry<String, String> entry : headermap.entrySet()) {
			httpGet.addHeader(entry.getKey(),entry.getValue());
		}
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
		return closeableHttpResponse;
	}
	
	//3��POST����
	public CloseableHttpResponse post(String url,String entityString, HashMap<String, String> headermap) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		//����payload
		httpPost.setEntity(new StringEntity(entityString));
		//��������ͷ��httppost����
		for (Map.Entry<String, String> entry : headermap.entrySet()) {
			httpPost.addHeader(entry.getKey(), entry.getValue());
		}
		//����post����
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
		return closeableHttpResponse;
	}

}
