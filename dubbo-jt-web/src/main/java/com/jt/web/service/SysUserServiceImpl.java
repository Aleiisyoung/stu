package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private HttpClientService httpClient;
	
	private static ObjectMapper objectMapper=new ObjectMapper();
	
	@Override
	public void saveUser(User user) {
		
		Map<String,String> params=new HashMap<>();
		//http://sso.jt.com/user/register
		String url="http://sso.jt.com/user/register";
		params.put("username",user.getUsername());
		params.put("password",user.getPassword());
		params.put("phone",user.getPhone());
		params.put("email",user.getPhone());//代替邮箱
		
		String resultJson = httpClient.post(url,params);
		try {
			SysResult sysResult = 
					objectMapper.readValue(resultJson, SysResult.class);
			if(sysResult.getStatus()!=200){
				throw new RuntimeException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public String findUserByUP(User user) {
		
		String url="http://sso.jt.com/user/login";
		Map<String,String> params=new HashMap<>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		//远程传输是基于HTTP协议，所以要用字符串接受
		String resultJson=httpClient.post(url, params);
		String token=null;
		try {
			
			SysResult sysResult = 
					objectMapper.readValue(resultJson, SysResult.class);
			if(sysResult.getStatus()!=200){
				throw new RuntimeException();
			}
			token=(String)sysResult.getData();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return token;
	}

}
