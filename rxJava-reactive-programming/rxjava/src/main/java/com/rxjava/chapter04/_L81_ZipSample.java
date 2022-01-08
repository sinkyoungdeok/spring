package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class _L81_ZipSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable<Long> flowable1 =
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .take(5);

    Flowable<Long> flowable2 =
        Flowable.interval(500L, TimeUnit.MILLISECONDS)
            .take(3)
            .map(data -> data + 100L);

    Flowable<List<Long>> result = Flowable.zip(
        flowable1,
        flowable2,
        (data1, data2) -> Arrays.asList(data1, data2));
    result.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(2000L);
  }

}
