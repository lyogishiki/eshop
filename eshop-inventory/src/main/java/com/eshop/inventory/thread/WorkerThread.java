package com.eshop.inventory.thread;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

import com.eshop.inventory.request.ProductInventoryCacheReloadRequest;
import com.eshop.inventory.request.ProductInventoryUpdateRequest;
import com.eshop.inventory.request.Request;
import com.eshop.inventory.request.RequestQueue;

public class WorkerThread implements Callable<Boolean>{

	private volatile boolean isShutdown = false;
	
	public void shutdown() {
		isShutdown = true;
	}
	
	private final ArrayBlockingQueue<Request> queue;
	
	public WorkerThread(ArrayBlockingQueue<Request> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public Boolean call() throws Exception {
		
		while(true) {
			if(isShutdown) {
				break;
			}
			
			Request request = queue.take();
			if(!request.isForceRefresh()) {
				/**
				 * 在WorkThread中去执行操作，可以确保对同一个商品的操作都会在一个线程中，顺序执行
				 * 已下是去重的操作。
				 */
				RequestQueue requestQueue = RequestQueue.getInstance();
				Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();
				
				if(request instanceof ProductInventoryUpdateRequest) {
//				如果是一个更新请求，那么就将这个ProductID对应的标志设为true
					flagMap.put(request.getDistributeKey(), true);
				}else if(request instanceof ProductInventoryCacheReloadRequest) {
					Boolean flag = flagMap.get(request.getDistributeKey());
					if(flag == null) {
//						如果flag是null，那么就说明没有之前的写请求，所以我们就读一下数据库，并把数据放入redis中,也就是加载到内存队列。
						flagMap.put(request.getDistributeKey(), false);
					}else if(flag) {
//				如果是缓存刷新的请求	,那么就判断，如果标志不为空，而且是true,那么就说明之前有一个商品的更新操作。
						flagMap.put(request.getDistributeKey(), false);
					}else if(!flag) {
//						如果flag是false，说明队列中已经有一个读请求在队列中了，那么就可以返回了
//						不执行request，继续执行之后的请求。
						continue;
					}
					
				}
			}
			
			request.process();
//			假如执行完一个读请求之后，假设数据刷新到redis中，
//			但是后面可能redis中的数据会因为内存满了，而被自动清理掉了
//			如果说数据从redis中被自动清理掉了以后，然后后面又来了一个读请求，发现标志位是false，
//			就不会去执行这个刷新的操作了、
		}
		
		return true;
	}

}
