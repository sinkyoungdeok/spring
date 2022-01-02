package com.rxjava.chapter01;

import io.reactivex.Flowable;

public class _L07_MethodChainSample {
  public static void main(String[] args) {
    Flowable<Integer> flowable =
        // 인자의 데이터를 순서대로 통지하는 Flowable을 생서한다
        Flowable.just(1,2,3,4,5,6,7,8,9,10)
            // 짝수에 해당하는 데이터만 통지한다
            .filter(data-> data % 2 ==0)
            // 데이터를 100배로 변환한다
            .map(data -> data * 100);

    flowable.subscribe(data -> System.out.println("data=" + data));
  }
}
