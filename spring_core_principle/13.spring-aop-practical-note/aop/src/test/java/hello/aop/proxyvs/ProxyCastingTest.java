package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.member.annotation.ClassAop;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.stereotype.Component;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ClassAop
@Component
public class ProxyCastingTest {

  @Test
  void jdkProxy() {
    MemberService target = new MemberServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시

    //프록시를 인터페이스로 캐스팅 성공
    MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

    //JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발생
    assertThrows(ClassCastException.class, () -> {
      MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    });

  }

  @Test
  void cglibProxy() {
    MemberService target = new MemberServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(true); // CGLIB 동적 프록시

    //프록시를 인터페이스로 캐스팅 성공
    MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

    //CGLIB 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발생
    MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;

  }
}
