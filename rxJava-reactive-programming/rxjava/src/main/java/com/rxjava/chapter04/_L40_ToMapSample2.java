package com.rxjava.chapter04;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.Map;

public class _L40_ToMapSample2 {

  public static void main(String[] args) {
    Single<Map<Long,String>> single =
        Flowable.just("1A", "2B", "3C", "1D", "2E")
            .toMap(
                data -> Long.valueOf(data.substring(0,1)),
                data -> data.substring(1));

    single.subscribe(); // main: {1=D, 2=E, 3=C}
  }

}
