package com.ericsson.rx;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javascalautils.Try;
import rx.Observable;
import rx.Observer;

import com.ericsson.akkaclusterservice.api.ClusterServiceFactory;
import com.ericsson.akkaclusterservice.api.ServiceInvoker;

public class ServiceBroadcastDemo {
	static final Logger logger = LogManager
			.getLogger(ServiceBroadcastDemo.class.getName());

	public static void main(String[] args) {

		ClusterServiceFactory factory = new ClusterServiceFactory(
				"example-client-name");
		factory.start();

		ServiceInvoker serviceInvoker = factory.getServiceInvoker();

		Observable<Try<Object>> observable = serviceInvoker.broadcast(
				"example-service-name", "Broadcast:Peter Rulez!!!", 10,
				TimeUnit.SECONDS);

		observable.subscribe(new Observer<Try<Object>>() {
			@Override
			public void onCompleted() {
			}

			@Override
			public void onError(Throwable e) {
				logger.error("onError:", e.getMessage());
				e.printStackTrace();
			}

			@Override
			public void onNext(Try<Object> response) {
				logger.info("Got response [" + response + "]");
			}
		});
	}

}
