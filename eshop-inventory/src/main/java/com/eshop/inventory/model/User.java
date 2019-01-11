package com.eshop.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	/**
	 * 测试用户姓名
	 */
	private String name;
	/**
	 * 测试用户年龄
	 */
	private Integer age;
	
	public static void main(String[] args) {
		User user = new User();
		user.setAge(11);
		user.setName("zhangsan");
		System.out.println(user.toString());
	}
}
