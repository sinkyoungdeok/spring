package com.rxjava.chapter06;

import io.reactivex.Flowable;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class _L15_BlockingIterableTest {

  public static void main(String[] args) throws InterruptedException {
    Iterable<Long> result =
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .take(5)
            .blockingIterable();

    Iterator<Long> iterator = result.iterator();

    System.out.println(iterator.hasNext());

    System.out.println(iterator.next());
    System.out.println(iterator.next());
    System.out.println(iterator.next());

    Thread.sleep(1000L);

    System.out.println(iterator.next());
    System.out.println(iterator.next());

    System.out.println(iterator.hasNext());
  }

}
