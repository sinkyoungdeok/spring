package com.rxjava.chapter04;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.Map;

public class _L39_ToMapSample1 {

  public static void main(String[] args) {
    Single<Map<Long, String>> single =
        Flowable.just("1A", "2B", "3C", "1D", "2E")
            .toMap(data -> Long.valueOf(data.substring(0,1)));
    single.subscribe(); // main : {1=1D, 2=2E, 3=3C}
  }

}
