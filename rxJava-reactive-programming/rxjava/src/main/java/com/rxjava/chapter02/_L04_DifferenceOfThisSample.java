package com.rxjava.chapter02;


import io.reactivex.functions.Action;

public class _L04_DifferenceOfThisSample {

  public static void main(String[] args) throws Exception {
    _L04_DifferenceOfThisSample target = new _L04_DifferenceOfThisSample();
    target.execute();
  }

  public void execute() throws Exception {
    // 익명 클래스
    Action anonymous = new Action() {
      @Override
      public void run() {
        System.out.println("익명 클래스의 this: " + this);
      }
    };

    // 람다식
    Action lambda = () -> System.out.println("람다식의 this: " + this);

    // 각각 실행
    anonymous.run();
    lambda.run();
  }

  @Override
  public String toString() {
    return  this.getClass().getSimpleName();
  }

}
