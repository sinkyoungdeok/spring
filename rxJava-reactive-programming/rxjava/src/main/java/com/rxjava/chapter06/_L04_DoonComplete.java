package com.rxjava.chapter06;

import com.rxjava.chapter04._L01_DebugSubscriber;
import io.reactivex.Flowable;

public class _L04_DoonComplete {

  public static void main(String[] args) {
    Flowable.range(1, 5)
        .doOnComplete(() -> System.out.println("doOnComplete"))
        .subscribe(new _L01_DebugSubscriber<>());
  }

}
