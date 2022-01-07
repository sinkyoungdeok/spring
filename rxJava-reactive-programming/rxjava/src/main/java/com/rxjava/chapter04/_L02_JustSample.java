package com.rxjava.chapter04;

import io.reactivex.Flowable;

public class _L02_JustSample {

  public static void main(String[] args) {
    Flowable<String> flowable = Flowable.just("A", "B", "C", "D", "E");

    flowable.subscribe(new _L01_DebugSubscriber<>());
  }

}
