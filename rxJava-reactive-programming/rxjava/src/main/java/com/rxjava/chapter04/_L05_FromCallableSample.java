package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.Callable;

public class _L05_FromCallableSample {

  public static void main(String[] args) {
    Flowable<Long> flowable = Flowable.fromCallable(() -> System.currentTimeMillis());

    flowable.subscribe(new _L01_DebugSubscriber<>());
  }

}
