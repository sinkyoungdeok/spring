package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV2 {

  // private final ApplicationContext applicationContext;
  private final ObjectProvider<CallServiceV2> callServiceProvider;

  public void external() {
    log.info("call external");
// CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
    CallServiceV2 callServiceV2 = callServiceProvider.getObject();
    callServiceV2.internal(); //외부 메서드 호출
  }

  public void internal() {
    log.info("call internal");
  }
}