package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L78_StartWithSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable<Long> flowable =
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .take(5);

    Flowable<Long> other =
        Flowable.interval(500L, TimeUnit.MILLISECONDS)
            .take(2)
            .map(data -> data + 100L);

    Flowable<Long> result = flowable.startWith(other);

    result.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(3000L);
  }

}
