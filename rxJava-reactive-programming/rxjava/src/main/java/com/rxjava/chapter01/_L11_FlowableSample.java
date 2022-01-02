package com.rxjava.chapter01;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class _L11_FlowableSample {

  public static void main(String[] args) throws InterruptedException {
    // 인사말을 통지하는 Flowable을 생성한다
    Flowable<String> flowable =
        Flowable.create(new FlowableOnSubscribe<String>() {
          @Override
          public void subscribe(@NonNull FlowableEmitter<String> emitter) throws Exception {

            String[] datas = {"Hello, World!", "안녕 RxJava!"};

            for (String data: datas) {
              // 구독이 해지되면 처리를 중단한다
              if (emitter.isCancelled()) {
                return;
              }

              // 데이터를 통지한다
              emitter.onNext(data);
            }

            // 완료를 통지한다
            emitter.onComplete();
          }
        }, BackpressureStrategy.BUFFER); // 초과한 데이터는 버퍼링한다

    flowable
        // Subscriber 처리를 개별 스레드에서 실행한다
        .observeOn(Schedulers.computation())
        // 구독한다
        .subscribe(new Subscriber<String>() {

          // 데이터 개수 요청과 구독 해지를 하는 객체
          private Subscription subscription;

          // 구독 시작 시 처리
          @Override
          public void onSubscribe(Subscription subscription) {
            // Subscription을 Subscriber에 보관한다.
            this.subscription = subscription;
            // 받을 데이터 개수를 요청한다.
            this.subscription.request(1L);
          }

          // 데이터를 받을 때 처리
          @Override
          public void onNext(String data) {
            // 실행 중인 스레드 이름을 얻는다
            String threadName = Thread.currentThread().getName();
            // 받은 데이터를 출력한다
            System.out.println("data = " + data);
            // 다음에 받을 데이터 개수를 요청한다
            this.subscription.request(1L);
          }

          // 완료 통지 시 처리
          @Override
          public void onComplete() {
            // 실행 중인 스레드 이름을 얻는다
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + ": 완료");
          }

          // 에러 통지 시 처리
          @Override
          public void onError(Throwable error) {
            error.printStackTrace();
          }
        });

    // 잠시 기다린다
    Thread.sleep(500L);
  }

}
