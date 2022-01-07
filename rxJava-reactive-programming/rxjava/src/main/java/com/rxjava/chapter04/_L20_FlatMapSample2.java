package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L20_FlatMapSample2 {

  public static void main(String[] args) throws InterruptedException {
    Flowable<String> flowable = Flowable.range(1,3)
        .flatMap(
            data -> {
              return Flowable.interval(100L, TimeUnit.MILLISECONDS)
                  .take(3);
            },
            (sourceData, newData) -> "[" + sourceData +"] " + newData);

    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(1000L);
  }

}
