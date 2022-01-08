package com.rxjava.chapter06;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L14_BlockingLastTest {

  public static void main(String[] args) {
    long actual =
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .take(3)
            .blockingLast();

    System.out.println("actual = " + actual);
  }

}
