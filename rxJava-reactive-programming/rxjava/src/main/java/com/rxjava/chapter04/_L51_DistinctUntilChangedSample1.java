package com.rxjava.chapter04;

import io.reactivex.Flowable;

// 같은 데이터를 연속해서 받을 때 이 데이터를 제외하고 통지
public class _L51_DistinctUntilChangedSample1 {

  public static void main(String[] args) {
    Flowable<String> flowable =
        Flowable.just("A", "a", "a", "A", "a")
            .distinctUntilChanged();

    flowable.subscribe(new _L01_DebugSubscriber<>());
  }

}
