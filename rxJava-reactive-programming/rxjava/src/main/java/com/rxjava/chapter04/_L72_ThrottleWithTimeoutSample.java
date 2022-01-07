package com.rxjava.chapter04;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L72_ThrottleWithTimeoutSample {

  public static void main(String[] args) {
    Flowable<String> flowable =
        Flowable.<String> create(
            emitter -> {
              emitter.onNext("A");
              Thread.sleep(1000L);

              emitter.onNext("B");
              Thread.sleep(300L);

              emitter.onNext("C");
              Thread.sleep(300L);

              emitter.onNext("D");
              Thread.sleep(300L);

              emitter.onNext("E");
              Thread.sleep(300L);

              emitter.onComplete();
            }, BackpressureStrategy.BUFFER)
            .throttleWithTimeout(500L, TimeUnit.MILLISECONDS);

    flowable.subscribe(new _L01_DebugSubscriber<>());
  }

}
