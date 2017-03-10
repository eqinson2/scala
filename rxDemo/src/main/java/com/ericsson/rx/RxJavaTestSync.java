package com.ericsson.rx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rx.Observable;
import rx.Subscriber;

public class RxJavaTestSync {
	static final Logger logger = LogManager.getLogger(RxJavaTestSync.class
			.getName());

	public static void main(String[] args) {
		Observable<String> myObservable = Observable
				.create(new Observable.OnSubscribe<String>() {
					@Override
					public void call(Subscriber<? super String> sub) {
						logger.info("Observable.create");
						sub.onNext("Hello, world!");
						sub.onCompleted();
					}
				});

		Subscriber<String> mySubscriber = new Subscriber<String>() {
			@Override
			public void onNext(String s) {
				logger.info("onNext:", s);
			}

			@Override
			public void onCompleted() {
				logger.info("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				logger.info("onError");
			}
		};

		myObservable.subscribe(mySubscriber);
	}

}
