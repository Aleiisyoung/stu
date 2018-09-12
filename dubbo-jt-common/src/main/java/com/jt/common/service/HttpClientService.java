package com.jt.common.service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

@Service
public class HttpClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientService.class);

    @Autowired(required=false)
    private CloseableHttpClient httpClient;

    @Autowired(required=false)
    private RequestConfig requestConfig;

    /*1.编辑get请求
     * 动态拼接参数localhost:8091?id=1&name=tom
     * 
     */
    public String get(String url,
    					Map<String,String> params,//用map封装参数
    					String charset){
    	String result=null;
    	//1.判断字符集编码是否为空
    	if(StringUtils.isEmpty(charset)){
    		//默认编码
    		charset="UTF-8";
    	}
    	//2.判断参数是否为空
    		try {
    			if(params!=null){
    			//有参数
				URIBuilder builder = new URIBuilder(url);//提供工具类封装地址
				//遍历参数的Entry类型set集合
				for (Map.Entry<String,String> entry : params.entrySet()){
					//url+参数拼接
					builder.addParameter(entry.getKey(),entry.getValue());
				}	
					//生成get请求具体路径localhost:8091?itemId=1......
					url=builder.build().toString();
				}
				
				//3.定义请求对象
				HttpGet httpGet = new HttpGet(url);
				httpGet.setConfig(requestConfig);//设置请求配置，定义连接时长
				
				//4.发起远程请求 //http://manage.jt.com/web/item/findItemById
				CloseableHttpResponse httpResponse =
						httpClient.execute(httpGet);//执行后台得到响应
				//5.判断响应结果是否正确
				if(httpResponse.getStatusLine().getStatusCode()==200){//设置编码
					result=EntityUtils.toString(httpResponse.getEntity(),charset);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
    	}	
    		    		/*String paramUrl=url+"?";
    		for (Map.Entry<String,String> entry : params.entrySet()) {
    			
				paramUrl=paramUrl+entry.getKey()
							+"="+entry.getValue()+"&";
			}
    		paramUrl.substring(0, paramUrl.length()-1);*/
    
    	//为了满足用户需要 添加下列方法
    	public String get(String url){
    		
    		return get(url,null,null);
    	}
    	public String get(String url,Map<String,String> params){
    		
    		return get(url,params,null);
    	}
    	
    /*2.post提交
     * 
     */
    	public String post(String url,Map<String,String> params,String charset){
    		//判断字符编码
    		if(StringUtils.isEmpty(charset)){
    			charset="UTF-8";
    		}
    		//定义请求对象
    		HttpPost httpPost = new HttpPost(url);
    		httpPost.setConfig(requestConfig);//设置请求配置
    		//判断是否有参数
    		if(params!=null){
    		List<NameValuePair> parameters=new ArrayList<>();
    		
    		for (Map.Entry<String, String> entity : params.entrySet()) {
				BasicNameValuePair valuePair = //map取出键值对封装到对象
						new BasicNameValuePair(entity.getKey(), entity.getValue());
    			parameters.add(valuePair);//添加到集合
			}
			try {
				UrlEncodedFormEntity formEntity = 
						new UrlEncodedFormEntity(parameters, charset);
				httpPost.setEntity(formEntity);//设置参数实体内容
				
			} catch (Exception e) {
				e.printStackTrace();
			}
    		}	
    		String result=null;
			try {
    			//发起请求
    			CloseableHttpResponse httpResponse =
    					httpClient.execute(httpPost);
				if(httpResponse.getStatusLine().getStatusCode()==200){
					result=EntityUtils.toString(httpResponse.getEntity(),charset);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			return result;
    	}
    	
    	public String post(String url){
    		
    		return post(url,null,null);
    	}
    	public String post(String url,Map<String,String> params){
    		
    		return post(url,params,null);
    	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }

