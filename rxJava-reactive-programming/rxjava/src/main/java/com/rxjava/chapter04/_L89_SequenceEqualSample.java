package com.rxjava.chapter04;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.concurrent.TimeUnit;

public class _L89_SequenceEqualSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable<Long> flowable1 =
        Flowable.interval(1000L, TimeUnit.MILLISECONDS).take(3);

    Flowable<Long> flowable2 = Flowable.just(0L, 1L, 2L);

    Single<Boolean> single = Flowable.sequenceEqual(flowable1, flowable2);

    single.subscribe(); // true

    Thread.sleep(4000L);
  }

}
