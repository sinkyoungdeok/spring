package com.rxjava.chapter01;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class _L17_FlowableCancel {

  public static void main(String[] args) throws InterruptedException {

    // 200밀리초마다 값을 통지하는 Flowable
    Flowable.interval(200L, TimeUnit.MILLISECONDS)
            .subscribe(new Subscriber<Long>() {

              private Subscription subscription;
              private long startTime;

              @Override
              public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                this.startTime = System.currentTimeMillis();
                this.subscription.request(Long.MAX_VALUE);
              }

              @Override
              public void onNext(Long data) {
                // 구독 시작부터 500밀리초가 지나면 구독을 해지하고 처리를 중지한다
                if ((System.currentTimeMillis() - startTime) > 500) {
                  subscription.cancel(); // 구독을 해지한다
                  System.out.println("구독 해지");
                  return;
                }

                System.out.println("data=" + data);
              }

              @Override
              public void onComplete() {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + ": 완료");
              }

              @Override
              public void onError(Throwable error) {
                error.printStackTrace();
              }
            });

    Thread.sleep(2000L);
  }

}
