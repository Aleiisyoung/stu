package com.jt.web.service;

import com.jt.web.pojo.User;

public interface SysUserService {

	void saveUser(User user);

	String findUserByUP(User user);

}
