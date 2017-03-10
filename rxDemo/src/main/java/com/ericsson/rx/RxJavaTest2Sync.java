package com.ericsson.rx;

import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rx.Observable;

public class RxJavaTest2Sync {
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

	public static void main(String[] args) throws Exception {
		final Logger logger = LogManager.getLogger(RxJavaTest2Sync.class.getName());

		Observable<String> ob1 = new GeneralService().getData();
		CountDownLatch latch = new CountDownLatch(1);

		ob1.subscribe(s -> {
			logger.info("Got {}", s);
		}, e -> logger.error(e.getMessage(), e), () -> latch.countDown());

		latch.await();
	}

}
