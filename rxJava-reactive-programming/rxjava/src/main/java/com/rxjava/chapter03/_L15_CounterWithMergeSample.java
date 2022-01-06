package com.rxjava.chapter03;

import com.rxjava.chapter02._L05_Counter;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class _L15_CounterWithMergeSample {

  public static void main(String[] args) throws InterruptedException {
    final _L05_Counter counter = new _L05_Counter();

    Flowable<Integer> source1 = Flowable.range(1, 10000)
        .subscribeOn(Schedulers.computation())
        .observeOn(Schedulers.computation());

    Flowable<Integer> source2 = Flowable.range(1, 10000)
        .subscribeOn(Schedulers.computation())
        .observeOn(Schedulers.computation());

    Flowable.merge(source1, source2)
        .subscribe(
            data -> counter.increment(),
            error -> System.out.println("error = " + error),
            () -> System.out.println("counter = " + counter.get()));

    Thread.sleep(1000L);
  }

}
