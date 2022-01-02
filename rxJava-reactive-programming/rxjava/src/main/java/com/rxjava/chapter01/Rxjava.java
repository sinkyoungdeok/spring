package com.rxjava.chapter01;

import com.rxjava.RxjavaApplication;
import io.reactivex.Flowable;
import org.springframework.boot.SpringApplication;

public class Rxjava {

  public static void main(String[] args) {
    Flowable<String> flowable = Flowable.just("Hello","World");

    flowable.subscribe(data -> System.out.println(data));
  }

}
