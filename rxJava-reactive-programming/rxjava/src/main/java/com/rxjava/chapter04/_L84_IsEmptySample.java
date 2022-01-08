package com.rxjava.chapter04;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.concurrent.TimeUnit;

public class _L84_IsEmptySample {

  public static void main(String[] args) throws InterruptedException {
    Single<Boolean> single =
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
            .take(3)
            .filter(data -> data >= 3)
            .isEmpty();

    single.subscribe(); // 결과값: true


    Thread.sleep(4000L);
  }

}
