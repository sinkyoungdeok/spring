package com.rxjava.chapter03;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;

public class _L20_SetCancellable {

  public static void main(String[] args) {
    Flowable<String> flowable =
        Flowable.create(new FlowableOnSubscribe<String>() {
          @Override
          public void subscribe(@NonNull FlowableEmitter<String> emitter) throws Exception {
//            emitter.setCancellable( () -> this.close());

            String[] datas = {"Hello, World!", "안녕 RxJava!"};

            for (String data: datas) {
              if (emitter.isCancelled()) {
                return;
              }

              emitter.onNext(data);
            }

            emitter.onComplete();
          }
        }, BackpressureStrategy.BUFFER); // 초과한 데이터는 버퍼링한다
  }

}
