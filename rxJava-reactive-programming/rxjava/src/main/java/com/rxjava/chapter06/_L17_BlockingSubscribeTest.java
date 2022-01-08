package com.rxjava.chapter06;

import com.rxjava.chapter02._L05_Counter;
import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.concurrent.TimeUnit;
import org.w3c.dom.css.Counter;

public class _L17_BlockingSubscribeTest {

  public static void main(String[] args) {
    Flowable<Long> flowable =
        Flowable.interval(100L, TimeUnit.MILLISECONDS)
            .take(5);

    _L05_Counter counter = new _L05_Counter();

    flowable
        .blockingSubscribe(new DisposableSubscriber<Long>() {
          @Override
          public void onNext(Long aLong) {
            counter.increment();
          }

          @Override
          public void onError(Throwable t) {
            System.out.println(t.getMessage());
          }

          @Override
          public void onComplete() {

          }
        });

    System.out.println(counter.get());
  }

}
