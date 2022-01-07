package com.rxjava.chapter04;

import io.reactivex.Flowable;

public class _L49_DistinctSample1 {

  public static void main(String[] args) {
    Flowable<String> flowable =
        Flowable.just("A", "a", "B", "b", "A", "a", "B", "b")
            .distinct();

    flowable.subscribe(new _L01_DebugSubscriber<>());
  }

}
