package com.rxjava.chapter03;

import com.rxjava.chapter02._L05_Counter;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class _L13_CounterSample {

  public static void main(String[] args) throws InterruptedException {
    final _L05_Counter counter = new _L05_Counter();

    Flowable.range(1, 10000)
        .subscribeOn(Schedulers.computation())
        .observeOn(Schedulers.computation())
        .subscribe(
            data -> counter.increment(),
            error -> System.out.println("에러=" + error),
            () -> System.out.println("counter.get()=" + counter.get()));

    Flowable.range(1, 10000)
        .subscribeOn(Schedulers.computation())
        .observeOn(Schedulers.computation())
        .subscribe(
            data -> counter.increment(),
            error -> System.out.println("에러=" + error),
            () -> System.out.println("counter.get()=" + counter.get()));

    Thread.sleep(1000L);
  }




}
