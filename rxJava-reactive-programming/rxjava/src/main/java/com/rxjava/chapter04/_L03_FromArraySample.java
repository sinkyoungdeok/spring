package com.rxjava.chapter04;

import io.reactivex.Flowable;

public class _L03_FromArraySample {

  public static void main(String[] args) {
    Flowable<String> flowable = Flowable.fromArray("A", "B", "C", "D", "E");

    flowable.subscribe(new _L01_DebugSubscriber<>());
  }

}
