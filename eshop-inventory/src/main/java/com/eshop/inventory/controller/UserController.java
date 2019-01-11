package com.eshop.inventory.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eshop.inventory.model.User;
import com.eshop.inventory.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/getUserInfo")
	public User getUserInfo() {
		return userService.findUserInfo();
	}

	@RequestMapping("getCachedUserInfoByJedisCluster")
	public User getCachedUserInfoByJedisCluster(@RequestParam("name") String key) {
		return userService.getCachedUserInfoByJedisCluster(key);
	}

	@RequestMapping("setCachedUserInfoByJedisCluster")
	public User setCachedUserInfoByJedisCluster(User user) {
		userService.setCachedUserInfoByJedisCluster(user);
		return user;
	}

	@RequestMapping("getCachedUserInfoByRedisTemplate")
	public User getCachedUserInfoByRedisTemplate(@RequestParam("name") String key) {
		return userService.getCachedUserInfoByRedisTemplate(key);
	}

	@RequestMapping("setCachedUserInfoByRedisTemplate")
	public User setCachedUserInfoByRedisTemplate(User user) {
		userService.setCachedUserInfoByRedisTemplate(user);
		return user;
	}
	
	@RequestMapping("getDate")
	public Date getDate() {
		return new Date();
	}

}
