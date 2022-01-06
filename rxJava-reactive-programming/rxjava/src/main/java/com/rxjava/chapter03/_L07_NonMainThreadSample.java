package com.rxjava.chapter03;

import io.reactivex.Flowable;
import io.reactivex.subscribers.ResourceSubscriber;
import java.util.concurrent.TimeUnit;

public class _L07_NonMainThreadSample {

  public static void main(String[] args) throws InterruptedException {
    System.out.println("start");

    Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .subscribe(new ResourceSubscriber<Long>() {
          @Override
          public void onNext(Long data) {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + ":" + data);
          }

          @Override
          public void onError(Throwable error) {
            error.printStackTrace();
          }

          @Override
          public void onComplete() {
            String threadname = Thread.currentThread().getName();
            System.out.println(threadname + " 완료");
          }
        });

    System.out.println("end");

    //잠시 기다린다
    Thread.sleep(1000L);
  }

}
