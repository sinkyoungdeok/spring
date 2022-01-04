package com.rxjava.chapter01;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class _L31_MaybeSample {

  public static void main(String[] args) {
    // Maybe 생성
    Maybe<DayOfWeek> maybe = Maybe.create(emitter -> {
      emitter.onSuccess(LocalDate.now().getDayOfWeek());
    });

    // 구독
    maybe.subscribe(new MaybeObserver<DayOfWeek>() {

      // 구독 준비가 됐을 때의 처리
      @Override
      public void onSubscribe(@NonNull Disposable d) {
        // 아무것도 하지 않는다
      }

      // 데이터 통지를 받을 때의 처리
      @Override
      public void onSuccess(@NonNull DayOfWeek dayOfWeek) {
        System.out.println("dayOfWeek = " + dayOfWeek);
      }

      // 에러 통지를 받을 때의 처리
      @Override
      public void onError(@NonNull Throwable e) {
        System.out.println("e = " + e);
      }

      // 완료 통지를 받을 때의 처리
      @Override
      public void onComplete() {
        System.out.println("완료");
      }
    });
  }

}
