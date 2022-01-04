package com.rxjava.chapter01;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class _L30_SingleSample {

  public static void main(String[] args) {
    // Single 생성
    Single<DayOfWeek> single = Single.create(emitter -> {
      emitter.onSuccess(LocalDate.now().getDayOfWeek());
    });

    // 구독
    single.subscribe(new SingleObserver<DayOfWeek>() {

      // 구독 준비가 됐을 때의 처리
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        // 아무것도 하지 않는다.
      }

      @Override
      public void onSuccess(@NonNull DayOfWeek dayOfWeek) {
        System.out.println("dayOfWeek = " + dayOfWeek);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        System.out.println("e = " + e);
      }
    });
  }

}
