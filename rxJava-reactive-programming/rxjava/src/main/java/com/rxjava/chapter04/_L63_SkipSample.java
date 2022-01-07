package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L63_SkipSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable<Long> flowable =
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
            .skip(2);

    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(5000L);
  }

}
