package com.rxjava.chapter03;

import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;

public class _L17_OnErrorResumeItemSample {

  public static void main(String[] args) {
    Flowable.just(1,3,5,0,2,4)
        .map(data -> 100 / data)
        .onErrorReturnItem(0) // 에러가 발생하면 0을 통지한다
        .subscribe(new DisposableSubscriber<Integer>() {
          @Override
          public void onNext(Integer integer) {
            System.out.println("integer = " + integer);
          }

          @Override
          public void onError(Throwable t) {
            System.out.println("t = " + t);
          }

          @Override
          public void onComplete() {
            System.out.println("완료");
          }
        });
  }

}
