package com.rxjava.chapter03;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class _L01_IteratorSample {

  public static void main(String[] args) {
    // 리스트에서 Iterator를 얻는다
    List<String> list = Arrays.asList("a", "b", "c");
    Iterator<String> iterator = list.iterator();

    // 받을 데이터가 남아 있는지 확인한다
    while (iterator.hasNext()) {
      // 데이터를 얻는다
      String value = iterator.next();
      // 얻은 데이터를 출력한다
      System.out.println("value = " + value);
    }
  }

}
