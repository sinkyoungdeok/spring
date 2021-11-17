package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

  //hello.aop.order 패키지와 하위 패키지
  @Pointcut("execution(* hello.aop.order..*(..))")
  public void allOrder() {} // pointcut signature

  //클래스 이름 패턴이 *Service
  @Pointcut("execution(* *..*Service.*(..))")
  public void allService() {}

  // allOrder && allService
  @Pointcut("allOrder() && allService()")
  public void orderAndService() {}

}
