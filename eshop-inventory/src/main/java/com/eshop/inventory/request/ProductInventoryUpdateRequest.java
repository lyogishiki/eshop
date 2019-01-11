package com.eshop.inventory.request;

import org.springframework.beans.factory.annotation.Autowired;

import com.eshop.inventory.model.ProductInventory;
import com.eshop.inventory.service.ProductInventoryService;

/**
 * 商品发生了交易，那么就要修改这个商品对应的库存，
 * 此时就会发生更新操作，要求更新库存。
 * @author ghost
 *
 */
public class ProductInventoryUpdateRequest implements Request {

	private ProductInventory productInventory;
	
	/**
	 * 商品库存service
	 */
	private ProductInventoryService productInventoryService;

	public ProductInventoryUpdateRequest(ProductInventory productInventory,
			ProductInventoryService productInventoryService) {
		super();
		this.productInventory = productInventory;
		this.productInventoryService = productInventoryService;
	}

	@Override
	public void process() {
		// 删除redis中缓存数据
		productInventoryService.removeProductInventoryCache(productInventory);
		// 修改数据库中的记录
		productInventoryService.updateProductInventory(productInventory);
	}

	@Override
	public Integer getDistributeKey() {
		return productInventory.getProductId();
	}

	@Override
	public boolean isForceRefresh() {
		return false;
	}

}
