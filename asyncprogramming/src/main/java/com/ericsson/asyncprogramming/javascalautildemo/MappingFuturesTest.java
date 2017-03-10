package com.ericsson.asyncprogramming.javascalautildemo;

import javascalautils.concurrent.Future;

class MappingFuturesTest {
	public static void main(String[] args) {
		Future<String> future = Future.apply(() -> {
			Thread.sleep(1000);
			return "Qingwei Song";
		});
		// our mapping function simply takes the length of the string
		Future<Integer> mapped = future.map(s -> s.length());
		mapped.onSuccess(length -> System.out
				.println("The string was this long:" + length));

		System.out.println("All finished");
	}
}