package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class _L32_BufferSample2 {

  public static void main(String[] args) throws InterruptedException {
    Flowable<List<Long>> flowable =
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .take(7)
            .buffer(
                () -> Flowable.timer(1000L, TimeUnit.MILLISECONDS));
    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(4000L);
  }

}
