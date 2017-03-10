package com.ericsson.asyncprogramming.javascalautildemo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javascalautils.concurrent.Future;

public class FlatteningFutureTest {
	private static void log(String s) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date) + " " + s);
	}

	private static Future<Integer> square(int para) {
		return Future.apply(() -> {
			Thread.sleep(1000);
			log("square(" + para + ")");
			return para * para;
		});
		
	}

	private static int add(Integer a, Integer b) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log("add(" + a + "," + b + ")");
		return a + b;
	}

	public static void main(String[] args) throws TimeoutException, Throwable {
		// TODO Auto-generated method stub
		Future<Integer> f1 = square(2);
		Future<Integer> f2 = square(3);
		// our mapping function simply takes the length of the string
		Future<Object> mapped = f1.flatMap(v1 -> f2.map(v2 -> add(v1, v2)));
		log(String.valueOf(mapped.result(5, TimeUnit.SECONDS)));
		log("All finished");
	}

}
