package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.time.LocalTime;

public class _L09_DeferSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable<LocalTime> flowable =
        Flowable.defer(() -> Flowable.just(LocalTime.now()));

    flowable.subscribe(new _L01_DebugSubscriber<>("No. 1"));

    Thread.sleep(2000L);

    flowable.subscribe(new _L01_DebugSubscriber<>("No. 2"));
  }

}
