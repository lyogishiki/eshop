package com.eshop.inventory.request;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.eshop.inventory.model.ProductInventory;
import com.eshop.inventory.service.ProductInventoryService;

/**
 * 从数据库中查询，然后在写入缓存中。
 * 
 * @author ghost
 *
 */
public class ProductInventoryCacheReloadRequest implements Request{

	private ProductInventory productInventory;
	
	private ProductInventoryService productInventoryService;
	
	/**
	 * 是否强制刷新缓存
	 */
	private boolean forceRefresh;
	
	public ProductInventoryCacheReloadRequest(ProductInventory productInventory,
			ProductInventoryService productInventoryService) {
		super();
		this.productInventory = productInventory;
		this.productInventoryService = productInventoryService;
		this.forceRefresh = false;
	}
	
	public ProductInventoryCacheReloadRequest(ProductInventory productInventory,
			ProductInventoryService productInventoryService , boolean forceRefresh) {
		super();
		this.productInventory = productInventory;
		this.productInventoryService = productInventoryService;
	}

	@Override
	public void process() {
//		拿到商品数量
		ProductInventory inventoryResult = productInventoryService.findProductInventory(productInventory.getProductId());
//		将最新的商品数量刷新到缓存中
		productInventoryService.setProductInventoryCache(inventoryResult);
	}

	@Override
	public Integer getDistributeKey() {
		return productInventory.getProductId();
	}

	@Override
	public boolean isForceRefresh() {
		return forceRefresh;
	}

}
