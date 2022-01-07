package com.rxjava.chapter04;

import io.reactivex.Flowable;

public class _L06_RangeSample {

  public static void main(String[] args) {
    Flowable<Integer> flowable = Flowable.range(10, 3);

    flowable.subscribe(new _L01_DebugSubscriber<>());
  }

}
