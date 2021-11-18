package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * 구조를 분리
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {

  private final InternalService internalService;

  public void external() {
    log.info("call external");
    internalService.internal(); //외부 메서드 호출
  }
}