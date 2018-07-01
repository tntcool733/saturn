package com.tnt.springbootpractice.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tnt.springbootpractice.facade.UserFacade;
import com.tnt.springbootpractice.service.RoleService;
import com.tnt.springbootpractice.service.UserService;

@Service
public class UserFacadeImpl implements UserFacade{

	//使用构造函数注入无需每个依赖bean配置Autowired注解
	//@Autowired
	private final UserService userService;
	private final RoleService roleService;

	//可以不配置Autowired注解，若此bean只有一个构造函数
	//@Autowired
	public UserFacadeImpl(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}


}
