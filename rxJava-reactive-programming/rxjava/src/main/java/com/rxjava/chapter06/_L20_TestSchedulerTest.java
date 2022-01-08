package com.rxjava.chapter06;

import io.reactivex.Flowable;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;
import java.util.concurrent.TimeUnit;

public class _L20_TestSchedulerTest {

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    TestScheduler testScheduler = new TestScheduler();

    Flowable<Long> flowable =
        Flowable.interval(500L, TimeUnit.MILLISECONDS, testScheduler);

    TestSubscriber<Long> result = flowable.test();

    System.out.println("data=" + result.values());
    result.assertEmpty();

    testScheduler.advanceTimeBy(500L, TimeUnit.MILLISECONDS);

    System.out.println("data=" + result.values());

    testScheduler.advanceTimeBy(500L, TimeUnit.MILLISECONDS);

    System.out.println("data=" + result.values());
    result.assertValues(0L, 1L);

    testScheduler.advanceTimeTo(2000L, TimeUnit.MILLISECONDS);

    System.out.println("data=" + result.values());
    result.assertValues(0L, 1L, 2L, 3L);

    System.out.println(
        "testSchedule#now=" + testScheduler.now(TimeUnit.MILLISECONDS));

    long totalTime = System.currentTimeMillis() - start;
    System.out.println("테스트에 걸린 시간 = " + totalTime);
  }

}
