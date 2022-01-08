package com.rxjava.chapter05;

import com.rxjava.chapter04._L01_DebugSubscriber;
import io.reactivex.processors.UnicastProcessor;

public class _L07_UnicastProcessorSample1 {

  public static void main(String[] args) {
    UnicastProcessor<Integer> processor = UnicastProcessor.create();

    processor.onNext(1);
    processor.onNext(2);

    System.out.println("Subscriber No.1 추가");
    processor.subscribe(new _L01_DebugSubscriber<>("No.1"));

    System.out.println("Subscriber No.2 추가");
    processor.subscribe(new _L01_DebugSubscriber<>("No.2"));

    processor.onNext(3);

    processor.onComplete();
  }

}
