package com.rxjava.chapter01;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class _L10_EffectedSample {
  // 계산 방법을 나타내는 enum 객체
  private enum State {
    ADD, MULTIPLY;
  };

  // 계산 방법
  private static State calcMethod;

  public static void main(String[] args) throws InterruptedException {

    // 계산 방법을 덧셈으로 설정한다
    calcMethod = State.ADD;

    Flowable<Long> flowable =
        // 300밀리초마다 데이터를 통지하는 Flowable을 생성한다
        Flowable.interval(300, TimeUnit.MILLISECONDS)
            // 7건까지 통지한다
            .take(7)
            // 각 데이터를 계산한다.
            .scan((sum, data) -> {
              if (calcMethod == State.ADD) {
                return sum + data;
              } else {
                return sum * data;
              }
            });

    // 구독하고 받은 데이터를 출력한다
    flowable.subscribe(data -> System.out.println("data = " + data));

    // 잠시 기다렸다가 계산 방법을 곱셈으로 변경한다
    Thread.sleep(1000);

    System.out.println("계산 방법 변경");
    calcMethod = State.MULTIPLY;

    // 잠시 기다린다
    Thread.sleep(2000);
  }

  // 위 방법은 위험한 방법이다.
  /*
  생산자에서 소비자까지의 처리가 노출되지 않게 폐쇄적으로 개발해야 된다.
  기본적으로는 생산자가 외부에서 데이터를 받아 데이터를 생성할 때와 소비자가 받은 데이터를 처리하고 이를 외부에 반영할 때만 외부 데이터를 참조하는 것이 좋다
   */
}
