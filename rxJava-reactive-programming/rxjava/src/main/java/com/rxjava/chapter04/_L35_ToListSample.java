package com.rxjava.chapter04;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import java.util.List;

public class _L35_ToListSample {

  public static void main(String[] args) throws InterruptedException {
    Single<List<String>> single =
        Flowable.just("A", "B", "C", "D", "E")
            .toList();

    single.subscribe();
  }

}
