package com.eshop.inventory.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.eshop.inventory.thread.RequestProcessorThreadPool;


/**
 * 注册监听器
 * @author ghost
 *
 */
@WebListener()
public class InitListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {	
		RequestProcessorThreadPool.initPool();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("InitListener.contextDestroyed()");
		RequestProcessorThreadPool.getInstance().shutdown();
		
	}

}
