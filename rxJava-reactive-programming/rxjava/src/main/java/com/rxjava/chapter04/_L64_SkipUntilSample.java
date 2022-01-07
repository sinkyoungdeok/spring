package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L64_SkipUntilSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable<Long> flowable =
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .skipUntil(Flowable.timer(1000L, TimeUnit.MILLISECONDS));

    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(2000L);
  }

}
