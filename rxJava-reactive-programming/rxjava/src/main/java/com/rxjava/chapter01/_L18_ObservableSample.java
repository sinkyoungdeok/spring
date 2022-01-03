package com.rxjava.chapter01;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class _L18_ObservableSample {

  public static void main(String[] args) throws InterruptedException {
    // 인사말을 통지하는 Observable 생성
    Observable<String> observable =
        Observable.create(new ObservableOnSubscribe<String>() {
          @Override
          public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
            // 통지 데이터
            String[] datas = {"Hello, World!", "안녕, RxJava!"};

            for (String data: datas) {
              // 구독이 해지되면 처리를 중단한다
              if (emitter.isDisposed()) {
                return;
              }

              // 데이터를 통지한다
              emitter.onNext(data);
            }
            // 완료를 통지한다
            emitter.onComplete();
          }
        });

    observable
        // 소비하는 측의 처리를 개별 스레드로 실행한다
        .observeOn(Schedulers.computation())
        // 구독한다
        .subscribe(new Observer<String>() {

          // subscribe 메서드 호출 시의 처리
          @Override
          public void onSubscribe(@NonNull Disposable d) {
            // 아무것도 하지 않는다
          }

          // 데이터를 받을 때의 처리
          @Override
          public void onNext(@NonNull String item) {
            // 실행하는 스레드 이름을 얻는다
            String threadName = Thread.currentThread().getName();
            // 받은 데이터를 출력한다
            System.out.println(threadName + " " + item);
          }

          // 오류 통지 시의 처리
          @Override
          public void onError(@NonNull Throwable error) {
            error.printStackTrace();
          }

          // 완료 통지 시의 처리
          @Override
          public void onComplete() {
            // 실행하는 스레드 이름을 얻는다
            String threadName = Thread.currentThread().getName();
            System.out.println("threadName = " + threadName);
          }
        });

    // 잠시 기다린다
    Thread.sleep(500L);
  }

}
