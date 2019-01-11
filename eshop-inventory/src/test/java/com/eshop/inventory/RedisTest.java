package com.eshop.inventory;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.ClusterInfo;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private ObjectMapper om;
	
	@Test
	public void test01() {
		RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
		org.springframework.data.redis.connection.jedis.JedisClusterConnection clusterConnection = (JedisClusterConnection) connectionFactory.getClusterConnection();
		List<RedisClientInfo> list = clusterConnection.getClientList();
		ClusterInfo clusterInfo = clusterConnection.clusterGetClusterInfo();
//		clusterConnection.get
		System.out.println(list);
		
		
	}
	
	@Test
	public void test02() {
		System.out.println(om);	
	}
	
	
}
