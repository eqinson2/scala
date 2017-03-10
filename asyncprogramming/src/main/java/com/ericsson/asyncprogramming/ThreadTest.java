package com.ericsson.asyncprogramming;

public class ThreadTest {
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName()
				+ " Main thread start...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName()
							+ " Child thread start...");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}).start();
	}
}
