package com.eshop.inventory.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.eshop.inventory.model.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		OBJECT_MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//		OBJECT_MAPPER.enabeDefaultTyping(OBJECT_MAPPER.DefaultTyping.NON_FINAL);
		
		//序列化的时候序列对象的所有属性
		OBJECT_MAPPER.setSerializationInclusion(Include.ALWAYS);
		
		//反序列化的时候如果多了其他属性,不抛出异常
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		//如果是空对象的时候,不抛异常
		OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		//取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
		OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

		
	}

	public static String parseJson(Object obj) {
		try {
			return OBJECT_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static <T> T parseObject(String json, Class<T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(json, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		User user = new User("张三", 22);
		String json = parseJson(user);
		System.out.println(json);
		User user2 = parseObject(json, User.class);
		System.out.println(user2);

		System.out.println(parseJson("1234567"));
	}
}
