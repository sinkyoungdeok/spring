package com.rxjava.chapter02;

public class _L08_ReferenceTypeTest {

  public static void main(String[] args) {
    final _L07_ReferenceTypeTest instance = new _L07_ReferenceTypeTest();

    // 참조를 변경하면 컴파일 에러가 발생한다.
    //instance = new _L07_ReferenceTypeTest();

    System.out.println(instance.getValue()); // "A"

    instance.setValue("B");

    System.out.println(instance.getValue()); // "B"


  }



}
