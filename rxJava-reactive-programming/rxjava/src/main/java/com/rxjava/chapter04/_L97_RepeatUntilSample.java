package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L97_RepeatUntilSample {

  public static void main(String[] args) throws InterruptedException {
    final long startTime = System.currentTimeMillis();

    Flowable<Long> flowable =
        Flowable.interval(100L, TimeUnit.MILLISECONDS)
            .take(3)
            .repeatUntil(() -> {
              System.out.println("called");

              return System.currentTimeMillis() - startTime > 500L;
            });

    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(1000L);
  }

}
