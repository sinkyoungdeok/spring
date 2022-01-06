package com.rxjava.chapter03;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L10_FlatMapSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable<String> flowable =
        Flowable.just("A", "B", "C")
            .flatMap(data -> Flowable.just(data).delay(1000L, TimeUnit.MILLISECONDS));

    flowable.subscribe(data -> {
      String threadName = Thread.currentThread().getName();
      System.out.println(threadName + " : " + data);
    }); // CAB 등으로 결과의 순서가 보장되지 않는다.

    Thread.sleep(2000L);
  }

}
