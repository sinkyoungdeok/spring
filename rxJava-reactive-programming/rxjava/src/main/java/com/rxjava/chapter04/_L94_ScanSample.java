package com.rxjava.chapter04;

import io.reactivex.Flowable;

public class _L94_ScanSample {

  public static void main(String[] args) {
    Flowable<Integer> flowable =
        Flowable.just(1,10,100,1000,10000)
            .scan(0, (sum, data) -> sum + data);

    flowable.subscribe(new _L01_DebugSubscriber<>());
  }

}
