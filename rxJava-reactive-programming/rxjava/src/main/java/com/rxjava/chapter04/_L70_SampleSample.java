package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L70_SampleSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable<Long> flowable =
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .take(9)
            .sample(Flowable.interval(1000L, TimeUnit.MILLISECONDS));

    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(3000L);
  }

}
