package com.eshop.inventory;

import java.io.IOException;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.eshop.inventory.listener.InitListener;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@SpringBootApplication
@MapperScan(basePackages= {"com.eshop.inventory.mapper"})
@ServletComponentScan
public class InventoryApplication {

	/**
	 * 使用hikari 连接池
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariDataSource  datasource() {
		return (HikariDataSource)DataSourceBuilder.create().type(HikariDataSource.class)
		.build();
	}
	
	/**
	 * 这个可以不写，因为Spring Boot会有默认的SQLSessionBean,
	 * 在配置文件中需要配置mybatis xml文件位置
	 * @return
	 * @throws Exception
	 */
//	@Bean
//	public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
//		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//		sqlSessionFactoryBean.setDataSource(dataSource);
//		
//		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/com/**/*.xml"));
//		return sqlSessionFactoryBean.getObject();
//	}
	
	/**
	 * 构建事务管理器
	 * @param dataSource
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	/**
	 * JedisCluster配置
	 * 可以不用,直接用redis.cluster.nodes配置项就可以了.
	 * 
	 */
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> redisClusterNodes = new HashSet<>();
		redisClusterNodes.add(new HostAndPort("92.168.0.222", 7004));
		redisClusterNodes.add(new HostAndPort("192.168.0.222", 7003));
		redisClusterNodes.add(new HostAndPort("192.168.0.223", 7006));
		JedisCluster jedisCluster = new JedisCluster(redisClusterNodes);
		return jedisCluster;
	}
	
	
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = 
				new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper om = new ObjectMapper();
		
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		
		StringRedisSerializer stringSerializer = new StringRedisSerializer();
		template.setValueSerializer(jackson2JsonRedisSerializer);//使用jackson2JsonRedisSeriallizer
		template.setKeySerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		//使用stringRedisSerializer
		template.setDefaultSerializer(stringSerializer);
//		template.setHashKeySerializer(hashKeySerializer);
//		template.setHashValueSerializer(hashValueSerializer);
		return template;
	}
	
	/**
	 * 使用ServletListenerRegistrationBean 来增加新的Listener
	 * 在Web3.0 之后 可以使用@WebListener注解增加监听器，所以这个就看看
	 * 需要添加@ServletComponentScan注解才行。
	 */
	/*@Bean
	public ServletListenerRegistrationBean<EventListener> servletListenerRegistrationBean(){
		ServletListenerRegistrationBean<EventListener> servletListenerRegistrationBean = 
				new ServletListenerRegistrationBean<>();
		servletListenerRegistrationBean.setListener(new InitListener());
		servletListenerRegistrationBean.setOrder(1);
		return servletListenerRegistrationBean;
	}*/
	
	
	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}
	
}
