package com.eshop.inventory.service.impl;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import org.springframework.stereotype.Service;

import com.eshop.inventory.request.ProductInventoryCacheReloadRequest;
import com.eshop.inventory.request.ProductInventoryUpdateRequest;
import com.eshop.inventory.request.Request;
import com.eshop.inventory.request.RequestQueue;
import com.eshop.inventory.service.RequestAsyncProcessService;


/**
 * 请求异步处理的service
 * @author ghost
 *
 */
@Service
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {

	@Override
	public void process(Request request) {
		// 做请求的路由，根据每个请求商品的id，路由到对应的内存队列中
		ArrayBlockingQueue<Request> queue = getRoutingQueue(request.getDistributeKey());
//		将请求放入对应的队列中，完成路由操作,暂时使用阻塞操作。
		try {
			queue.put(request);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private ArrayBlockingQueue<Request> getRoutingQueue(Integer distributeKey) {
		RequestQueue requestQueue = RequestQueue.getInstance();
		int h;
		int hash = distributeKey == null ? 0 : (h = distributeKey.hashCode()) ^ (h >>> 16);

		int index = (requestQueue.size() - 1) & hash;
		System.out.println("====================路由商品队列，商品id:" + distributeKey + ",路由到的队列:" + index);
		return requestQueue.getQueue(index);
	}
}
