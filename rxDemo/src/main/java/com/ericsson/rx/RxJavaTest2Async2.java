package com.ericsson.rx;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rx.Observable;
import rx.schedulers.Schedulers;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class RxJavaTest2Async2 {
	private static class GeneralService {
		static final Logger logger = LogManager.getLogger(GeneralService.class
				.getName());

		public Observable<String> getData() {
			return Observable.<String> create(s -> {
				logger.info("Start: Executing a Service");
				for (int i = 1; i <= 3; i++) {
					logger.info("Emitting {}", "root " + i);
					s.onNext("root " + i);
				}
				logger.info("End: Executing a Service");
				s.onCompleted();
			});
		}
	}

	private static final Logger logger = LogManager.getLogger(RxJavaTest2Async2.class
			.getName());
	private static ExecutorService executor2 = Executors.newFixedThreadPool(5,
			new ThreadFactoryBuilder().setNameFormat("ObserveOn-%d").build());

	public static void main(String[] args) throws Exception {
		Observable<String> ob1 = new GeneralService().getData();

		CountDownLatch latch = new CountDownLatch(1);

		ob1.observeOn(Schedulers.from(executor2)).subscribe(s -> {
			logger.info("Got {}", s);
		}, e -> logger.error(e.getMessage(), e), () -> latch.countDown());

		latch.await();
	}
}
