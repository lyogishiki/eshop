package com.eshop.inventory.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.eshop.inventory.dao.RedisDao;
import com.eshop.inventory.mapper.ProductInventoryMapper;
import com.eshop.inventory.model.ProductInventory;
import com.eshop.inventory.service.ProductInventoryService;

public class ProductInventoryServiceImpl implements ProductInventoryService{

	@Autowired
	private ProductInventoryMapper productInventoryMapper;
	
	@Autowired
	private RedisDao redisDao;
	
	@Override
	public void updateProductInventory(ProductInventory productInventory) {
		productInventoryMapper.updateProductInventory(productInventory);
	}

	@Override
	public void removeProductInventoryCache(ProductInventory productInventory) {
		String key = "product:inventory:" + productInventory.getProductId();
		redisDao.delete(key);
	}

	@Override
	public ProductInventory findProductInventory(Integer productId) {
		return productInventoryMapper.findProductInventory(productId);
	}

	@Override
	public void setProductInventoryCache(ProductInventory productInventory) {
		String key = "product:inventory:" + productInventory.getProductId();
		redisDao.setByJedisCluster(key, String.valueOf(productInventory.getInventoryCnt()));
	}

	@Override
	public ProductInventory getProductInventoryCache(Integer productId) {
		String key = "product:inventory:" + productId;
		String result = redisDao.getByJedisCluster(key);
		if(StringUtils.isNotBlank(result)) {
			try {
				Long inventoryCnt = Long.parseLong(result);
				ProductInventory productInventory = new ProductInventory(productId, inventoryCnt);
				return productInventory;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	

}
