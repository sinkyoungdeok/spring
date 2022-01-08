package com.rxjava.chapter04;

import io.reactivex.Flowable;

public class _L95_RepeatSample {

  public static void main(String[] args) {
    Flowable<String> flowable =
        Flowable.just("A", "B", "C")
            .repeat(2);

    flowable.subscribe(new _L01_DebugSubscriber<>());
  }

}
