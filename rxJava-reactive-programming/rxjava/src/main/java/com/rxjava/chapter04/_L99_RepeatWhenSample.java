package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L99_RepeatWhenSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable<String> flowable =
        Flowable.just(1,2,3)
            .repeatWhen(completeHandler -> {
              return completeHandler
                  .delay(1000L, TimeUnit.MILLISECONDS)
                  .take(2) // 1,2,3을 2번
                  .doOnNext(data -> System.out.println("emit: " + data))
                  .doOnComplete(() -> System.out.println("complete"));
            })
            .map(data -> {
              long time = System.currentTimeMillis();
              return time + "ms: " + data;
            });

    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(5000L);
  }
}
