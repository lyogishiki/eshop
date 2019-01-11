package com.eshop.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventory {

	/**
	 * 商品id
	 */
	private Integer productId;
	
	/**
	 * 商品库存
	 */
	private Long inventoryCnt;
	
}
