package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.Locale;

public class _L15_MapSample {

  public static void main(String[] args) {
    Flowable<String> flowable =
        Flowable.just("A", "B", "C", "D", "E")
            .map(data -> data.toLowerCase());

    flowable.subscribe(new _L01_DebugSubscriber<>());
  }
}
