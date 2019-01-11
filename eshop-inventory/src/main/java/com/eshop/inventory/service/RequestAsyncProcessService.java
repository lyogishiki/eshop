package com.eshop.inventory.service;

import com.eshop.inventory.request.Request;

/**
 * 请求异步执行的Service
 * @author ghost
 *
 */
public interface RequestAsyncProcessService {

	void process(Request request);
	
}
