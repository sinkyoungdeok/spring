package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L60_TakeWhileSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable<Long> flowable =
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .takeWhile(data -> data != 3);

    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(2000L);
  }

}
