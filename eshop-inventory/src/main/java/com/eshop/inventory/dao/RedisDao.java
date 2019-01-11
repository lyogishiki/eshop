package com.eshop.inventory.dao;

public interface RedisDao {

	public void setByJedisCluster(String key,String value);
	public String getByJedisCluster(String key);
	
	public void setByRedisTemplate(Object key,Object value);
	public Object getByRedisTemplate(Object key);
	
	public void delete(String key);
	
}
