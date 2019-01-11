package com.eshop.inventory.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.eshop.inventory.request.Request;
import com.eshop.inventory.request.RequestQueue;

/**
 * 线程池:单例
 * @author ghost
 *
 */
public class RequestProcessorThreadPool {

	private RequestProcessorThreadPool() {
		init();
	}
	
	private static class Singleton{
		private static final RequestProcessorThreadPool INSTANCE;
		
		static {
			INSTANCE = new RequestProcessorThreadPool();
		}
	}
	
	public static RequestProcessorThreadPool getInstance() {
		return Singleton.INSTANCE;
	}
	
	private static final int N_THREAD = 10;
	
	/**
	 * 线程池
	 */
	private ExecutorService threadPool = Executors.newFixedThreadPool(N_THREAD);
	
	private void init() {
		RequestQueue requestQueue = RequestQueue.getInstance();
		for (int i = 0; i < N_THREAD; i++) {
			ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<>(100);
			requestQueue.addQueue(queue);
			threadPool.submit(new WorkerThread(queue));
		}

	}
	
	public static void initPool() {
		RequestProcessorThreadPool.getInstance();
	}
}
