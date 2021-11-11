package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component {

  private Component component;

  public MessageDecorator(Component component) {
    this.component = component;
  }

  @Override
  public String operation() {
    log.info("MessageDecorator 실행");
    String result = component.operation();
    String decoResult = "*****" + result + "*****";
    log.info("MessageDecorator 꾸미기 적용 전={}, 적용 후={}", result,
        decoResult);
    return decoResult;
  }
}