package com.rxjava.chapter02;

public class _L14_SynchronizedPoint {

  private final Object lock = new Object();

  private int x;
  private int y;

  void rightUp() {
    synchronized (lock) {
      x ++;
      y ++;
    }
  }

  int getX() {
    synchronized (lock) {
      return x;
    }
  }

  int getY() {
    synchronized (lock) {
      return y;
    }
  }


}
