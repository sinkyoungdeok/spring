package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class _L07_IntervalSample {

  public static void main(String[] args) throws InterruptedException {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss.SSS");

    Flowable<Long> flowable = Flowable.interval(1000L, TimeUnit.MILLISECONDS);

    System.out.println("시작 시각: " + LocalTime.now().format(formatter));

    flowable.subscribe(data -> {
      String threadName = Thread.currentThread().getName();

      String time = LocalTime.now().format(formatter);
      System.out.println(threadName + ": " + time + ": data=" + data);
    });

    Thread.sleep(5000L);
  }

}
