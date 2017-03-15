package com.framework.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogFactory {

	private static LogFactory logFactory = new LogFactory();

	private LogFactory() {
	}

	public static LogFactory getInstance() {
		return logFactory;
	}
	
	/**
	 * 获取默认的Logger，默认为控制台
	 * 
	 * @return Logger
	 */
	public Logger getLogger() {
		return LoggerFactory.getLogger("stdout");
	}
	
	/**
	 * 系统日志信息
	 * @return
	 */
	public Logger getSysInfoLogger(){
		return LoggerFactory.getLogger("sys_info");
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LogFactory logFactory = LogFactory.getInstance();
		Logger log = null;
		// 循环用来测试对配置文件的监控是否生效
		for (int i = 1; i < 8; i++) {
			log = logFactory.getLogger();
			log.debug("debug");
			log.info("info");
			log.warn("warn");
			log.error("error");

//			log = logFactory.getRecoverOrderLogger();
//			log.info("thread_poll_recover_order");
//
//			log = logFactory.getRecoverStateLogger();
//			log.info("thread_poll_recover_state");
//
//			log = logFactory.getRecoverSendLogger();
//			log.info("thread_poll_recover_send");
		}
	}

}
