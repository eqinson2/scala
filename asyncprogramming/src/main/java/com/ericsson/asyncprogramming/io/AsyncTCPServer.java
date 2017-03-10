package com.ericsson.asyncprogramming.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class AsyncTCPServer {
	private AsynchronousServerSocketChannel server;

	public static void main(String[] args) throws IOException {
		AsyncTCPServer aioServer = new AsyncTCPServer();
		aioServer.init("localhost", 6025);
	}

	private void init(String host, int port) throws IOException {
		AsynchronousChannelGroup group = AsynchronousChannelGroup
				.withCachedThreadPool(Executors.newCachedThreadPool(), 10);
		server = AsynchronousServerSocketChannel.open(group);

		server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
		server.setOption(StandardSocketOptions.SO_RCVBUF, 16 * 1024);

		server.bind(new InetSocketAddress(host, port));
		System.out.println("Listening on " + host + ":" + port);
		System.out.println("Channel Provider : " + server.provider());

		server.accept(null,
				new CompletionHandler<AsynchronousSocketChannel, Object>() {
					final ByteBuffer buffer = ByteBuffer.allocate(1024);

					@Override
					public void completed(AsynchronousSocketChannel result,
							Object attachment) {
						System.out.println("waiting....");
						buffer.clear();
						try {
							result.read(buffer).get();
							buffer.flip();
							System.out.println("Echo "
									+ new String(buffer.array()).trim()
									+ " to " + result);

							result.write(buffer);
							buffer.flip();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						} finally {
							try {
								result.close();
								server.accept(null, this);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

					@Override
					public void failed(Throwable exc, Object attachment) {
						System.out.print("Server failed...." + exc.getCause());
					}
				});

		while (true)
			Thread.yield();
	}
}