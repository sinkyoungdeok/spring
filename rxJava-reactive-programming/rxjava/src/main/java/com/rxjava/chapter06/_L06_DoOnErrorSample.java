package com.rxjava.chapter06;

import com.rxjava.chapter04._L01_DebugSubscriber;
import io.reactivex.Flowable;

public class _L06_DoOnErrorSample {

  public static void main(String[] args) {
    Flowable.range(1, 5)
        .doOnError(error -> System.out.println("기존 데이터: " + error.getMessage()))
        .map(data -> {
          if (data == 3) {
            throw new Exception("예외 발생");
          }
          return data;
        })
        .doOnError(
            error -> System.out.println("--- map 적용 후: " + error.getMessage()))
        .subscribe(new _L01_DebugSubscriber<>());
  }

}
