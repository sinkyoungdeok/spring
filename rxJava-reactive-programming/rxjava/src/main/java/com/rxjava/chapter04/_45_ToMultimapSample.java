package com.rxjava.chapter04;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class _45_ToMultimapSample {

  public static void main(String[] args) throws InterruptedException {
    Single<Map<String, Collection<Long>>> single =
        Flowable.interval(500L, TimeUnit.MILLISECONDS)
            .take(5)
            .toMultimap(data -> {
              if (data % 2 == 0) {
                return "짝수";
              } else {
                return "홀수";
              }
            });
    single.subscribe(); // {짝수=[0,2,4], 홀수=[1,3]}

    Thread.sleep(3000L);
  }

}
