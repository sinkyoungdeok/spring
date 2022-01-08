package com.rxjava.chapter06;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L13_BlockingFirstTest {

  public static void main(String[] args) {
    long actual =
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .blockingFirst();

    System.out.println("actual = " + actual);
  }

}
