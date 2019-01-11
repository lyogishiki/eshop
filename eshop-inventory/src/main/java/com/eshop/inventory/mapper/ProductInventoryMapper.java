package com.eshop.inventory.mapper;

import com.eshop.inventory.model.ProductInventory;

/**
 * 库存数量Mapper
 * @author ghost
 *
 */
public interface ProductInventoryMapper {

	void updateProductInventory(ProductInventory productInventory);
	
	ProductInventory findProductInventory(Integer productId);
}
