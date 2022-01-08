package com.rxjava.chapter04;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.concurrent.TimeUnit;

public class _L90_CountSample {

  public static void main(String[] args) throws InterruptedException {
    Single<Long> single =
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
            .take(3)
            .count();

    single.subscribe(); // 3

    Thread.sleep(4000L);
  }

}
