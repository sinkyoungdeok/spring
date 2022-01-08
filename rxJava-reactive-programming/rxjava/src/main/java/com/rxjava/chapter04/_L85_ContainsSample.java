package com.rxjava.chapter04;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.concurrent.TimeUnit;

public class _L85_ContainsSample {

  public static void main(String[] args) throws InterruptedException {
    Single<Boolean> single =
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
            .contains(3L);

    single.subscribe(); // true

    Thread.sleep(4000L);
  }

}
