package com.ericsson.asyncprogramming;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Task implements Callable<Integer> {
	static final Logger logger = LogManager.getLogger(Task.class.getName());

	@Override
	public Integer call() throws Exception {
		logger.info("Child thread start computing...");
		Thread.sleep(3000);
		int sum = 0;
		for (int i = 0; i < 100; i++)
			sum += i;
		return sum;
	}
}

public class CallableTest {
	static final Logger logger = LogManager.getLogger(CallableTest.class
			.getName());

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		Task task = new Task();
		Future<Integer> result = executor.submit(task);
		// executor.shutdown();

		logger.info("Main thread start waiting...");

		try {
			result.cancel(true);
			logger.info("task state: " + result.isDone());
			logger.info("task result: " + result.get());
		} catch (InterruptedException e) {
			logger.error(e);
		} catch (ExecutionException e) {
			logger.error(e);
		}
		executor.shutdownNow();
		System.out.println("All finished");
	}
}
