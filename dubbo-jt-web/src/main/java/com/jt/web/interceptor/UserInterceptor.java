package com.jt.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.web.pojo.User;
import com.jt.web.thread.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

//拦截器
public class UserInterceptor implements HandlerInterceptor{
	@Autowired
	private JedisCluster jedisCluster;
	private ObjectMapper objectMapper=new ObjectMapper();

	@Override//在执行controller方法之前执行
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//判断客户端是否有cookie
		String token="";
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())){
				token=cookie.getValue();
				break;
			}
		}
		//判断token是否为空
		if(!StringUtils.isEmpty(token)){
			//检测redis是否有数据
			String userJSON = jedisCluster.get(token);
			System.out.println(userJSON);
			if(!StringUtils.isEmpty(userJSON)){
				//用户已经登录
				//userJSON转化为user对象
				User user=
					objectMapper.readValue(userJSON, User.class);
				//使用session共享
				//request.getSession().setAttribute("JT_USER",user);
				UserThreadLocal.set(user);
				return true;
			}
		}
		//表示用户没有登录,重定向
		response.sendRedirect("/user/login.html");
		
		return false;
	}

	@Override//执行完业务逻辑之后执行
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
	}

	@Override//返回页面之前拦截
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		//将UserThreadLocal数据清空
		UserThreadLocal.remove();
	}
	

}
