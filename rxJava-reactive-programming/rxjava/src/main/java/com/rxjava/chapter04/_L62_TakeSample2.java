package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L62_TakeSample2 {

  public static void main(String[] args) throws InterruptedException {
    Flowable<Long> flowable =
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .take(10)
            .takeLast(2, 1000L, TimeUnit.MILLISECONDS);

    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(4000L);
  }

}
