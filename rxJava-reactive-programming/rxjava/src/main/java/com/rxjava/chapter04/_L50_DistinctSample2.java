package com.rxjava.chapter04;

import io.reactivex.Flowable;

public class _L50_DistinctSample2 {

  public static void main(String[] args) {
    Flowable<String> flowable =
        Flowable.just("A", "a", "B", "b", "A", "a", "B", "b")
            .distinct(data -> data.toLowerCase());

    flowable.subscribe(new _L01_DebugSubscriber<>());
  }

}
