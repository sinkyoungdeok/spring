package com.rxjava.chapter06;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import java.util.concurrent.TimeUnit;

public class _L18_TestSubscriberTest {

  public static void main(String[] args) throws InterruptedException {
    Flowable<Long> target = Flowable.interval(100L, TimeUnit.MILLISECONDS);

    TestSubscriber<Long> testSubscriber = target.test();

    testSubscriber.assertEmpty();

    testSubscriber.await(150L, TimeUnit.MILLISECONDS);

    testSubscriber.assertValues(0L);

    testSubscriber.await(100L, TimeUnit.MILLISECONDS);

    testSubscriber.assertValues(0L, 1L);
  }

}
