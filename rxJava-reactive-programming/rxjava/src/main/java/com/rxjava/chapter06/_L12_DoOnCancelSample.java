package com.rxjava.chapter06;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class _L12_DoOnCancelSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable.interval(100L, TimeUnit.MILLISECONDS)
        .doOnCancel(() -> System.out.println("doOnCancel"))
        .subscribe(new Subscriber<Long>() {

          private long startTime;
          private Subscription subscription;

          @Override
          public void onSubscribe(Subscription s) {
            this.startTime = System.currentTimeMillis();
            this.subscription = s;
            this.subscription.request(Long.MAX_VALUE);
          }

          @Override
          public void onNext(Long aLong) {
            if (System.currentTimeMillis() - startTime > 300L) {
              System.out.println("구독 해지");
              subscription.cancel();
              return;
            }
            System.out.println(aLong);
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

    Thread.sleep(1000L);
  }

}
