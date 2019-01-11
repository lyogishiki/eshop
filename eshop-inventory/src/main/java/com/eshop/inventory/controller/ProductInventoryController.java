package com.eshop.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.inventory.model.ProductInventory;
import com.eshop.inventory.request.ProductInventoryCacheReloadRequest;
import com.eshop.inventory.request.ProductInventoryUpdateRequest;
import com.eshop.inventory.request.Request;
import com.eshop.inventory.service.ProductInventoryService;
import com.eshop.inventory.service.RequestAsyncProcessService;
import com.eshop.inventory.vo.Response;

/**
 * 商品库存Controller
 * @author ghost
 *
 */
@RestController
@RequestMapping("productInventory")
public class ProductInventoryController {

	@Autowired
	private ProductInventoryService productInventoryService;

	@Autowired
	private RequestAsyncProcessService requestAsyncProcessService;

	@RequestMapping("updProductInventory")
	public Response updProductInventory(ProductInventory productInventory) {
		Response response = null;
		System.out.println("================商品更新请求:" + productInventory);
		try {
			Request request = new ProductInventoryUpdateRequest(productInventory, productInventoryService);

			requestAsyncProcessService.process(request);
			response = Response.success("更新商品库存成功!");

		} catch (Exception e) {
			e.printStackTrace();
			Response.failure(e.getMessage());
		}

		return response;
	}

	@RequestMapping("updProductInventory2")
	public Response updProductInventory2(ProductInventory productInventory) {
		Response response = null;
		System.out.println("================商品更新请求:" + productInventory);
		try {
			productInventoryService.updateProductInventory(productInventory);
			response = Response.success();
		} catch (Exception e) {
			e.printStackTrace();
			Response.failure(e.getMessage());
		}

		return response;
	}

	@RequestMapping("getProductInventory")
	public ProductInventory getProductInventory(Integer productId) {
		try {
			
			ProductInventory productInventoryResult = productInventoryService.getProductInventoryCache(productId);
			if (productInventoryResult != null) {
				return productInventoryResult;
			}

			ProductInventory productInventoryVo = new ProductInventory();
			productInventoryVo.setProductId(productId);
			Request request = new ProductInventoryCacheReloadRequest(productInventoryVo, productInventoryService);
			requestAsyncProcessService.process(request);
			
//			request.
			
			// 去尝试等待前面的商品库存更新操作，同时缓存刷新别的操作。将最新数据刷新到缓存中。
			long startTime = System.currentTimeMillis();
			long waitTime = 0L;

			while (true) {
				if (waitTime > 200) {
					break;
				}
				// 尝试去redis中读取一次库存缓存数据
				productInventoryResult = productInventoryService.getProductInventoryCache(productId);
				// 如果读到了结果，那么就返回
				if (productInventoryResult != null) {
					return productInventoryResult;
				} else {
					Thread.sleep(20);
					long now = System.currentTimeMillis();
					waitTime = now - startTime;
				}

			}

			// 这里是一直无法从换从中读到请求的情况，那么这种情况下，就只能从数据库中读取数据
			productInventoryResult = productInventoryService.findProductInventory(productId);
			if (productInventoryResult != null) {
				// 如果能够读取导数据，证明数据库中存在这条数据，那么就把这条数据发到缓存中。
				// 但是如果这里没有放入队列中串行执行，那么就由可能出现数据库不一致的情况.
				// 代码运行到这里，有3中情况
				// 1.redis把缓存清理掉了。标志位还是false。所以这时候缓存中是拿不到数据的。
				// 2.可能在200ms内，就是渡请求在队列中一直积压，这种一般都是出现了性能问题。
				// 3.数据库中本来就没有数据。
				// productInventoryService.setProductInventoryCache(productInventoryResult);
				// 所以 这里如果有值，那么就要强制去刷新队列去更新缓存中的值。
				request = new ProductInventoryCacheReloadRequest(productInventoryVo, productInventoryService, true);
				requestAsyncProcessService.process(request);
				return productInventoryResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ProductInventory(productId, -1L);
	}

}
