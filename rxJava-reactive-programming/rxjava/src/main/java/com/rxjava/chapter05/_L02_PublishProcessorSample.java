package com.rxjava.chapter05;

import com.rxjava.chapter04._L01_DebugSubscriber;
import io.reactivex.processors.PublishProcessor;

public class _L02_PublishProcessorSample {

  public static void main(String[] args) {
    PublishProcessor<Integer> processor = PublishProcessor.create();

    processor.subscribe(new _L01_DebugSubscriber<>("No.1"));

    processor.onNext(1);
    processor.onNext(2);
    processor.onNext(3);

    System.out.println("Subscriber No.2 추가");
    processor.subscribe(new _L01_DebugSubscriber<>("---No.2"));

    processor.onNext(4);
    processor.onNext(5);

    processor.onComplete();

    System.out.println("Subscriber No.3 추가");
    processor.subscribe(new _L01_DebugSubscriber<>("------ No.3"));
  }

}
