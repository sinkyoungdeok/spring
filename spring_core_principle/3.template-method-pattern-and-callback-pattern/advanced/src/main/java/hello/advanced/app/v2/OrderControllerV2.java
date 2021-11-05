package hello.advanced.app.v2;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

  private final OrderServiceV2 orderService;
  private final HelloTraceV2 trace;

  @GetMapping("/v2/request")
  public String request(String itemId) {

    TraceStatus status = null;
    try {
      status = trace.begin("OrderController.request()");

      orderService.orderItem(status.getTraceId(), itemId);

      trace.end(status);
      return "ok";
    } catch (Exception e) {
      trace.exception(status, e);
      throw e; // 예외를 꼭 다시 던져주어야 한다.
    }
  }
}
