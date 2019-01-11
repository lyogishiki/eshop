package com.eshop.inventory.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

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

	private ConcurrentHashMap<Integer, Boolean> flagMap = new ConcurrentHashMap<>();
	
	
	
	public void addQueue(ArrayBlockingQueue<Request> queue) {
		this.queues.add(queue);
	}

	public int size() {
		return queues.size();
	}

	public ArrayBlockingQueue<Request> getQueue(int index) {
		return queues.get(index);
	}

	public Map<Integer, Boolean> getFlagMap() {
		return flagMap;
	}
}
