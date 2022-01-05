package com.rxjava.chapter02;

import java.util.Date;

public class _L10_ImmutableObject {

  private final Date value;

  public _L10_ImmutableObject(Date date) {
    // 가변적인 Date가 변경돼도 영향이 없도록 복제한 값을 설정한다
    this.value = (Date) date.clone();
  }

  public Date getValue() {
    // 반환값 Date가 외부에서 변경돼도 영향이 없도록 복제한 값을 반환한다
    return (Date) value.clone();
  }

  public _L10_ImmutableObject changeValue(Date value) {
    return new _L10_ImmutableObject(value);
  }

}
