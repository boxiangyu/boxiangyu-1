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
	//1、GET请求(无请求头)
	public CloseableHttpResponse get(String url) throws ClientProtocolException,IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();//创建一个可关闭的httpclient对象
		HttpGet httpGet = new HttpGet(url);//创建一个httpget的请求对象
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);//执行请求
		return closeableHttpResponse;
	}
	
	//2、GET请求（有请求头）
	public CloseableHttpResponse get(String url,HashMap<String, String> headermap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		for (Map.Entry<String, String> entry : headermap.entrySet()) {
			httpGet.addHeader(entry.getKey(),entry.getValue());
		}
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
		return closeableHttpResponse;
	}
	
	//3、POST请求
	public CloseableHttpResponse post(String url,String entityString, HashMap<String, String> headermap) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		//设置payload
		httpPost.setEntity(new StringEntity(entityString));
		//加载请求头到httppost对象
		for (Map.Entry<String, String> entry : headermap.entrySet()) {
			httpPost.addHeader(entry.getKey(), entry.getValue());
		}
		//发送post请求
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);
		return closeableHttpResponse;
	}

}
