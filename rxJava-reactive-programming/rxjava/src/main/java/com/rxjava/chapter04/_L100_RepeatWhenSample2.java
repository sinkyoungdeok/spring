package com.rxjava.chapter04;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L100_RepeatWhenSample2 {

  public static void main(String[] args) throws InterruptedException {
    Flowable<String> flowable =
        Flowable.interval(100L, TimeUnit.MILLISECONDS) // 원본 Flowable이 interval 메서드처럼 다른 스레드에서 처리되면 아래 Flowable이 비동기 처리를 하려고 두번 째 데이터를 통보하자마자 바로 완료를 통지한다.
            .take(3)
            .repeatWhen(completeHandler -> {
              return completeHandler
                  .delay(1000L, TimeUnit.MILLISECONDS)
                  .take(2) // 1,2,3을 2번
                  .doOnNext(data -> System.out.println("emit: " + data))
                  .doOnComplete(() -> System.out.println("complete"));
            })
            .map(data -> {
              long time = System.currentTimeMillis();
              return time + "ms: " + data;
            });

    flowable.subscribe(new _L01_DebugSubscriber<>());

    Thread.sleep(5000L);
  }

}
