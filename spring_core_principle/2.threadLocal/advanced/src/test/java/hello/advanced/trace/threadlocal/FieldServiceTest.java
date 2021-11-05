package hello.advanced.trace.threadlocal;

import hello.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class FieldServiceTest {

  private FieldService fieldService = new FieldService();

  @Test
  void field() {
    log.info("main start");

//    Runnable userA = new Runnable() {
//      @Override
//      public void run() {
//        fieldService.logic("userA")();
//      }
//    };

    Runnable userA = () -> {
      fieldService.logic("userA");
    };
    Runnable userB = () -> {
      fieldService.logic("userB");
    };

    Thread threadA = new Thread(userA);
    threadA.setName("thread-A");
    Thread threadB = new Thread(userB);
    threadB.setName("thread-B");

    threadA.start();
//    sleep(2000); // 동시성 문제 발생X
    sleep(100); // 동시성 문제 발생O
    threadB.start();

    sleep(3000); // 메인 쓰레드 종료 대기
    log.info("main exit");
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
