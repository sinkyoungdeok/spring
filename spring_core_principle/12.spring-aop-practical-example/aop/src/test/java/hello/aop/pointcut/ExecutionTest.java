package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.*;

import hello.aop.member.MemberServiceImpl;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

@Slf4j
public class ExecutionTest {

  AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
  Method helloMethod;

  @BeforeEach
  public void init() throws NoSuchMethodException {
    helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
  }

  @Test
  void printMethod() {
    //public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    log.info("helloMethod={}", helloMethod);
  }

  @Test
  void exactMatch() {
    //public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
    pointcut
        .setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void allMatch() {
    pointcut.setExpression("execution(* *(..)))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void nameMatch() {
    pointcut.setExpression("execution(* hello(..)))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void nameMatchStar1() {
    pointcut.setExpression("execution(* hel*(..)))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void nameMatchStar2() {
    pointcut.setExpression("execution(* *el*(..)))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  void nameMatchFalse() {
    pointcut.setExpression("execution(* nono(..)))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
  }

  @Test
  void packageExactMatch1() {
    pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
    assertThat(pointcut.matches(helloMethod,
        MemberServiceImpl.class)).isTrue();
  }

  @Test
  void packageExactMatch2() {
    pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
    assertThat(pointcut.matches(helloMethod,
        MemberServiceImpl.class)).isTrue();
  }

  @Test
  void packageExactFalse() {
    pointcut.setExpression("execution(* hello.aop.*.*(..))");
    assertThat(pointcut.matches(helloMethod,
        MemberServiceImpl.class)).isFalse();
  }

  @Test
  void packageMatchSubPackage1() {
    pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
    assertThat(pointcut.matches(helloMethod,
        MemberServiceImpl.class)).isTrue();
  }

  @Test
  void packageMatchSubPackage2() {
    pointcut.setExpression("execution(* hello.aop..*.*(..))");
    assertThat(pointcut.matches(helloMethod,
        MemberServiceImpl.class)).isTrue();
  }

  @Test
  void typeExactMatch() {
    pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
    assertThat(pointcut.matches(helloMethod,
        MemberServiceImpl.class)).isTrue();
  }

  @Test
  void typeMatchSuperType() {
    pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
    assertThat(pointcut.matches(helloMethod,
        MemberServiceImpl.class)).isTrue();
  }

  @Test
  void typeMatchInternal() throws NoSuchMethodException {
    pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
    Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
    assertThat(pointcut.matches(internalMethod,
        MemberServiceImpl.class)).isTrue();
  }

  @Test
  void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
    pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
    Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
    assertThat(pointcut.matches(internalMethod,
        MemberServiceImpl.class)).isFalse();
  }

  //String 타입의 파라미터 허용
  //(String)
  @Test
  void argsMatch() {
    pointcut.setExpression("execution(* *(String))");
    assertThat(pointcut.matches(helloMethod,
        MemberServiceImpl.class)).isTrue();
  }

  //파라미터가 없어야 함
  //()
  @Test
  void argsMatchNoArgs() {
    pointcut.setExpression("execution(* *())");
    assertThat(pointcut.matches(helloMethod,
        MemberServiceImpl.class)).isFalse();
  }

  //정확히 하나의 파라미터 허용, 모든 타입 허용
  //(Xxx)
  @Test
  void argsMatchStar() {
    pointcut.setExpression("execution(* *(*))");
    assertThat(pointcut.matches(helloMethod,
        MemberServiceImpl.class)).isTrue();
  }

  //숫자와 무관하게 모든 파라미터, 모든 타입 허용
  //(),(Xxx), (Xxx, Xxx)
  @Test
  void argsMatchAll() {
    pointcut.setExpression("execution(* *(..))");
    assertThat(pointcut.matches(helloMethod,
        MemberServiceImpl.class)).isTrue();
  }

  //String 타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용
  //(String),(String, Xxx), (String, Xxx, Xxx)
  @Test
  void argsMatchComplex() {
    pointcut.setExpression("execution(* *(String, ..))");
    assertThat(pointcut.matches(helloMethod,
        MemberServiceImpl.class)).isTrue();
  }
}
