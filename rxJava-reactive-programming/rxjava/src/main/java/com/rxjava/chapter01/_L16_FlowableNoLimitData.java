package com.rxjava.chapter01;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class _L16_FlowableNoLimitData {

  public static void main(String[] args) throws InterruptedException {
    Flowable<String> flowable =
        Flowable.create(new FlowableOnSubscribe<String>() {
          @Override
          public void subscribe(@NonNull FlowableEmitter<String> emitter) throws Exception {

            String[] datas = {"Hello, World!", "안녕 RxJava!"};

            for (String data: datas) {
              if (emitter.isCancelled()) {
                return;
              }

              emitter.onNext(data);
            }

            emitter.onComplete();
          }
        }, BackpressureStrategy.BUFFER);

    flowable
        .observeOn(Schedulers.computation())
        .subscribe(new Subscriber<String>() {
          private Subscription subscription;

          @Override
          public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            // 데이터 개수를 제한하지 않고 데이터를 통지하게 요청한다
            this.subscription.request(Long.MAX_VALUE);
          }

          @Override
          public void onNext(String data) {
            String threadName = Thread.currentThread().getName();
            System.out.println("data = " + data);
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

    Thread.sleep(500L);
  }

}
