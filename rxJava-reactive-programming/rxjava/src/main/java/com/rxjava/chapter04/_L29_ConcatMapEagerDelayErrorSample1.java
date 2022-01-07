package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L29_ConcatMapEagerDelayErrorSample1 {

  public static void main(String[] args) throws InterruptedException {
    Flowable<String> flowable = Flowable.range(10, 3)
        .concatMapEagerDelayError(
            sourceData -> Flowable.interval(500L, TimeUnit.MILLISECONDS)
                .take(3)
                .doOnNext(data -> {
                  if (sourceData == 11 && data == 1) {
                    throw new Exception("예외 발생");
                  }
                })
                .map(data -> "[" + sourceData + "] " + data),
            true); // 얘를 false로 지정하면 에러가 발생한 Flowable의 통지 순서가 됐을 대 바로 에러를 통지한다.
    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(4000L);
  }

}
