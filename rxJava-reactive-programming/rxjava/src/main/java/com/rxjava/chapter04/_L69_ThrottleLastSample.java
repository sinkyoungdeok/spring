package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L69_ThrottleLastSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable<Long> flowable =
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .take(9)
            .throttleLast(1000L, TimeUnit.MILLISECONDS);

    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(3000L);
  }

}
