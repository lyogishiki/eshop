package com.eshop.inventory.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.eshop.inventory.dao.RedisDao;

import redis.clients.jedis.JedisCluster;

@Repository
public class RedisDaoImpl implements RedisDao{

	@Autowired
	private JedisCluster jedisCluster;
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Override
	public void setByJedisCluster(String key,String value) {
		jedisCluster.set(key, value);
	}

	@Override
	public String getByJedisCluster(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public void setByRedisTemplate(Object key,Object value) {
		redisTemplate.boundValueOps(key).set(value);
	}

	@Override
	public Object getByRedisTemplate(Object key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void delete(String key) {
		jedisCluster.del(key);
	}
	
}
