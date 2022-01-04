package com.rxjava.chapter01;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import java.util.concurrent.TimeUnit;

public class _L28_subscribeWith {

  public static void main(String[] args) throws InterruptedException {
    Flowable<String> flowable = Flowable.interval(100L, TimeUnit.MILLISECONDS).just("Hello","World","a","b","c");

    Disposable disposable =
        flowable.subscribeWith(new ResourceSubscriber<>() {
          @Override
          public void onNext(String s) {

          }

          @Override
          public void onError(Throwable t) {

          }

          @Override
          public void onComplete() {

          }
        });


    disposable.dispose();

  }

}
