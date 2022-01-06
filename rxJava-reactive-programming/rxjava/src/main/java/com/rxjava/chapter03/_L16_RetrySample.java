package com.rxjava.chapter03;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class _L16_RetrySample {

  public static void main(String[] args) {
    Flowable<Integer> flowable = Flowable.<Integer> create(emitter -> {
      System.out.println("Flowable 처리 시작");

      for(int i=1; i<=3; i++) {
        if (i==2) {
          throw new Exception("예외 발생");
        }
        emitter.onNext(i);
      }

      emitter.onComplete();
      System.out.println("Flowable 처리 완료");
    }, BackpressureStrategy.BUFFER)
        .doOnSubscribe(
            subscription -> System.out.println("flowable: doOnSubscribe"))
        .retry(2);

    flowable.subscribe(new Subscriber<Integer>() {
      @Override
      public void onSubscribe(Subscription s) {
        System.out.println("subscriber: OnSubscribe");
        s.request(Long.MAX_VALUE);
      }

      @Override
      public void onNext(Integer integer) {
        System.out.println("integer = " + integer);
      }

      @Override
      public void onError(Throwable t) {
        System.out.println("t = " + t);
      }

      @Override
      public void onComplete() {
        System.out.println("종료");
      }
    });
  }

}
