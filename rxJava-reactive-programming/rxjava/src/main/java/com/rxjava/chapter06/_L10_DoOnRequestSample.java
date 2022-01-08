package com.rxjava.chapter06;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class _L10_DoOnRequestSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable.range(1,3)
        .doOnRequest(size -> System.out.println("기존 데이터: size=" + size))
        .observeOn(Schedulers.computation())
        .doOnRequest(size -> System.out.println("--- observeOn 적용 후: size=" + size))
        .subscribe(new Subscriber<Integer>() {
          private Subscription subscription;

          @Override
          public void onSubscribe(Subscription s) {
            this.subscription = s;
            this.subscription.request(1);
          }

          @Override
          public void onNext(Integer data) {
            System.out.println("data = " + data);
            subscription.request(1);
          }

          @Override
          public void onError(Throwable t) {
            System.out.println("에러: " + t);
          }

          @Override
          public void onComplete() {
            System.out.println("완료");
          }
        });

    Thread.sleep(500L);
  }

}
