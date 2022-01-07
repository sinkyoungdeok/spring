package com.rxjava.chapter04;

import io.reactivex.Flowable;

public class _L11_EmptySample {

  public static void main(String[] args) {
    Flowable
        .empty()
        .subscribe(new _L01_DebugSubscriber<>());
  }

}
