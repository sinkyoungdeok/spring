package com.rxjava.chapter06;

import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class _L08_DoOnSubscribeSample {

  public static void main(String[] args) {
    Flowable.range(1, 5)
        .doOnSubscribe(subscription -> System.out.println("doOnSubscribe"))
        .subscribe(new Subscriber<Integer>() {
          @Override
          public void onSubscribe(Subscription s) {
            System.out.println("--- Subscriber: onSbuscribe");
            s.request(Long.MAX_VALUE);
          }

          @Override
          public void onNext(Integer integer) {
            System.out.println("--- Subscriber: onNext: " + integer);
          }

          @Override
          public void onError(Throwable t) {

          }

          @Override
          public void onComplete() {

          }
        });
  }

}
