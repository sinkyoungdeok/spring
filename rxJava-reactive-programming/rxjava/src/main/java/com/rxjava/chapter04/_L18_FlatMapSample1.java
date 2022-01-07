package com.rxjava.chapter04;

import io.reactivex.Flowable;

public class _L18_FlatMapSample1 {

  public static void main(String[] args) {
    Flowable<String> flowable = Flowable.just("A", "", "B", "C")
        .flatMap(data -> {
          if("".equals(data)) {
            return Flowable.empty();
          } else {
            return Flowable.just(data.toLowerCase());
          }
        });

    flowable.subscribe(new _L01_DebugSubscriber<>());
  }

}
