package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.SysUserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private SysUserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	
	@RequestMapping("/{moduleName}")
	public String module(@PathVariable String moduleName){
		
		return moduleName;
	}
	//用户注册
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user){
		try {
			userService.saveUser(user);
			
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201,"用户新增失败");
	}
	//登录页面
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult findUserByUP(User user,
							HttpServletResponse response){
		try {
			//获取加密后的秘钥
			String token=userService.findUserByUP(user);
			
			//判断token是否为空
			if(StringUtils.isEmpty(token)){
				throw new RuntimeException();
			}
			//将token写入cookie中响应给客户端
			Cookie cookie = new Cookie("JT_TICKET",token);
			cookie.setPath("/");//保存到根目录
			//0 立即销毁    -1会话关闭后cookie销毁
			cookie.setMaxAge(60*60*24*7);//7天免密登录
			response.addCookie(cookie);//写入响应对象
			
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201,"用户登录失败");
	}
	
	//用户登出时删除1。redis缓存，删除2.Cookie
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,
						HttpServletResponse response){
		
		Cookie[] cookies = request.getCookies();
		
		String token=null;
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())){
				
				token=cookie.getValue();
				break;
			}
		}
		jedisCluster.del(token);
		Cookie cookie = new Cookie("JT_TICKET","");
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		
		return "redirect:/index.html";
	}
	
	
	
	

}
