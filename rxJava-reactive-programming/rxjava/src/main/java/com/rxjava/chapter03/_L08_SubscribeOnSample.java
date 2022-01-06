package com.rxjava.chapter03;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class _L08_SubscribeOnSample {

  public static void main(String[] args) throws InterruptedException {
    Flowable.just(1,2,3,4,5) // Flowable 설정
        .subscribeOn(Schedulers.computation()) // RxComputationThreadPool
        .subscribeOn(Schedulers.io()) // RxCachedTrheadScheduler, 뒤에 설정한 subscribeOn은 무시된다.
        .subscribeOn(Schedulers.single()) // RxSingleScheduler, 뒤에 설정한 subscribeOn은 무시된다.
        .subscribe(data -> {
          String threadName = Thread.currentThread().getName();
          System.out.println("threadName = " + threadName + " : " + data);
        });
    // 잠시 기다린다
    Thread.sleep(500);
  }

}
