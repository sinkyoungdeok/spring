package com.rxjava.chapter02;

import java.util.concurrent.atomic.AtomicInteger;

public class _L13_Point {

  private final AtomicInteger x = new AtomicInteger(0);
  private final AtomicInteger y = new AtomicInteger(0);

  void rightUp() { // 단일 객체에 대해서만 원자성이 있으므로, x값만 변경한 시점에 다른 스레드에서 변경된 x값과 아직 변경되지 못한 y값을 가져간다.
    x.incrementAndGet();
    y.incrementAndGet();
  }

  int getX() {
    return x.get();
  }

  int getY() {
    return y.get();
  }
}
