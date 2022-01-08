package com.rxjava.chapter04;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import java.util.concurrent.TimeUnit;

public class _L74_ElementAtSample {

  public static void main(String[] args) throws InterruptedException {
    Maybe<Long> maybe =
        Flowable.interval(100L, TimeUnit.MILLISECONDS)
            .elementAt(3);

    maybe.subscribe();

    Thread.sleep(1000L);
  }

}
