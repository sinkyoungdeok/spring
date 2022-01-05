package com.rxjava.chapter02;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class _L06_CounterSample {

  public static void main(String[] args) throws Exception {
    final _L05_Counter counter = new _L05_Counter();

    Runnable task = () -> {
      for (int i=0; i< 10000; i++) {
        counter.increment();
      }
    };

    ExecutorService executorService = Executors.newCachedThreadPool();

    Future<Boolean> future1 = executorService.submit(task, true);
    Future<Boolean> future2 = executorService.submit(task, true);

    if (future1.get() && future2.get()) {
      System.out.println(counter.get());
    } else {
      System.out.println("실패");
    }

    executorService.shutdown();
  }

}
