package com.rxjava.chapter02;

public class _L15_SynchronizedPoint2 {

  private int x;
  private int y;

  synchronized void rightUp() {
      x ++;
      y ++;
  }

  synchronized int getX() {
    return x;
  }

  synchronized int getY() {
    return y;
  }

}
