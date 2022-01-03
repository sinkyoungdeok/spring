package com.rxjava.chapter01;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public abstract interface _L24_Processor<T, R> extends Subscriber<T>, Publisher<R> {

}
