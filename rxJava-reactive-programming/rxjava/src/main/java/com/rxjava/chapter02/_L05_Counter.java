package com.rxjava.chapter02;

public class _L05_Counter {
  private volatile int count;

  void increment() {
    count ++;
  }

  int get() {
    return count;
  }
}
