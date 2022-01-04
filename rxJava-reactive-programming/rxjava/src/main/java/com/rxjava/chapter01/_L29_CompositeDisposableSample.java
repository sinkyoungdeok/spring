package com.rxjava.chapter01;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class _L29_CompositeDisposableSample {
  public static void main(String[] args) throws InterruptedException {
    // Disposable을 합친다
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    compositeDisposable.add(Flowable.range(1,3)
        .doOnCancel(() -> System.out.println("No. 1 canceled"))
        .observeOn(Schedulers.computation())
        .subscribe(data -> {
          Thread.sleep(100L);
          System.out.println("No.1: " + data);
        })
    );

    compositeDisposable.add(Flowable.range(1,3)
        .doOnCancel(() -> System.out.println("No. 2 canceled"))
        .observeOn(Schedulers.computation())
        .subscribe(data -> {
          Thread.sleep(100L);
          System.out.println("No.2: " + data);
        })
    );

    // 잠시 기다린다
    Thread.sleep(200L);

    // 한번에 구독을 해지한다
    compositeDisposable.dispose();
  }
}
