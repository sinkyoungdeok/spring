package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L47_FilterSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable<Long> flowable =
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .filter(data -> data % 2 ==0);

    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(3000L);
  }

}
