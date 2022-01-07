package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class _L30_BufferSample1 {

  public static void main(String[] args) throws InterruptedException {
    Flowable<List<Long>> flowable =
        Flowable.interval(100L, TimeUnit.MILLISECONDS)
            .take(10)
            .buffer(3);
    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(3000L);
  }

}
