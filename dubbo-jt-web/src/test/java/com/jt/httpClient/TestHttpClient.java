package com.jt.httpClient;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {
	
	@Test//get提交
	public void testGet() throws Exception, IOException{
		
		String url="http://www.tmooc.cn/course/100010.shtml";
		//定义请求对象
		HttpGet httpGet=new HttpGet(url);//构造
		//HttpPost httpPost = new HttpPost(url);
		//定义httpClient
		CloseableHttpClient httpClient =
				HttpClients.createDefault();
		//获取httpResponse对象
		CloseableHttpResponse httpResponse = 
				httpClient.execute(httpGet);//执行请求
		//判断状态:状态行状态码
		if(httpResponse.getStatusLine().getStatusCode()==200){
			//请求正确，获取正确的响应实体数据
			String data=(EntityUtils.toString(httpResponse.getEntity()));
			System.out.println(data);
		}
	}

}
