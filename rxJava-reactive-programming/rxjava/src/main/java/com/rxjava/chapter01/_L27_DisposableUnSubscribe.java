package com.rxjava.chapter01;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class _L27_DisposableUnSubscribe {

  public static void main(String[] args) throws InterruptedException {
    Flowable<String> flowable = Flowable.interval(100L, TimeUnit.MILLISECONDS).just("Hello","World","a","b","c");

    Disposable disposable =
        flowable.observeOn(Schedulers.computation()).subscribe(data -> System.out.println("data = " + data));


    disposable.dispose();

  }

}
