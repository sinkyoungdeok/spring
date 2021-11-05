package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelloTraceV2Test {

  @Test
  void begin_end() {
    HelloTraceV2 trace = new HelloTraceV2();
    TraceStatus status1 = trace.begin("hello1");
    TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
    trace.end(status2);
    trace.end(status1);
  }

  @Test
  void begin_exception_level2() {
    HelloTraceV2 trace = new HelloTraceV2();
    TraceStatus status1 = trace.begin("hello");
    TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
    trace.exception(status2, new IllegalStateException());
    trace.exception(status1, new IllegalStateException());
  }
}