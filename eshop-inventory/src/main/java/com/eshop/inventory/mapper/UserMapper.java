package com.eshop.inventory.mapper;

import com.eshop.inventory.model.User;

/**
 * 测试用户接口
 * @author ghost
 *
 */
public interface UserMapper {
 
	/**
	 * 查询用户信息
	 * @return
	 */
	public User findUserInfo();
}
