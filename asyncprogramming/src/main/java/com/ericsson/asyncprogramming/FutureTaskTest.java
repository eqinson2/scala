package com.ericsson.asyncprogramming;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

// FutureTask is both a Runnable and a future
public class FutureTaskTest {
	public static void main(String[] args) {
		// option1
		ExecutorService executor = Executors.newCachedThreadPool();
		FTask task1 = new FTask();
		FutureTask<Integer> futureTask = new FutureTask<Integer>(task1);
		executor.submit(futureTask);
		executor.shutdown();

		// option2
		FTask task2 = new FTask();
		FutureTask<Integer> futureTask2 = new FutureTask<Integer>(task2);
		Thread thread = new Thread(futureTask2);
		thread.start();

		System.out.println("Main thread start waiting...");

		try {
			System.out.println("task result: " + futureTask.get());
			if (!futureTask2.isDone())
				futureTask2.cancel(true);
			System.out.println("task result: " + futureTask2.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		System.out.println("All finished");
	}
}

class FTask implements Callable<Integer> {
	@Override
	public Integer call() throws Exception {
		System.out.println("Child thread start computing...");
		Thread.sleep(3000);
		int sum = 0;
		for (int i = 0; i < 100; i++)
			sum += i;
		return sum;
	}
}
