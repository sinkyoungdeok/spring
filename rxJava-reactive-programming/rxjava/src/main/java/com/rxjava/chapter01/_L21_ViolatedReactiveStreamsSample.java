package com.rxjava.chapter01;

import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class _L21_ViolatedReactiveStreamsSample {
  public static void main(String[] args) {
    Flowable.range(1,3)
        .subscribe(new Subscriber<Integer>() {
          @Override
          public void onSubscribe(Subscription subscription) {
            System.out.println("onSubscribe: start");
            subscription.request(Long.MAX_VALUE);
            System.out.println("onSubscribe: end");
          }

          @Override
          public void onNext(Integer data) {
            System.out.println("data = " + data);
          }

          @Override
          public void onError(Throwable error) {
            System.out.println("error = " + error);
          }

          @Override
          public void onComplete() {
            System.out.println("완료");
          }
        });
  }
}
