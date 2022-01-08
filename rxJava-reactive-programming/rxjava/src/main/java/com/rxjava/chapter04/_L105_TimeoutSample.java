package com.rxjava.chapter04;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L105_TimeoutSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable<Integer> flowable =
        Flowable.<Integer>create(emitter -> {
              emitter.onNext(1);
              emitter.onNext(2);
              try {
                Thread.sleep(1200L);
              } catch (InterruptedException e) {
                emitter.onError(e);
                return;
              }

              emitter.onNext(3);
              emitter.onComplete();
            }, BackpressureStrategy.BUFFER)
            .timeout(1000L, TimeUnit.MILLISECONDS);

    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(2000L);
  }

}
