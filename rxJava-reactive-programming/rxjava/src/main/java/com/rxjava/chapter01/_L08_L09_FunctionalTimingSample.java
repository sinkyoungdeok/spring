package com.rxjava.chapter01;

import io.reactivex.Flowable;

public class _L08_L09_FunctionalTimingSample {

  public static void main(String[] args) {
    Flowable<Long> flowable1 = Flowable.just(System.currentTimeMillis()); // 여러번 구독해도 같은 값
    Flowable<Long> flowable2 = Flowable.fromCallable(() -> System.currentTimeMillis()); // 두곻라 때 마다 다른 값 통지
  }

}
