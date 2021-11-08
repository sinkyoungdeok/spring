package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

  private final OrderRepositoryV5 orderRepository;
  private final TraceTemplate traceTemplate;

  public OrderServiceV5(OrderRepositoryV5 orderRepository,
      LogTrace trace) {
    this.orderRepository = orderRepository;
    this.traceTemplate = new TraceTemplate(trace);
  }

  public void orderItem(String itemId) {
    traceTemplate.execute("OrderService.orderItem()", () -> {
      orderRepository.save(itemId);
      return null;
    });
  }
}
