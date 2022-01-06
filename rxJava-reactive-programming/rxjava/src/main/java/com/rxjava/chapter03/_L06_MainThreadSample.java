package com.rxjava.chapter03;

import io.reactivex.Flowable;
import io.reactivex.subscribers.ResourceSubscriber;

public class _L06_MainThreadSample {

  public static void main(String[] args) {
    System.out.println("start");

    Flowable.just(1,2,3)
        .subscribe(new ResourceSubscriber<Integer>() {
          @Override
          public void onNext(Integer integer) {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " " + integer);
          }

          @Override
          public void onError(Throwable error) {
            error.printStackTrace();
          }

          @Override
          public void onComplete() {
            String threadName = Thread.currentThread().getName();
            System.out.println("threadName = " + threadName);
          }
        });

    System.out.println("end");
  }

}
