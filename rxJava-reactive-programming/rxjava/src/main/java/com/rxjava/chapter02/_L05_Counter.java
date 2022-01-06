package com.rxjava.chapter02;

public class _L05_Counter {
  private volatile int count;

  public void increment() {
    count ++;
  }

  public int get() {
    return count;
  }
}
