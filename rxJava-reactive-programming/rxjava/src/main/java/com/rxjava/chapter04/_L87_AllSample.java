package com.rxjava.chapter04;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.concurrent.TimeUnit;

public class _L87_AllSample {

  public static void main(String[] args) throws InterruptedException {
    Single<Boolean> single =
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
            .take(3)
            .all(data -> data < 5);

    single.subscribe(); // true

    Thread.sleep(4000L);
  }

}
