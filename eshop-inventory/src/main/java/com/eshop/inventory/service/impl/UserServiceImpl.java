package com.eshop.inventory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eshop.inventory.dao.RedisDao;
import com.eshop.inventory.mapper.UserMapper;
import com.eshop.inventory.model.User;
import com.eshop.inventory.service.UserService;
import com.eshop.inventory.util.JsonUtil;

@Service("userService")
@Transactional(readOnly=false)
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RedisDao redisDao;
	
	@Override
	public User findUserInfo() {
		return userMapper.findUserInfo();
	}

	@Override
	public User getCachedUserInfoByJedisCluster(String key) {
		String json = redisDao.getByJedisCluster(key);
		return JsonUtil.parseObject(json, User.class);
	}

	@Override
	public void setCachedUserInfoByJedisCluster(User user) {
		redisDao.setByJedisCluster(user.getName(), JsonUtil.parseJson(user));
	}

	@Override
	public User getCachedUserInfoByRedisTemplate(String key) {
		String json = (String) redisDao.getByRedisTemplate(key);
		return JsonUtil.parseObject(json, User.class);
		
	}

	@Override
	public void setCachedUserInfoByRedisTemplate(User user) {
		redisDao.setByRedisTemplate(user.getName(), user);
	}

}
