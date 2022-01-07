package com.rxjava.chapter04;

import io.reactivex.Flowable;

public class _L14_NeverSample {

  public static void main(String[] args) {
    Flowable
        .never()
        .subscribe(new _L01_DebugSubscriber<>());
  }

}
