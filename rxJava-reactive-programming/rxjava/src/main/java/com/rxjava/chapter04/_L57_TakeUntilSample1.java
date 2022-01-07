package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L57_TakeUntilSample1 {

  public static void main(String[] args) throws InterruptedException {
    Flowable<Long> flowable =
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .takeUntil(data -> data == 3);

    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(2000L);
  }

}
