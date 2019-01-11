package com.eshop.inventory.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 请求内存队列
 * @author ghost
 */
public class RequestQueue {

	private RequestQueue() {

	}

	private static class Singleton {
		private static final RequestQueue REQUEST_QUEUE;
		static {
			REQUEST_QUEUE = new RequestQueue();
		}
	}

	public static RequestQueue getInstance() {
		return Singleton.REQUEST_QUEUE;
	}

	/**
	 * 线程队列，有多少线程池，就有多少线程队列。
	 */
	private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();

	public void addQueue(ArrayBlockingQueue<Request> queue) {
		this.queues.add(queue);
	}

	public int size() {
		return queues.size();
	}

	public ArrayBlockingQueue<Request> getQueue(int index) {
		return queues.get(index);
	}

	/**
	 * 并发级别和线程队列一样大,写入缓存中5分钟后失效，
	 * 最大容量就是队列中装的请求的3倍
	 */
	private Cache<Integer, Boolean> flagMap = CacheBuilder.newBuilder()
			.concurrencyLevel(10)
			.expireAfterWrite(5, TimeUnit.MINUTES)
			.maximumSize(3000)
			.build();
	
	public Cache<Integer, Boolean> getFlagMap() {
		return flagMap;
	}
}
