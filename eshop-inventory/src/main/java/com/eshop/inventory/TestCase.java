package com.eshop.inventory;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;

import java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class TestCase {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
//		Lock lock = new ReentrantLock();
//		Condition wait = lock.newCondition();
//		lock.lock();
//		wait.await();
		
//	Future<String> futureTask = 	Executors.newCachedThreadPool().submit(new Callable<String>() {
//
//			@Override
//			public String call() throws Exception {
//				return "1234";
//			}
//		});
//	
//	System.out.println(futureTask.getClass());
		
/*		CompletableFuture<String> future = new CompletableFuture<>();
		
		new Thread(() -> {
			try {
				Thread.sleep(5005);
			} catch (Exception e) {
				e.printStackTrace();
			}
			future.complete("zhangsan");
		}).start();
		System.out.println("main : " + future.get());*/
		
//		LockSupport.park
		Object obj = new Object();
		Thread t = Thread.currentThread();
		new Thread(() -> {
			try {
				Thread.sleep(5005);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("解锁");
			LockSupport.unpark(t);
		}).start();
		LockSupport.park(obj);
		System.out.println("main : " );
	}
	
}
