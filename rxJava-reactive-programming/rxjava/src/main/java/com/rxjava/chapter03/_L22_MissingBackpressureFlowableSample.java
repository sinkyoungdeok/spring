package com.rxjava.chapter03;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class _L22_MissingBackpressureFlowableSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable<Long> flowable = Flowable.interval(10L, TimeUnit.MILLISECONDS)
        .doOnNext(value -> System.out.println("value = " + value));

    flowable
        .observeOn(Schedulers.computation())
        .subscribe(new Subscriber<Long>() {
          @Override
          public void onSubscribe(Subscription s) {
            s.request(Long.MAX_VALUE);
          }

          @Override
          public void onNext(Long aLong) {
            try {
              System.out.println("waiting...");
              Thread.sleep(1000L);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            System.out.println("aLong = " + aLong);
          }

          @Override
          public void onError(Throwable t) {
            System.out.println("t = " + t);
          }

          @Override
          public void onComplete() {
            System.out.println("종료");
          }
        });

    Thread.sleep(5000L);
  }

}
