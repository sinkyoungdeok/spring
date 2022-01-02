package com.rxjava.chapter01;

import io.reactivex.Flowable;

public class _L01_HelloWorldSample {

  public static void main(String[] args) {
    Flowable<String> flowable = Flowable.just("Hello","World");

    flowable.subscribe(data -> System.out.println(data));
  }

}
