package com.ericsson.asyncprogramming;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class RTask implements Runnable {
	@Override
	public void run() {
		System.out.println("Child thread start computing...");
		int sum = 0;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < 100; i++)
			sum += i;
		System.out.println("Child thread end computing...");
	}
}

public class RunnableTest {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		RTask task = new RTask();
		//executor.execute(task);
		Future<?> future = executor.submit(task);
		executor.shutdownNow();
		try {
			System.out.println(future.get(5, TimeUnit.SECONDS));
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//executor.shutdownNow();
		System.out.println("All finished");
	}
}
