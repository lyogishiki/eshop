package com.eshop.inventory.request;

public interface Request {

	void process();
	
	Integer getDistributeKey();
	
	boolean isForceRefresh();
}
