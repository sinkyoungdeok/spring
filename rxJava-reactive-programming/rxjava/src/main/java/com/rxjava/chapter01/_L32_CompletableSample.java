package com.rxjava.chapter01;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class _L32_CompletableSample {

  public static void main(String[] args) throws InterruptedException {
    // Completable 생성
    Completable completable = Completable.create(emitter -> {
      // 중략 ( 업무 로직 처리 )

      // 완료 통지
      emitter.onComplete();
    });

    completable
        .subscribeOn(Schedulers.computation())
        .subscribe(new CompletableObserver() {

          @Override
          public void onSubscribe(@NonNull Disposable d) {
            // 아무것도 하지 않는다.
          }

          @Override
          public void onComplete() {
            System.out.println("완료");
          }

          @Override
          public void onError(@NonNull Throwable e) {
            System.out.println("e = " + e);
          }
        });

    // 잠시 기다린다
    Thread.sleep(100L);
  }

}
