package com.eshop.inventory;

import java.util.concurrent.locks.Condition;

import java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.locks.Lock;

public class TestCase {

	public static void main(String[] args) throws InterruptedException {
		Lock lock = new ReentrantLock();
		Condition wait = lock.newCondition();
		lock.lock();
		wait.await();
	}
	
}
