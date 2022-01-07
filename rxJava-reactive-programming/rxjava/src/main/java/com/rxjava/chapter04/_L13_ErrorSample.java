package com.rxjava.chapter04;

import io.reactivex.Flowable;

public class _L13_ErrorSample {

  public static void main(String[] args) {
    Flowable
        .error(new Exception("예외 발생"))
        .subscribe(new _L01_DebugSubscriber<>());
  }

}
