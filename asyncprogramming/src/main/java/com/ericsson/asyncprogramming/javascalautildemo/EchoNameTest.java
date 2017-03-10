package com.ericsson.asyncprogramming.javascalautildemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javascalautils.concurrent.Future;
import javascalautils.concurrent.Promise;

public final class EchoNameTest {
	public static Future<String> echoName(String name) {
		Promise<String> promise = Promise.apply();
		new Thread(() -> {
			// pseudo-code to illustrate some execution time
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// the actual response
				promise.success("Hello " + name);
			}).start();

		return promise.future();
	}

	public static void main(String[] args) throws TimeoutException, Throwable {
		// TODO Auto-generated method stub
		Future<String> f = echoName("Qingwei Song");

		System.out.println("Main thread:" + Thread.currentThread().getName());
		f.onSuccess(s -> System.out.println(Thread.currentThread().getName()
				+ " " + s));
		f.onFailure(ex -> {
			System.out.println(Thread.currentThread().getName() + " "
					+ ex.getMessage());
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		f.onComplete(t -> {
			System.out.println(Thread.currentThread().getName() + " "
					+ t.isSuccess());
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println("=========================");
		System.out.println(f.result(5000, TimeUnit.SECONDS));
	}
}
