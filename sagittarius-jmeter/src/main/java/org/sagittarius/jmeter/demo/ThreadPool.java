package org.sagittarius.jmeter.demo;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPool {

	private ExecutorService threadPool;
	private int threadsNum = 5;

	public ThreadPool() {
		super();
		this.threadPool = Executors.newFixedThreadPool(threadsNum);
	}

	public void execute() {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					System.out.println(1);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public Future<?> submitRunnable() {
		Future<?> future = threadPool.submit(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (i < 3) {
					System.out.println("2");
					try {
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					i++;
				}
			}
		});
		return future;
	}

	public Future<?> submitCallable() {
		Future<?> future = threadPool.submit(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return 3;
			}
		});
		return future;
	}

	public List<Future<Object>> invokeAll() throws InterruptedException {
		Set<Callable<Object>> callables = new HashSet<Callable<Object>>();
		for (int i = 0; i < 1000; i++) {
			callables.add(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					return new Random().nextInt(10);
				}
			});
		}

		return threadPool.invokeAll(callables);
	}

	public void shutdown() {
		threadPool.shutdown();
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ThreadPool pool = new ThreadPool();
		pool.execute();

		Future<?> future1 = pool.submitRunnable();
		System.out.println(future1.get());

		Future<?> future2 = pool.submitCallable();
		System.out.println(future2.get());

		List<Future<Object>> list = pool.invokeAll();
		list.forEach(arg -> {
			try {
				System.out.println(arg.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
		pool.shutdown();
	}

}
