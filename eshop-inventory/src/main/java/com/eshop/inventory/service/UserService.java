package com.eshop.inventory.service;

import com.eshop.inventory.model.User;

public interface UserService {

	public User findUserInfo();
	
	public User getCachedUserInfoByJedisCluster(String key);
	
	public void setCachedUserInfoByJedisCluster(User user);
	
	public User getCachedUserInfoByRedisTemplate(String key);
	
	public void setCachedUserInfoByRedisTemplate(User user);
}
