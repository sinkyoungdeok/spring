package com.rxjava.chapter04;

import io.reactivex.subscribers.DisposableSubscriber;

public class _L01_DebugSubscriber<T> extends DisposableSubscriber<T> {
  private String label;

  public _L01_DebugSubscriber() {
    super();
  }

  public _L01_DebugSubscriber(String label) {
    super();
    this.label = label;
  }


  @Override
  public void onNext(T data) {
    String threadName = Thread.currentThread().getName();
    if (label == null) {
      System.out.println(threadName + ": " + data);
    } else {
      System.out.println(threadName + ": " + label + ": " + data);
    }
  }

  @Override
  public void onError(Throwable throwable) {
    String threadName = Thread.currentThread().getName();
    if(label == null) {
      System.out.println(threadName + ": 에러 = " + throwable);
    } else {
      System.out.println(threadName + ": " + label + ": 에러 = " + throwable);
    }
  }

  @Override
  public void onComplete() {
    String threadName = Thread.currentThread().getName();
    if(label == null) {
      System.out.println(threadName + ":완료");
    } else {
      System.out.println(threadName + ": " + label + ": 완료");
    }
  }
}
