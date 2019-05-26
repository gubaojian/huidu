package com.google.code.efurture.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunUtils {

	private static ExecutorService executorService = Executors.newFixedThreadPool(6);
	
	public static ExecutorService getPushExecutorService(){
		return executorService;
	}
	
	
	
}
