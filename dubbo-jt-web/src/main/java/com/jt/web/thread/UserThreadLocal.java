package com.jt.web.thread;

import com.jt.web.pojo.User;

public class UserThreadLocal {
	/*参数：
	 * 如果需要传递多值：则使用Map集合封装
	 * 
	 */
	private static ThreadLocal<User> userThread=
									new ThreadLocal();
	
	public static void set(User user){
		
		userThread.set(user);
	}
	
	public static User get(){
		
		return userThread.get();
	}
	//防止内存泄漏
	public static void remove(){
		//移除
		userThread.remove();
	}
	
}
