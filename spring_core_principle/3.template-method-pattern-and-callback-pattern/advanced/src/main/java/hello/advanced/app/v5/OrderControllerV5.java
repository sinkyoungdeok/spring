package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceCallback;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerV5 {

  private final OrderServiceV5 orderService;
  private final TraceTemplate traceTemplate;
  
  public OrderControllerV5(OrderServiceV5 orderService,
      LogTrace trace) {
    this.orderService = orderService;
    this.traceTemplate = new TraceTemplate(trace);
  }

  @GetMapping("/v5/request")
  public String request(String itemId) {
    return traceTemplate.execute("OrderController.request()", () -> {
      orderService.orderItem(itemId);
      return "ok";
    });
  }
}
