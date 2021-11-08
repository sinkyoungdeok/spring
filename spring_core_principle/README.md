# [1. 예제 만들기](./1.example-make)

<details> <summary> 1. 프로젝트 생성 </summary>

## 1. 프로젝트 생성

[https://start.spring.io/](https://start.spring.io/)

</details>


<details> <summary> 2. 예제 프로젝트 만들기 - V0 </summary>

## 2. 예제 프로젝트 만들기 - V0

- 학습을 위한 간단한 예제 프로젝트를 만들어보자.
- 상품을 주문하는 프로세스로 가정하고, 일반적인 웹 애플리케이션에서 Controller -> Service -> Repository로 이어지는 흐름을 최대한 단순하게 만들어보자.

**OrderRepositoryV0**
```java
package hello.advanced.app.v0;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {
 public void save(String itemId) {
 //저장 로직
 if (itemId.equals("ex")) {
 throw new IllegalStateException("예외 발생!");
 }
 sleep(1000);
 }
 private void sleep(int millis) {
 try {
 Thread.sleep(millis);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
}
```
- `@Repository`: 컴포넌트 스캔의 대상이 된다. 따라서 스프링 빈으로 자동 등록된다. 
- `sleep(1000)`: 리포지토리는 상품을 저장하는데 약 1초 정도 걸리는 것으로 가정하기 위해 1초 지연을 주었다 (1000ms)
- 예외가 발생하는 상황도 확인하기 위해 파라미터(`itemId`)의 값이 `ex`로 넘어오면 `IllegalStateException` 예외가 발생하도록 했다.

**OrderServiceV0**
```java
package hello.advanced.app.v0;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class OrderServiceV0 {
 private final OrderRepositoryV0 orderRepository;
 public void orderItem(String itemId) {
 orderRepository.save(itemId);
 }
}
```
- `@Service`: 컴포넌트 스캔의 대상이 된다.
- 실무에서는 복잡한 비즈니스 로직이 서비스 계층에 포함되지만, 애졔에서는 단순함을 위해서 리포지토리에 저장을 호출하는 코드만 있다.

**OrderControllerV0**
```java
package hello.advanced.app.v0;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
public class OrderControllerV0 {
 private final OrderServiceV0 orderService;
 @GetMapping("/v0/request")
 public String request(String itemId) {
 orderService.orderItem(itemId);
 return "ok";
 }
}
```
- `@RestController`: 컴포넌트 스캔과 스프링 Rest 컨트롤러로 인식된다.
- `/v0/request` 메서드는 HTTP 파라미터로 `itemId`를 받을 수 있다.


- 실행: http://localhost:8080/v0/request?itemId=hello
- 결과: `ok`

실무에서 일반적으로 사용하는 컨트롤러 -> 서비스 -> 리포지토리의 기본 흐름을 만들었다.
지금부터 이 흐름을 기반으로 예제를 점진적으로 발전시켜 나가면서 학습을 진행하겠다.

</details>

<details> <summary> 3. 로그 추적기 - 요구사항 분석 </summary>

## 3. 로그 추적기 - 요구사항 분석

새로운 회사에 입사 했는데, 수 년간 운영중인 거대한 프로젝트에 투입되었다. 전체 소스 코드는 수십만 라인이고, 클래스 수도 수 백개 이상이다.

우리가 처음 맡겨진 요구사항은 로그 추적기를 만드는 것이다.

애플리케이션이 커지면서 점점 모니터링과 운영이 중요해지는 단계이다. 특히 최근 자주 병목이 발생하고 있다.
어떤 부분에서 병목이 발생하는지, 그리고 어떤 부분에서 예외가 발생하는지를 로그를 통해 확인하는 것이 점점 중요해지고 있다.

기존에는 개발자가 문제가 발생한 다음에 관련 부분을 어렵게 찾아서 로그를 하나하나 직접 만들어서 남겼다.

로그를 미리 남겨둔다면 이런 부분을 손쉽게 찾을 수 있을 것이다. 

이부분을 개선하고 자동화하는 것이 우리의 미션이다.

**요구사항**
- 모든 PUBLIC 메서드의 호출과 응답 정보를 로그로 출력
- 애플리케이션의 흐름을 변경하면 안됨
  - 로그를 남긴다고 해서 비즈니스 로직의 동작에 영향을 주면 안됨
- 메서드 호출에 걸린 시간
- 정상 흐름과 예외 흐름 구분
  - 예외 발생시 예외 정보가 남아야 함
- 메서드 호출의 깊이 표현
- HTTP 요청을 구분
  - HTTP 요청 단위로 특정 ID를 남겨서 어떤 HTTP 요청에서 시작된 것인지 명확하게 구분이 가능해야 함
  - 트랜잭션 ID (DB 트랜잭션X), 여기서는 하나의 HTTP 요청이 시작해서 끝날 때 까지를 하나의 트랜잭션이라 함

**예시**
```
정상 요청
[796bccd9] OrderController.request()
[796bccd9] |-->OrderService.orderItem()
[796bccd9] | |-->OrderRepository.save()
[796bccd9] | |<--OrderRepository.save() time=1004ms
[796bccd9] |<--OrderService.orderItem() time=1014ms
[796bccd9] OrderController.request() time=1016ms

예외 발생
[b7119f27] OrderController.request()
[b7119f27] |-->OrderService.orderItem()
[b7119f27] | |-->OrderRepository.save()
[b7119f27] | |<X-OrderRepository.save() time=0ms
ex=java.lang.IllegalStateException: 예외 발생!
[b7119f27] |<X-OrderService.orderItem() time=10ms
ex=java.lang.IllegalStateException: 예외 발생!
[b7119f27] OrderController.request() time=11ms
ex=java.lang.IllegalStateException: 예외 발생!
``` 

**참고**
> 모니터링 툴을 도입하면 많은 부분이 해결되지만, 지금은 학습이 목적이라는 사실을 기억하자.

</details>

<details> <summary> 4. 로그 추적기V1 - 프로토타입 개발 </summary>

## 4. 로그 추적기V1 - 프로토타입 개발

애플리케이션의 모든 로직에 직접 로그를 남겨도 되지만, 그것보다는 더 효율적인 개발 방법이 필요하다.

특히 트랜잭션ID와 깊이를 표현하는 방법은 기존 정보를 이어 받아야 하기 떄문에 단순히 로그만 남긴다고 해결할 수 있는 것은 아니다.

요구사항에 맞추어 애플리케이션에 효과적으로 로그를 남기기 위한 로그 추적기를 개발해보자.

먼저 프로토타입 버전을 개발해보자. 아마 코드를 모두 작성하고 테스트 코드까지 실행해보아야 어떤 것을 하는지 감이 올 것이다.

먼저 로그 추적기를 위한 기반 데이터를 가지고 있는 `TraceId`, `TraceStatus` 클래스를 만들어보자. 

**TraceId**
```java
package hello.advanced.trace;
import java.util.UUID;
public class TraceId {
 private String id;
 private int level;
 public TraceId() {
 this.id = createId();
 this.level = 0;
 }
 private TraceId(String id, int level) {
 this.id = id;
 this.level = level;
 }
 private String createId() {
 return UUID.randomUUID().toString().substring(0, 8);
 }
 public TraceId createNextId() {
 return new TraceId(id, level + 1);
 }
 public TraceId createPreviousId() {
 return new TraceId(id, level - 1);
 }
 public boolean isFirstLevel() {
 return level == 0;
 }
 public String getId() {
 return id;
 }
 public int getLevel() {
 return level;
 }
}
```

**TraceId 클래스**
- 로그 추적기는 트랜잭션 ID와 깊이를 표현하는 방법이 필요하다.
- 여기서는 트랜잭션ID와 깊이를 표현하는 level을 묶어서 `TraceId`라는 개념을 만들었다.
- `TraceId`는 단순히 `id`(트랜잭션ID)와 `level`정보를 함께 가지고 있다.

```
[796bccd9] OrderController.request() //트랜잭션ID:796bccd9, level:0
[796bccd9] |-->OrderService.orderItem() //트랜잭션ID:796bccd9, level:1
[796bccd9] | |-->OrderRepository.save()//트랜잭션ID:796bccd9, level:2
```

**UUID**
- `TraceId`를 처음 생성하면 `createId()`를 사용해서 UUID를 만들어낸다.
- UUID가 너무 길어서 여기서는 앞 8자리만 사용한다.
- 이 정도면 로그를 충분히 구분할 수 있다.
- 여기서는 이렇게 만들어진 값을 트랜잭션ID로 사용한다.
```
ab99e16f-3cde-4d24-8241-256108c203a2 //생성된 UUID
ab99e16f //앞 8자리만 사용
``` 

**createNextId()**
- 다음 `TraceId`를 만든다. 
- 예제 로그를 잘 보면 깊이가 증가해도 트랜잭션ID는 같다.
- 대신에 깊이가 하나 증가한다.
- 실행 코드: `new TraceId(id, level + 1)`
```
[796bccd9] OrderController.request()
[796bccd9] |-->OrderService.orderItem() //트랜잭션ID가 같다. 깊이는 하나 증가한다
``` 
- 따라서 `createNextId()`를 사용해서 현재 `TraceId`를 기반으로 다음 `TraceId`를 만들면 `id`는 기존과 같고, `level`은 하나 증가한다.

**createPreviousId()**
- `createNextId()`의 반대 역할을 한다.
- `id`는 기존과 같고, `level`은 하나 감소한다.

**isFirstLevel()**
- 첫 번째 레벨 여부를 편리하게 확인할 수 있는 메서드

**TraceStatus**
```java
package hello.advanced.trace;
public class TraceStatus {
 private TraceId traceId;
 private Long startTimeMs;
 private String message;
 public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
 this.traceId = traceId;
 this.startTimeMs = startTimeMs;
 this.message = message;
 }
 public Long getStartTimeMs() {
 return startTimeMs;
 }
 public String getMessage() {
 return message;
 }
 public TraceId getTraceId() {
 return traceId;
 }
}
```

**TraceStatus 클래스**: 로그의 상태 정보를 나타낸다.
- 로그를 시작하면 끝이 있어야 한다.
```
[796bccd9] OrderController.request() //로그 시작
[796bccd9] OrderController.request() time=1016ms //로그 종료
``` 
- `TraceStatus`는 로그를 시작할 때의 상태 정보를 가지고 있다. 
- 이 상태 정보는 로그를 종료할 때 사용 된다.
- `traceId`: 내부에 트랜잭션 ID와 level을 가지고 있다. 
- `startTimeMs`: 로그 시작시간이다. 로그 종료시 이 시작 시간을 기준으로 시작~종료까지 전체 수행 시간을 구할 수 있다.
- `message`: 시작 시 사용한 메시지이다. 이후 로그 종료시에도 이 메시지를 사용해서 출력한다.


`TraceId`,`TraceStatus`를 사용해서 실제 로그를 생성하고, 처리하는 기능을 개발해보자

**HelloTraceV1**
```java
package hello.advanced.trace.hellotrace;
import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class HelloTraceV1 {
 private static final String START_PREFIX = "-->";
 private static final String COMPLETE_PREFIX = "<--";
 private static final String EX_PREFIX = "<X-";
 public TraceStatus begin(String message) {
 TraceId traceId = new TraceId();
 Long startTimeMs = System.currentTimeMillis();
 log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX,
traceId.getLevel()), message);
 return new TraceStatus(traceId, startTimeMs, message);
 }
 public void end(TraceStatus status) {
 complete(status, null);
 }
 public void exception(TraceStatus status, Exception e) {
 complete(status, e);
 }
 private void complete(TraceStatus status, Exception e) {
 Long stopTimeMs = System.currentTimeMillis();
 long resultTimeMs = stopTimeMs - status.getStartTimeMs();
 TraceId traceId = status.getTraceId();
 if (e == null) {
 log.info("[{}] {}{} time={}ms", traceId.getId(),
addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(),
resultTimeMs);
 } else {
 log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,
e.toString());
 }
 }
 private static String addSpace(String prefix, int level) {
 StringBuilder sb = new StringBuilder();
 for (int i = 0; i < level; i++) {
 sb.append( (i == level - 1) ? "|" + prefix : "| ");
 }
 return sb.toString();
 }
}
```
- `HelloTraceV1`을 사용해서 실제 로그를 시작하고 종료할 수 있다. 그리고 로그를 출력하고 실행시간도 측정할 수 있다.
- `@Component`: 싱글톤으로 사용하기 위해 스프링 빈으로 등록한다. 컴포넌트 스캔의 대상이 된다.

**공개 메서드**

로그 추적기에서 사용되는 공개 메서드는 다음 3가지이다.
- `begin(..)`
- `end(..)`
- `exception(..)`

**하나씩 자세히 알아보자**
- `TraceStatus begin(String message)`
  - 로그를 시작한다.
  - 로그 메시지를 파라미터로 받아서 시작 로그를 출력한다.
  - 응답 결과로 현재 로그의 상태인 `TRaceStatus`를 반환한다.
- `void end(TraceStatus status)`
  - 로그를 정상 종료한다.
  - 파라미터로 시작 로그의 상태(`TraceStatus`)를 전달 받는다. 이 값을 활용해서 실행 시간을 계산하고, 종료시에도 시작할 때와 동일한 로그 메시지를 출력할 수 있다.
  - 정상 흐름에서 호출한다.
- `void exception(TraceStatus status, Exception e)`
  - 로그를 예외 상황으로 종료한다.
  - `TraceStatus`, `Exception` 정보를 함께 전달 받아서 실행시간, 예외 정보를 포함한 결과 로그를 출력한다.
  - 예외가 발생했을 때 호출한다.

**비공개 메서드**
- `complete(TraceStatus status, Exception e)`
  - `end()`, `exception()` 의 요청 흐름을 한곳에서 편리하게 처리한다. 실행 시간을 측정하고 로그를 남긴다.
- `String addSpace(String prefix, int level)`: 다음과 같은 결과를 출력한다.
  - prefix: `-->`
    - level 0:
    - level 1: `-->`
    - level 2: `| |-->`
  - prefix: `<--`
    - level 0:
    - level 1: `|<--`
    - level 2: `| |<---`
  - prefix: `<X-`
    - level 0:
    - level 1: `|<X-`
    - level 2: `| |<X`
참고로 `HelloTraceV1`는 아직 모든 요구사항을 만족하지 못한다. 이후에 기능을 하나씩 추가할 예정이다.

테스트 작성

**HelloTraceV1Test**

주의! 테스트 코드는 `test/java/` 하위에 위치함
```java
package hello.advanced.trace.hellotrace;
import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;
class HelloTraceV1Test {
 @Test
 void begin_end() {
 HelloTraceV1 trace = new HelloTraceV1();
 TraceStatus status = trace.begin("hello");
 trace.end(status);
 }
 @Test
 void begin_exception() {
 HelloTraceV1 trace = new HelloTraceV1();
 TraceStatus status = trace.begin("hello");
 trace.exception(status, new IllegalStateException());
 }
}
```

테스트 코드를 보면 로그 추적기를 어떻게 실행해야 하는지, 그리고 어떻게 동작하는지 이해가 될 것이다.

**begin_end() - 실행 로그**
```
[41bbb3b7] hello
[41bbb3b7] hello time=5ms
```

**begin_exception() - 실행 로그**
```
[898a3def] hello
[898a3def] hello time=13ms ex=java.lang.IllegalStateException
```

이제 실제 애플리케이션에 적용해보자.

> 참고: 이것은 온전한 테스트 코드가 아니다. 일반적으로 테스트라고 하면 자동으로 검증하는 과정이
> 필요하다. 이 테스트는 검증하는 과정이 없고 결과를 콘솔로 직접 확인해야 한다. 이렇게 응답값이 없는
> 경우를 자동으로 검증하려면 여러가지 테스트 기법이 필요하다. 이번 강의에서는 예제를 최대한 단순화
> 하기 위해 검증 테스트를 생략했다.

> 주의: 지금까지 만든 로그 추적기가 어떻게 동작하는지 확실히 이해해야 다음 단계로 넘어갈 수 있다.
> 복습과 코드를 직접 만들어보면서 확실하게 본인 것으로 만들고 다음으로 넘어가자

</details>

<details> <summary> 5. 로그 추적기V1 - 적용 </summary>

## 5. 로그 추적기V1 - 적용 

이제 애플리케이션에 우리가 개발한 로그 추적기를 적용해보자.

기존 `v0` 패키지에 코드를 직접 작성해도 되지만, 기존 코드를 유지하고, 비교하기 위해서 `v1` 패키지를 새로 만들고 기존 코드를 복사하자. 복사하는 과정은 다음을 참고하자.

### V0 -> V1 복사 
- `hello.advanced.app.v1` 패키지 생성
- 복사
  - `v0.OrderRepositoryV0` -> `v1.OrderRepositoryV1`
  - `v0.OrderServiceV0` -> `v1.OrderServiceV1`
  - `v0.OrderControllerV0` -> `v1.OrderControllerV1`
- 코드 내부 의존관계 클래스를 V1으로 변경
  - `OrderControllerV1`: `OrderServiceV0` -> `OrderServiceV1`
  - `OrderServiceV1`: `OrderRepositoryV0` -> `OrderRepositoryV1`
- `OrderControllerV1` 매핑 정보 변경
  - `@GetMapping("/v1/request")`

실행해서 정상 동작하는지 확인하자.
- 실행: http://localhost:8080/v1/request?itemId=hello
- 결과: `ok`

### V1 적용하기
`OrderControllerV1`, `OrderServiceV1`, `OrderRepositoryV1`에 로그 추적기를 적용해보자.

먼저 컨트롤러에 우리가 개발한 `HelloTraceV1`을 적용해보자.

**OrderControllerV1**
```java
package hello.advanced.app.v1;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {
 private final OrderServiceV1 orderService;
 private final HelloTraceV1 trace;
 @GetMapping("/v1/request")
 public String request(String itemId) {
 TraceStatus status = null;
 try {
 status = trace.begin("OrderController.request()");
 orderService.orderItem(itemId);
 trace.end(status);
 return "ok";
 } catch (Exception e) {
 trace.exception(status, e);
 throw e; //예외를 꼭 다시 던져주어야 한다.
 }
 }
}
```
- `HelloTraceV1 trace`: `HelloTraceV1`을 주입 받는다. 참고로 `HelloTraceV1`은 `@Component` 애노테이션을 가지고 있기 때문에 컴포넌트 스캔의 대상이 된다. 따라서 자동으로 스프링 빈으로 등록된다.
- `trace.begin("OrderController.request()")`: 로그를 시작할 때 메시지 이름으로 컨트롤러 이름 + 메서드 이름을 주었다. 이렇게 하면 어떤 컨트롤러와 메서드가 호출되었는지 로그로 편리하게 확인할 수 있다. 물론 수작업이다.
- 단순하게 `trace.begin()`, `trace.end()` 코드 두 줄만 적용하면 될 줄 알았지만, 실상은 그렇지 않다. `trace.exception()`으로 예외까지 처리해야 하므로 지저분한 `try`, `catch` 코드가 추가된다.
- `begin()`의 결과 값으로 받은 `TraceStatus status` 값을 `end()`, `exception()`에 넘겨야 한다. 결국 `try`, `catch` 블록 모두에 이 값을 넘겨야 한다. 따라서 `try` 상위에 `TraceStatus status`코드를 선언해야 한다. 만약 `try`안에서 `TraceStatus status`를 선언하면 `try` 블록안에서만 해당 변수가 유효하기 때문에 `catch`블록에 넘길 수 없다. 따라서 컴파일 오류가 발생한다.
- `throw e`: 예외를 꼭 다시 던져주어야 한다. 그렇지 않으면 여기서 예외를 먹어버리고, 이후에 정상 흐름으로 동작한다. 로그는 애플리케이션에 흐름에 영향을 주면 안된다. 로그 때문에 예외가 사라지면 안된다.

실행
- 정상: http://localhost:8080/v1/request?itemId=hello
- 예외: http://localhost:8080/v1/request?itemId=ex

실행해보면 정상 흐름과 예외 모두 로그로 잘 출력되는 것을 확인할 수 있다. 나머지 부분도 완성하자.

**OrderServiceV1**
```java
package hello.advanced.app.v1;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class OrderServiceV1 {
 private final OrderRepositoryV1 orderRepository;
 private final HelloTraceV1 trace;
 public void orderItem(String itemId) {
 TraceStatus status = null;
 try {
 status = trace.begin("OrderService.orderItem()");
 orderRepository.save(itemId);
 trace.end(status);
 } catch (Exception e) {
 trace.exception(status, e);
 throw e;
 }
 }
}
```

**OrderRepositoryV1**
```java
package hello.advanced.app.v1;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {
 private final HelloTraceV1 trace;
 public void save(String itemId) {
 TraceStatus status = null;
 try {
 status = trace.begin("OrderRepository.save()");
 //저장 로직
 if (itemId.equals("ex")) {
 throw new IllegalStateException("예외 발생!");
 }
 sleep(1000);
 trace.end(status);
 } catch (Exception e) {
 trace.exception(status, e);
 throw e;
 }
 }
 private void sleep(int millis) {
 try {
 Thread.sleep(millis);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
}
```

정상 실행
- http://localhost:8080/v1/request?itemId=hello


**정상 실행 로그**
```
[11111111] OrderController.request()
[22222222] OrderService.orderItem()
[33333333] OrderRepository.save()
[33333333] OrderRepository.save() time=1000ms
[22222222] OrderService.orderItem() time=1001ms
[11111111] OrderController.request() time=1001ms
```
![image](https://user-images.githubusercontent.com/28394879/140095299-02d7c3f7-0a51-46f6-9cc3-dd9e7c7062ad.png)

> 참고: 아직 level 관련 기능을 개발하지 않았다. 
> 따라서 level값은 항상 0이다.
> 그리고 트랜잭션ID 값도 다르다. 
> 이부분은 아직 개발하지 않았다.


**예외 실행**
- http://localhost:8080/v1/request?itemId=ex

**예외 실행 로그**
```
[5e110a14] OrderController.request()
[6bc1dcd2] OrderService.orderItem()
[48ddffd6] OrderRepository.save()
[48ddffd6] OrderRepository.save() time=0ms ex=java.lang.IllegalStateException:
예외 발생!
[6bc1dcd2] OrderService.orderItem() time=6ms
ex=java.lang.IllegalStateException: 예외 발생!
[5e110a14] OrderController.request() time=7ms
ex=java.lang.IllegalStateException: 예외 발생!
```

`HelloTraceV1` 덕분에 직접 로그를 하나하나 남기는 것 보다는 편하게 여러가지 로그를 남길 수 있었다.
하지만 로그를 남기기 위한 코드가 생각보다 너무 복잡하다. 지금은 우선 요구사항과 동작하는 것에만 집중하자.


### 남은 문제
**요구사항**
- ~~모든 PUBLIC 메서드의 호출과 응답 정보를 로그로 출력~~
- ~~애플리케이션의 흐름을 변경하면 안됨~~
  - ~~로그를 남긴다고 해서 비즈니스 로직의 동작에 영향을 주면 안됨~~
- ~~메서드 호출에 걸린 시간~~
- ~~정상 흐름과 예외 흐름 구분~~
  - ~~예외 발생시 예외 정보가 남아야 함~~
- 메서드 호출의 깊이 표현
- HTTP 요청을 구분
  - HTTP 요청 단위로 특정 ID를 남겨서 어떤 HTTP 요청에서 시작된 것인지 명확하게 구분이 가능해야 함
  - 트랜잭션 ID (DB 트랜잭션 말고..)
  
아직 구현하지 못한 요구사항은 메서드 호출의 깊이를 표현하고, 같은 HTTP 요청이면 같은 트랜잭션 ID를 남기는 것이다.

이 기능은 직전 로그의 깊이와 트랜잭션 ID가 무엇인지 알아야 할 수 있는 일이다.

예를 들어서 `OrderController.request()`에서 로그를 남길 때 어떤 깊이와 어떤 트랜잭션 ID를 사용했는지를 그 다음에 로그를 남기는 `OrderService.orderItem()`에서 로그를 남길 때 알아야 한다.
결국 현재 로그의 상태 정보인 `트랜잭션ID`와 `level`이 다음으로 전달되어야 한다.
정리하면 로그에 대한 문액(`Context`) 정보가 필요하다.



</details>

<details> <summary> 6. 로그 추적기V2 - 파라미터로 동기화 개발 </summary>

## 6. 로그 추적기V2 - 파라미터로 동기화 개발

트랜잭션ID와 메서드 호출의 깊이를 표현하는 가장 단순한 방법은 첫 로그에서 사용한 `트랜잭션ID`와 `level`을 다음 로그에 넘겨주면 된다.

현재 로그의 상태 정보인 `트랜잭션ID`와 `level`은 `TraceId`에 포함되어 있다. 따라서 `TraceId`를 다음 로그에 넘겨주면 된다. 이 기능을 추가한 `HelloTraceV2`를 개발해보자. 

**HelloTraceV2**
```java
package hello.advanced.trace.hellotrace;
import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class HelloTraceV2 {
 private static final String START_PREFIX = "-->";
 private static final String COMPLETE_PREFIX = "<--";
 private static final String EX_PREFIX = "<X-";
 public TraceStatus begin(String message) {
 TraceId traceId = new TraceId();
 Long startTimeMs = System.currentTimeMillis();
 log.info("[" + traceId.getId() + "] " + addSpace(START_PREFIX,
traceId.getLevel()) + message);
 return new TraceStatus(traceId, startTimeMs, message);
 }
 //V2에서 추가
 public TraceStatus beginSync(TraceId beforeTraceId, String message) {
 TraceId nextId = beforeTraceId.createNextId();
 Long startTimeMs = System.currentTimeMillis();
 log.info("[" + nextId.getId() + "] " + addSpace(START_PREFIX,
nextId.getLevel()) + message);
 return new TraceStatus(nextId, startTimeMs, message);
 }
 public void end(TraceStatus status) {
 complete(status, null);
 }
 public void exception(TraceStatus status, Exception e) {
 complete(status, e);
 }
 private void complete(TraceStatus status, Exception e) {
 Long stopTimeMs = System.currentTimeMillis();
 long resultTimeMs = stopTimeMs - status.getStartTimeMs();
 TraceId traceId = status.getTraceId();
 if (e == null) {
 log.info("[" + traceId.getId() + "] " + addSpace(COMPLETE_PREFIX,
traceId.getLevel()) + status.getMessage() + " time=" + resultTimeMs + "ms");
 } else {
 log.info("[" + traceId.getId() + "] " + addSpace(EX_PREFIX,
traceId.getLevel()) + status.getMessage() + " time=" + resultTimeMs + "ms" + "
ex=" + e);
 }
 }
 private static String addSpace(String prefix, int level) {
 StringBuilder sb = new StringBuilder();
 for (int i = 0; i < level; i++) {
 sb.append( (i == level - 1) ? "|" + prefix : "| ");
 }
 return sb.toString();
 }
}
```

`HelloTraceV2`는 기존 코드인 `HelloTraceV2`와 같고, `beginSync(..)`가 추가되었다.

```java
//V2에서 추가
public TraceStatus beginSync(TraceId beforeTraceId, String message) {
 TraceId nextId = beforeTraceId.createNextId();
 Long startTimeMs = System.currentTimeMillis();
 log.info("[" + nextId.getId() + "] " + addSpace(START_PREFIX,
nextId.getLevel()) + message);
 return new TraceStatus(nextId, startTimeMs, message);
}
```

**beginSync(..)**
- 기존 `TraceId`에서 `createNextId()`를 통해 다음 ID를 구한다.
- `createNextId()`의 `TraceId` 생성 로직은 다음과 같다.
  - 트랜잭션ID는 기존과 같이 유지한다.
  - 깊이를 표현하는 Level은 하나 증가한다. (`0 -> 1`)

테스트 코드를 통해 잘 동작하는지 확인해보자.

**HelloTraceV2Test**
```java
package hello.advanced.trace.hellotrace;
import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;
class HelloTraceV2Test {
 @Test
 void begin_end_level2() {
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
```

처음에는 `begin(..)`을 사용하고, 이후에는 `beginSync(..)`를 사용하면 된다. `beginSync(..)`를 호출할 때 직전 로그의 `traceId` 정보를 넘겨주어야 한다.

**begin_end_level2() - 실행 로그**
```
[0314baf6] hello1
[0314baf6] |-->hello2
[0314baf6] |<--hello2 time=2ms
[0314baf6] hello1 time=25ms
```

**begin_exception_level2() - 실행 로그**
```
[37ccb357] hello
[37ccb357] |-->hello2
[37ccb357] |<X-hello2 time=2ms ex=java.lang.IllegalStateException
[37ccb357] hello time=25ms ex=java.lang.IllegalStateException
```

실행 로그를 보면 `트랜잭션ID`를 유지하고 `level`을 통해 메서드 호출의 깊이를 표현하는 것을 확인할 수 있다. 

</details>

<details> <summary> 7. 로그 추적기 V2 - 적용 </summary>

## 7. 로그 추적기 V2 - 적용

로그 추적기를 애플리케이션에 적용하자

**v1 -> v2 복사**

로그 추적기 V2를 적용하기 전에 먼저 기존 코드를 복사하자 
- `hello.advanced.app.v2`패키지 생성
- 복사
  - `v1.OrderControllerV1` -> `v2.OrderControllerV2`
  - `v1.OrderServiceV1` -> `v2.OrderServiceV2`
  - `v1.OrderRepositoryV1` -> `v2.OrderRepositoryV2`
- 코드 내부 의존관계 클래스를 V2로 변경
  - `OrderControllerV2`: `OrderServiceV1` -> `OrderServiceV2`
  - `OrderServiceV2`: `OrderRepositoryV1` -> `OrderRepositoryV2`
- `OrderControllerV2`: 매핑 정보 변경
  - `@GetMapping("/v2/request")
- `app.v2`에서는 `HelloTraceV1` -> `HelloTraceV2`를 사용하도록 변경
  - `OrderControllerV2`
  - `OrderServiceV2`
  - `OrderRepositoryV2`
 

### V2 적용하기

메서드 호출의 깊이를 표현하고, HTTP 요청도 구분해보자

이렇게 하려면 처음 로그를 남기는 `OrderController.request()`에서 로그를 남길 때 어떤 깊이와 어떤 트랜잭션 ID를 사용했는지 다음 차례인 `OrderService.orderItem()`에서 로그를 남기는 시점에 알아야 한다.

결국 현재 로그의 상태 정보인 `트랜잭션ID`와 `level`이 다음으로 전달되어야 한다.
이 정보는 `TraceStatus.traceId`에 담겨있다. 따라서 `traceId`를 컨트롤러에서 서비스를 호출할 때 넘겨주면 된다.

![image](https://user-images.githubusercontent.com/28394879/140507295-bcc66f99-fce9-42a5-9e08-d372f5a40ade.png)

`traceId`를 넘기도록 V2 전체 코드를 수정하자

**OrderControllerV2**
```java
package hello.advanced.app.v2;
import hello.advanced.trace.TraceStatus;
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
 throw e;
 }
 }
}
```
- `TraceStatus status = trace.begin()`에서 반환 받은 `TraceStatus`에는 `트랜잭션ID`와 `level`정보가 있는 `TraceId`가 있다.
- `orderService.orderItem()`을 호출할 때 `TraceId`를 파라미터로 전달한다.
- `TraceId`를 파라미터로 전달하기 위해 `OrderServiceV2.orderItem()`의 파라미터에 `TraceId`를 추가해야 한다.


**OrderServiceV2**
```java
package hello.advanced.app.v2;
import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class OrderServiceV2 {
 private final OrderRepositoryV2 orderRepository;
 private final HelloTraceV2 trace;
 public void orderItem(TraceId traceId, String itemId) {
 TraceStatus status = null;
 try {
 status = trace.beginSync(traceId, "OrderService.orderItem()");
 orderRepository.save(status.getTraceId(), itemId);
 trace.end(status);
 } catch (Exception e) {
 trace.exception(status, e);
 throw e;
 }
 }
}
```
- `orderItem()`은 파라미터로 전달 받은 `traceId`를 사용해서 `trace.beginSync()`를 실행한다.
- `beginSync()`는 내부에서 다음 `traceId`를 생성하면서 트랜잭션ID는 유지하고 `level`은 하나 증가시킨다.
- `beginSync()`가 반환한 새로운 `TraceStatus`를 `orderRepository.save()`를 호출하면서 파라미터로 전달한다.
- `TraceId`를 파라미터로 전달하기 위해 `orderRepository.save()`의 파라미터에 `TraceId`를 추가해야 한다.

**OrderRepositoryV2**
```java
package hello.advanced.app.v2;
import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {
 private final HelloTraceV2 trace;
 public void save(TraceId traceId, String itemId) {
 TraceStatus status = null;
 try {
 status = trace.beginSync(traceId, "OrderRepository.save()");
 //저장 로직
 if (itemId.equals("ex")) {
 throw new IllegalStateException("예외 발생!");
 }
 sleep(1000);
 trace.end(status);
 } catch (Exception e) {
 trace.exception(status, e);
 throw e;
 }
 }
 private void sleep(int millis) {
 try {
 Thread.sleep(millis);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
}
```

- `save()`는 파라미터로 전달 받은 `traceId`를 사용해서 `trace.beginSync()`를 실행한다.
- `beginSync()`는 내부에서 다음 `traceId`를 생성하면서 트랜잭션ID는 유지하고 `level`은 하나 증가시킨다.
- `beginSync()`는 이렇게 갱신된 `traceId`로 새로운 `TraceStatus`를 반환한다.
- `trace.end(status)`를 호출하면서 반환된 `TraceStatus`를 전달한다.

**정상 실행**
- http://localhost:8080/v2/request?itemId=hello

**정상 실행 로그**
```
[c80f5dbb] OrderController.request()
[c80f5dbb] |-->OrderService.orderItem()
[c80f5dbb] | |-->OrderRepository.save()
[c80f5dbb] | |<--OrderRepository.save() time=1005ms
[c80f5dbb] |<--OrderService.orderItem() time=1014ms
[c80f5dbb] OrderController.request() time=1017ms
```

**예외 실행**
- http://localhost:8080/v2/request?itemId=ex

**예외 실행 로그**
```
[ca867d59] OrderController.request()
[ca867d59] |-->OrderService.orderItem()
[ca867d59] | |-->OrderRepository.save()
[ca867d59] | |<X-OrderRepository.save() time=0ms
ex=java.lang.IllegalStateException: 예외 발생!
[ca867d59] |<X-OrderService.orderItem() time=7ms 
ex=java.lang.IllegalStateException: 예외 발생!
[ca867d59] OrderController.request() time=7ms
ex=java.lang.IllegalStateException: 예외 발생!
```

실행 로그를 보면 같은 HTTP 요청에 대해서 `트랜잭션ID`가 유지되고, `level`도 잘 표현되는 것을 확인할 수 있다.


</details>

<details> <summary> 8. 정리 </summary>

## 8. 정리 

**요구사항**

- ~~모든 PUBLIC 메서드의 호출과 응답 정보를 로그로 출력~~
- ~~애플리케이션의 흐름을 변경하면 안됨~~
  - ~~로그를 남긴다고 해서 비즈니스 로직의 동작에 영향을 주면 안됨~~
- ~~메서드 호출에 걸린 시간~~
- ~~정상 흐름과 예외 흐름 구분~~
  - ~~예외 발생시 예외 정보가 남아야 함~~
- ~~메서드 호출의 깊이 표현~~
- ~~HTTP 요청을 구분~~
  - ~~HTTP 요청 단위로 특정 ID를 남겨서 어떤 HTTP 요청에서 시작된 것인지 명확하게 구분이 가능해야 함~~
  - ~~트랜잭션 ID (DB 트랜잭션 말고..)~~

**남은 문제**
- HTTP 요청을 구분하고 깊이를 표현하기 위해서 `TraceId` 동기화가 필요하다.
- `TraceId`의 동기화를 위해서 관련 메서드의 모든 파라미터를 수정해야 한다.
  - 만약 인터페이스가 있다면 인터페이스까지 모두 고쳐야 하는 상황이다.
- 로그를 처음 시작할 때는 `begin()`을 호출하고, 호출이 아닐때는 `beginSync()`를 호출해야 한다.
  - 만약에 컨트롤러를 통해서 서비스를 호출하는 것이 아니라, 다른 곳에서 서비스를 처음으로 호출하는 상황이라면 파라미터로 넘길 `TraceId`가 없다.

HTTP 요청을 구분하고 깊이를 표현하기 위해서 `TraceId`를 파라미터로 넘기는 것 말고 다른 대안은 없을까?


</details>

# [2. 쓰레드 로컬 - ThreadLocal](./2.threadLocal)

<details> <summary> 1. 필드 동기화 - 개발 </summary>

## 1. 필드 동기화 - 개발

앞서 로그 추적기를 만들면서 다음 로그를 출력할 때 `트랜잭션ID`와 `level`을 동기화 하는 문제가 있었다.

이 문제를 해결하기 위해 `TraceId`를 파라미터로 넘기도록 구현했다.

이렇게 해서 동기화는 성공했지만, 로그를 출력하는 모든 메서드에 `TraceId` 파라미터를 추가해야 하는 문제가 발생했다.

`TraceId`를 파라미터로 넘기지 않고 이 문제를 해결할 수 있는 방법은 없을까?

이런 문제를 해결할 목적으로 새로운 로그 추적기를 만들어보자.
이제 프로토타입 버전이 아닌 정식 버전으로 제대로 개발해보자.

향후 다양한 구현체로 변경할 수 있도록 `LogTrace` 인터페이스를 먼저 만들고, 구현 해보자.

**LogTrace 인터페이스**
```java
package hello.advanced.trace.logtrace;
import hello.advanced.trace.TraceStatus;
public interface LogTrace {
 TraceStatus begin(String message);
 void end(TraceStatus status);
 void exception(TraceStatus status, Exception e);
}
```

**FieldLogTrace**
```java
package hello.advanced.trace.logtrace;
import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class FieldLogTrace implements LogTrace {
 private static final String START_PREFIX = "-->";
 private static final String COMPLETE_PREFIX = "<--";
 private static final String EX_PREFIX = "<X-";
 private TraceId traceIdHolder; //traceId 동기화, 동시성 이슈 발생
 @Override
 public TraceStatus begin(String message) {
 syncTraceId();
 TraceId traceId = traceIdHolder;
 Long startTimeMs = System.currentTimeMillis();
 log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX,
traceId.getLevel()), message);
 return new TraceStatus(traceId, startTimeMs, message);
 }
 @Override
 public void end(TraceStatus status) {
 complete(status, null);
 }
 @Override
 public void exception(TraceStatus status, Exception e) {
 complete(status, e);
 }
 private void complete(TraceStatus status, Exception e) {
 Long stopTimeMs = System.currentTimeMillis();
 long resultTimeMs = stopTimeMs - status.getStartTimeMs();
 TraceId traceId = status.getTraceId();
 if (e == null) {
 log.info("[{}] {}{} time={}ms", traceId.getId(),
addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(),
resultTimeMs);
 } else {
 log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,
e.toString());
 }
 releaseTraceId();
 }
 private void syncTraceId() {
 if (traceIdHolder == null) {
 traceIdHolder = new TraceId();
 } else {
 traceIdHolder = traceIdHolder.createNextId();
 }
 }
 private void releaseTraceId() {
 if (traceIdHolder.isFirstLevel()) {
 traceIdHolder = null; //destroy
 } else {
 traceIdHolder = traceIdHolder.createPreviousId();
 }
 }
 private static String addSpace(String prefix, int level) {
 StringBuilder sb = new StringBuilder();
 for (int i = 0; i < level; i++) {
 sb.append( (i == level - 1) ? "|" + prefix : "| ");
 }
 return sb.toString();
 }
}
```
`FieldLogTrace`는 기존에 만들었던 `HelloTraceV2`와 거의 같은 기능을 한다.

`TraceId`를 동기화하는 부분만 파라미터를 사용하는 것에서 `TraceId traceIdHolder`필드를 사용하도록 변경되었다.

이제 직전 로그의 `TraceId`는 파라미터로 전달되는 것이 아니라 `FieldLogTrace`의 필드인 `traceIdHolder`에 저장된다.

여기서 중요한 부분은 로그를 싲가할 때 호출하는 `syncTraceId()`와 로그를 종료할 때 호출하는 `releaseTraceId()`이다.

- `syncTraceId()`
  - `TraceId`를 새로 만들거나 앞선 로그의 `TraceId`를 참고해서 동기화하고, `level`도 증가한다.
  - 최초 호출이면 `TraceId`를 새로 만든다.
  - 직전 로그가 있으면 해당 로그의 `TraceId`를 참고해서 동기화하고, `level`도 하나 증가한다.
  - 결과를 `traceIdHolder`에 보관한다.
- `releaseTraceId()`
  - 메서드를 추가로 호출할 때는 `level`이 하나 증가해야 하지만, 메서드 호출이 끝나면 `level`이 하나 감소해야 한다.
  - `releaseTraceId()`는 `level`을 하나 감소한다.
  - 만약 최초 호출(`level==0`)이면 내부에서 관리하는 `traceId`를 제거한다

```
[c80f5dbb] OrderController.request() //syncTraceId(): 최초 호출 level=0
[c80f5dbb] |-->OrderService.orderItem() //syncTraceId(): 직전 로그 있음 level=1
증가
[c80f5dbb] | |-->OrderRepository.save() //syncTraceId(): 직전 로그 있음 level=2
증가
[c80f5dbb] | |<--OrderRepository.save() time=1005ms //releaseTraceId(): 
level=2->1 감소
[c80f5dbb] |<--OrderService.orderItem() time=1014ms //releaseTraceId():
level=1->0 감소
[c80f5dbb] OrderController.request() time=1017ms //releaseTraceId():
level==0, traceId 제거
```

### 테스트 코드

**FieldLogTraceTest**
```java
package hello.advanced.trace.logtrace;
import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;
class FieldLogTraceTest {
 FieldLogTrace trace = new FieldLogTrace();
 @Test
 void begin_end_level2() {
 TraceStatus status1 = trace.begin("hello1");
 TraceStatus status2 = trace.begin("hello2");
 trace.end(status2);
 trace.end(status1);
 }
 @Test
 void begin_exception_level2() {
 TraceStatus status1 = trace.begin("hello");
 TraceStatus status2 = trace.begin("hello2");
 trace.exception(status2, new IllegalStateException());
 trace.exception(status1, new IllegalStateException());
 }
}
```

**begin_end_level2() - 실행 결과**
```
[ed72b67d] hello1
[ed72b67d] |-->hello2
[ed72b67d] |<--hello2 time=2ms
[ed72b67d] hello1 time=6ms
```

**begin_exception_level2() - 실행 결과**
```
[59770788] hello
[59770788] |-->hello2
[59770788] |<X-hello2 time=3ms ex=java.lang.IllegalStateException
[59770788] hello time=8ms ex=java.lang.IllegalStateException
```

실행 결과를 보면 `트랜잭션ID`도 동일하게 나오고, `level`을 통한 깊이도 잘 표현된다.
`FieldLogTrace.traceIdHolder` 필드를 사용해서 `TraceId`가 잘 동기화 되는 것을 확인할 수 있다.
이제 불필요하게 `TraceId`를 파라미터로 전달하지 않아도 되고, 애플리케이션의 메서드 파라미터도 변경하지 않아도 된다.


</details>


<details> <summary> 2. 필드 동기화 - 적용 </summary>

## 2. 필드 동기화 - 적용

지금까지 만든 `FieldLogTrace`를 애플리케이션에 적용해보자

### LogTrace 스프링 빈 등록
`FieldLogTrace`를 수동으로 스프링 빈으로 등록하자. 수동으로 등록하면 향후 구현체를 편리하게 변경할 수 있다는 장점이 있다. 

**LogTraceConfig**
```java
package hello.advanced;
import hello.advanced.trace.logtrace.FieldLogTrace;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class LogTraceConfig {
 @Bean
 public LogTrace logTrace() {
 return new FieldLogTrace();
 }
}
```

**v2 -> v3 복사**

로그 추적기 V3를 적용하기 전에 먼저 기존 코드를 복사하자.
- `hello.advanced.app.v3` 패키지 생성
- 복사
  - `v2.OrderControllerV2` -> `v3.OrderControllerV3`
  - `v2.OrderServiceV2` -> `v3.OrderServiceV3`
  - `v2.OrderRepositoryV2` -> `v3.OrderRepositoryV3`
- 코드 내부 의존관계 클래스를 V3으로 변경
  - `OrderControllerV3`: `OrderServiceV2` -> `OrderServiceV3`
  - `OrderServiceV3`: `OrderRepositoryV2` -> `OrderRepositoryV3`
- `OrderControllerV3` 매핑 정보 변경
  - `@GetMapping("/v3/request")`
- `HelloTraceV2` -> `LogTrace` 인터페이스 사용 -> **주의!**
- `TraceId traceId` 파라미터를 모두 제거
- `beginSync()` -> `begin`으로 사용다로고 변경 

전체 코드는 다음과 같다.

**OrderControllerV3**
```java
package hello.advanced.app.v3;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {
 private final OrderServiceV3 orderService;
 private final LogTrace trace;
 @GetMapping("/v3/request")
 public String request(String itemId) {
 TraceStatus status = null;
 try {
 status = trace.begin("OrderController.request()");
 orderService.orderItem(itemId);
 trace.end(status);
 return "ok";
 } catch (Exception e) {
 trace.exception(status, e);
 throw e; //예외를 꼭 다시 던져주어야 한다.
 }
 }
}
```

**OrderServiceV3**
```java
package hello.advanced.app.v3;
import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class OrderServiceV3 {
 private final OrderRepositoryV3 orderRepository;
 private final LogTrace trace;
 public void orderItem(String itemId) {
 TraceStatus status = null;
 try {
 status = trace.begin("OrderService.orderItem()");
 orderRepository.save(itemId);
 trace.end(status);
 } catch (Exception e) {
 trace.exception(status, e);
 throw e;
 }
 }
}
```

**OrderRepositoryV3**
```java
package hello.advanced.app.v3;
import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV3 {
 private final LogTrace trace;
 public void save(String itemId) {
 TraceStatus status = null;
 try {
 status = trace.begin("OrderRepository.save()");
 //저장 로직
 if (itemId.equals("ex")) {
 throw new IllegalStateException("예외 발생!");
 }
 sleep(1000);
 trace.end(status);
 } catch (Exception e) {
 trace.exception(status, e);
 throw e;
 }
 }
 private void sleep(int millis) {
 try {
 Thread.sleep(millis);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
}
```

**정상 실행**
- http://localhost:8080/v3/request?itemId=hello

**정상 실행 로그**
```
[f8477cfc] OrderController.request()
[f8477cfc] |-->OrderService.orderItem()
[f8477cfc] | |-->OrderRepository.save()
[f8477cfc] | |<--OrderRepository.save() time=1004ms
[f8477cfc] |<--OrderService.orderItem() time=1006ms
[f8477cfc] OrderController.request() time=1007ms
```

**예외 실행**
- http://localhost:8080/v3/request?itemId=ex

**예외 실행 로그**
```
[c426fcfc] OrderController.request()
[c426fcfc] |-->OrderService.orderItem()
[c426fcfc] | |-->OrderRepository.save()
[c426fcfc] | |<X-OrderRepository.save() time=0ms
ex=java.lang.IllegalStateException: 예외 발생!
[c426fcfc] |<X-OrderService.orderItem() time=7ms
ex=java.lang.IllegalStateException: 예외 발생!
[c426fcfc] OrderController.request() time=7ms
ex=java.lang.IllegalStateException: 예외 발생!
```

`traceIdHolder` 필드를 사용한 덕분에 파라미터 추가 없는 깔끔한 로그 추적기를 완성했다. 이제 실제 서비스에 배포한다고 가정해보자. 


</details>


<details> <summary> 3. 필드 동기화 - 동시성 문제 </summary>

## 3. 필드 동기화 - 동시성 문제

잘 만든 로그 추적기를 실제 서비스에 배포 했다고 가정해보자.

테스트 할 때는 문제가 없는 것처럼 보인다. 

하지만 `FieldLogTrace`는 심각한 동시성 문제를 가지고 있다.

동시성 문제를 확인하려면 다음과 같이 동시에 여러번 호출해보면 된다.

**동시성 문제 확인**  
다음 로직을 1초안에 2번 실행해보자.
- http://localhost:8080/v3/request?itemId=hello
- http://localhost:8080/v3/request?itemId=hello

**기대하는 결과**
```
[nio-8080-exec-3] [52808e46] OrderController.request()
[nio-8080-exec-3] [52808e46] |-->OrderService.orderItem()
[nio-8080-exec-3] [52808e46] | |-->OrderRepository.save()
[nio-8080-exec-4] [4568423c] OrderController.request()
[nio-8080-exec-4] [4568423c] |-->OrderService.orderItem()
[nio-8080-exec-4] [4568423c] | |-->OrderRepository.save()
[nio-8080-exec-3] [52808e46] | |<--OrderRepository.save() time=1001ms
[nio-8080-exec-3] [52808e46] |<--OrderService.orderItem() time=1001ms
[nio-8080-exec-3] [52808e46] OrderController.request() time=1003ms
[nio-8080-exec-4] [4568423c] | |<--OrderRepository.save() time=1000ms
[nio-8080-exec-4] [4568423c] |<--OrderService.orderItem() time=1001ms
[nio-8080-exec-4] [4568423c] OrderController.request() time=1001ms
```

동시에 여러 사용자가 요청하면 여러 쓰레드가 동시에 애플리케이션 로직을 호출하게 된다.  
따라서 로그는 이렇게 섞어서 출력된다.

**기대하는 결과 - 로그 분리해서 확인하기**
```
[52808e46]
[nio-8080-exec-3] [52808e46] OrderController.request()
[nio-8080-exec-3] [52808e46] |-->OrderService.orderItem()
[nio-8080-exec-3] [52808e46] | |-->OrderRepository.save()
[nio-8080-exec-3] [52808e46] | |<--OrderRepository.save() time=1001ms
[nio-8080-exec-3] [52808e46] |<--OrderService.orderItem() time=1001ms
[nio-8080-exec-3] [52808e46] OrderController.request() time=1003ms
[4568423c]
[nio-8080-exec-4] [4568423c] OrderController.request()
[nio-8080-exec-4] [4568423c] |-->OrderService.orderItem()
[nio-8080-exec-4] [4568423c] | |-->OrderRepository.save()
[nio-8080-exec-4] [4568423c] | |<--OrderRepository.save() time=1000ms
[nio-8080-exec-4] [4568423c] |<--OrderService.orderItem() time=1001ms
[nio-8080-exec-4] [4568423c] OrderController.request() time=1001ms
```

로그가 섞어서 출력되더라도 특정 트랜잭션ID로 구분해서 직접 분류해보면 이렇게 깔끔하게 분리된 것을 확인할 수 있다.  
그런데 실제 결과를 기대한 것과 다르게 다음과 같이 출력 된다.  

**실제 결과**
```
[nio-8080-exec-3] [aaaaaaaa] OrderController.request()
[nio-8080-exec-3] [aaaaaaaa] |-->OrderService.orderItem()
[nio-8080-exec-3] [aaaaaaaa] | |-->OrderRepository.save()
[nio-8080-exec-4] [aaaaaaaa] | | |-->OrderController.request()
[nio-8080-exec-4] [aaaaaaaa] | | | |-->OrderService.orderItem()
[nio-8080-exec-4] [aaaaaaaa] | | | | |-->OrderRepository.save()
[nio-8080-exec-3] [aaaaaaaa] | |<--OrderRepository.save() time=1005ms
[nio-8080-exec-3] [aaaaaaaa] |<--OrderService.orderItem() time=1005ms
[nio-8080-exec-3] [aaaaaaaa] OrderController.request() time=1005ms
[nio-8080-exec-4] [aaaaaaaa] | | | | |<--OrderRepository.save()
time=1005ms
[nio-8080-exec-4] [aaaaaaaa] | | | |<--OrderService.orderItem()
time=1005ms
[nio-8080-exec-4] [aaaaaaaa] | | |<--OrderController.request() time=1005ms
```

**실제 결과 - 로그 분리해서 확인하기**
```
[nio-8080-exec-3]
[nio-8080-exec-3] [aaaaaaaa] OrderController.request()
[nio-8080-exec-3] [aaaaaaaa] |-->OrderService.orderItem()
[nio-8080-exec-3] [aaaaaaaa] | |-->OrderRepository.save()
[nio-8080-exec-3] [aaaaaaaa] | |<--OrderRepository.save() time=1005ms
[nio-8080-exec-3] [aaaaaaaa] |<--OrderService.orderItem() time=1005ms
[nio-8080-exec-3] [aaaaaaaa] OrderController.request() time=1005ms
[nio-8080-exec-4]
[nio-8080-exec-4] [aaaaaaaa] | | |-->OrderController.request()
[nio-8080-exec-4] [aaaaaaaa] | | | |-->OrderService.orderItem()
[nio-8080-exec-4] [aaaaaaaa] | | | | |-->OrderRepository.save()
[nio-8080-exec-4] [aaaaaaaa] | | | | |<--OrderRepository.save()
time=1005ms
[nio-8080-exec-4] [aaaaaaaa] | | | |<--OrderService.orderItem()
time=1005ms
[nio-8080-exec-4] [aaaaaaaa] | | |<--OrderController.request() time=1005ms
```

기대한 것과 전혀 다른 문제가 발생한다. `트랜잭션 ID`도 동일하고, `level`도 뭔가 많이 꼬인 것 같다.   

**동시성 문제**  
이 문제가 동시성 문제다.
`FieldLogTrace`는 싱글톤으로 등록된 스프링 빈이다. 이 객체의 인스턴스가 애플리케이션에 딱 1개 존재한다는 뜻이다.  
이렇게 하나만 있는 인스턴스의 `FieldLogTrace.traceIdHolder` 필드를 여러 쓰레드가 동시에 접근하기 때문에 문제가 발생한다.  
실무에서 한번 나타나면 개발자를 가장 괴롭히는 문제도 바로 이러한 동시성 문제이다.


</details>

<details> <summary> 4. 동시성 문제 - 예제 코드 </summary>

## 4. 동시성 문제 - 예제 코드

동시성 문제가 어떻게 발생하는지 단순화해서 알아보자. 

테스트에서도 lombok을 사용하기 위해 다음 코드를 추가하자  
`build.gradle`
```
dependencies {
 ...
 //테스트에서 lombok 사용
 testCompileOnly 'org.projectlombok:lombok'
 testAnnotationProcessor 'org.projectlombok:lombok'
}
```
이렇게 해야 테스트 코드에서 `@Slfj4` 같은 애노테이션이 작동한다.

**FieldService**  
주의: 테스트코드(src/test)에 위치한다. 
```java
package hello.advanced.trace.threadlocal.code;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class FieldService {
 private String nameStore;
 public String logic(String name) {
 log.info("저장 name={} -> nameStore={}", name, nameStore);
 nameStore = name;
 sleep(1000);
 log.info("조회 nameStore={}",nameStore);
 return nameStore;
 }
 private void sleep(int millis) {
 try {
 Thread.sleep(millis);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
}
```
매우 단순한 로직이다. 파라미터로 넘어온 `name`을 필드인 `nameStore`에 저장한다. 그리고 1초간 쉰 다음 필드에 저장된 `nameStore`를 반환한다.

**FieldServiceTest**
```java
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
 threadA.start(); //A실행
 sleep(2000); //동시성 문제 발생X
// sleep(100); //동시성 문제 발생O
 threadB.start(); //B실행
 sleep(3000); //메인 쓰레드 종료 대기
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
```

**순서대로 실행**  
`sleep(2000)`을 설정해서 `thread-A`의 실행이 끝나고 나서 `thread-B`가 실행되도록 해보자.  
참고로 `FieldService.logic()`메서드는 내부에 `sleep(1000)`으로 1초의 지연이 있다.  
따라서 1초 이후에 호출하면 순서대로 실행할 수 있다. 여기서는 넉넉하게 2초(2000ms)를 설정했다.
```java
sleep(2000); //동시성 문제 발생X
//sleep(100); //동시성 문제 발생O
```

**실행 결과**
```java
[Test worker] main start
[Thread-A] 저장 name=userA -> nameStore=null
[Thread-A] 조회 nameStore=userA
[Thread-B] 저장 name=userB -> nameStore=userA
[Thread-B] 조회 nameStore=userB
[Test worker] main exit
```

실행 결과를 보면 문제가 없다. 


**동시성 문제 발생 코드**  
이번에는 `sleep(100)`을 설정해서 `thread-A`의 작업이 끝나기 전에 `thread-B`가 실행되도록 해보자.  
참고로 `FieldService.logic()` 메서드는 내부에 `sleep(1000)`으로 1초의 지연이 있다.  
따라서 1초 이후에 호출하면 순서대로 실행할 수 있다.  
다음에 설정할 100(ms)는 0.1초 이기 때문에 `thread-A`의 작업이 끝나기 전에 `thread-B`가 실행된다.
```java
//sleep(2000); //동시성 문제 발생X
sleep(100); //동시성 문제 발생O
```

**실행 결과**
```
[Test worker] main start
[Thread-A] 저장 name=userA -> nameStore=null
[Thread-B] 저장 name=userB -> nameStore=userA
[Thread-A] 조회 nameStore=userB
[Thread-B] 조회 nameStore=userB
[Test worker] main exit
```

실행 결과를 보면 저장하는 부분은 문제가 없는데, 조회하는 부분에서 발생한다.
- `thread-A`의 호출이 끝나면서 `nameStore`의 결과를 반환하는데, 이때 `nameStore`는 앞의 2번에서  
`userB`의 값으로 대체되었다. 따라서 기대했던 `userA`의 값이 아니라 `userB`의 값이 반환된다.
- `thread-B`의 호출이 끝나면서 `nameStore`의 결과인 `userB`를 반환받는다. 

**동시성 문제**  
결과적으로 `thread-A`입장에서는 저장한 데이터와 조회한 데이터가 다른 문제가 발생한다.  
이처럼 여러 쓰레드가 동시에 같은 인스턴스의 필드 값을 변경하면서 발생하는 문제를 동시성 문제라 한다.  
이런 동시성 문제는 여러 쓰레드가 같은 인스턴스의 필드에 접근해야 하기 때문에 트래픽이 적은 상황에서는 확률상 잘 나타나지 않고, 트래픽이 점점 많아질 수록 자주 발생한다.  
특히 스프링 빈 처럼 싱글톤 객체의 필드를 변경하며 사용할 때 이러한 동시성 문제를 조심해야 한다. 

> 참고  
> 이런 동시성 문제는 지역 변수에서는 발생하지 않는다. 지역 변수는 쓰레드마다 각각 다른 메모리 영역이 할당된다.  
> 동시성 문제가 발생하는 곳은 같은 인스턴스의 필드(주로 싱글톤에서 자주 발생), 또는 static 같은 공용 필드에 접근할 때 발생한다.  
> 동시성 문제는 값을 읽기만 하면 발생하지 않는다. 어디선가 값을 변경하기 때문에 발생한다. 

그렇다면 지금처럼 싱글톤 객체의 필드를 사용하면서 동시성 문제를 해결하려면 어떻게 해야할까? 다시 파라미터를 전달하는 방식으로 돌아가야 할까? 이럴 때 사용하는 것이 바로 쓰레드 로컬이다. 

</details>


<details> <summary> 5. ThreadLocal - 소개 </summary>

## 5. ThreadLocal - 소개

쓰레드 로컬은 해당 쓰레드만 접근할 수 있는 특별한 저장소를 말한다. 쉽게 이야기해서 물건 보관 창구를 떠올리면 된다.  
여러 사람이 같은 물건 보관 창구르 사용하더라도 창구 직원은 사용자를 인식해서 사용자별로 확실하게 물건을 구분해준다.  
사용자A, 사용자B 모두 창구 직원을 통해서 물건을 보관하고, 꺼내지만 창구 지원이 사용자에 따라 보관한 물건을 구분해주는 것이다. 

**일반적인 변수 필드**  
여러 쓰레드가 같은 인스턴스의 필드에 접근하면 처음 쓰레드가 보관한 데이터가 사라질 수 있다. (이전까지 설명했던 동시성 문제)

**쓰레드 로컬**  
쓰레드 로컬을 사용하면 각 쓰레드마다 별도의 내부 저장소를 제공한다. 따라서 같은 인스턴스의 쓰레드 로컬 필드에 접근해도 문제 없다. 

자바는 언어차원에서 쓰레드 로컬을 지원하기 위한 `java.lang.ThreadLocal` 클래스를 제공한다. 

</details>


<details> <summary> 6. ThreadLocal - 예제 코드 </summary>

## 6. ThreadLocal - 예제 코드

예제 코드를 통해서 `ThreadLocal`을 학습해보자.

**ThreadLocalService**  
주의: 테스트 코드(src/test)에 위치한다.
```java
package hello.advanced.trace.threadlocal.code;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ThreadLocalService {
 private ThreadLocal<String> nameStore = new ThreadLocal<>();
 public String logic(String name) {
 log.info("저장 name={} -> nameStore={}", name, nameStore.get());
 nameStore.set(name);
 sleep(1000);
 log.info("조회 nameStore={}",nameStore.get());
 return nameStore.get();
 }
 private void sleep(int millis) {
 try {
 Thread.sleep(millis);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
}
```
기존에 있던 `FieldService`와 거의 같은 코드인데, `nameStore`필드가 일반 `String`타입에서 `ThreadLocal`을 사용하도록 변경되었다. 

**ThreadLocal 사용법**
- 값 저장: `ThreadLocal.set(xxx)`
- 값 조회: `ThreadLocal.get()`
- 값 제거: `ThreadLocal.remove()`

> 주의  
> 해당 쓰레드가 쓰레드 로컬을 모두 사용하고 나면 `ThreadLocal.remove()`를 호출해서 쓰레드 로컬에  
> 저장된 값을 제거해주어야 한다. 제거하는 구체적인 예제는 조금 뒤에 설명한다.

**ThreadLocalServiceTest**
```java
package hello.advanced.trace.threadlocal;
import hello.advanced.trace.threadlocal.code.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
@Slf4j
public class ThreadLocalServiceTest {
 private ThreadLocalService service = new ThreadLocalService();
 @Test
 void threadLocal() {
 log.info("main start");
 Runnable userA = () -> {
 service.logic("userA");
 };
 Runnable userB = () -> {
 service.logic("userB");
 };
 Thread threadA = new Thread(userA);
 threadA.setName("thread-A");
 Thread threadB = new Thread(userB);
 threadB.setName("thread-B");
 threadA.start();
 sleep(100);
 threadB.start();
 sleep(2000);
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
```

**실행 결과**
```
[Test worker] main start
[Thread-A] 저장 name=userA -> nameStore=null
[Thread-B] 저장 name=userB -> nameStore=null
[Thread-A] 조회 nameStore=userA
[Thread-B] 조회 nameStore=userB
[Test worker] main exit
```

쓰레드 로컬 덕분에 쓰레드 마다 각 별도의 데이터 저장소를 가지게 되었다. 결과적으로 동시성 문제도 해결되었다.



</details>


<details> <summary> 7. 쓰레드 로컬 동기화 - 개발 </summary>

## 7. 쓰레드 로컬 동기화 - 개발

`FieldLogTrace`에서 발생했던 동시성 문제를 `ThreadLocal`로 해결해보자  
`TraceId traceIdHolder` 필드를 쓰레드 로컬을 사용하도록 `ThreadLocal<TraceId> traceIdHolder`로 변경하면 된다.

필드 대신에 쓰레드 로컬을 사용해서 데이터를 동기화하는 `ThreadLocalLogTrace`를 새로 만들자. 

**ThreadLocalLogTrace**
```java
package hello.advanced.trace.logtrace;
import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ThreadLocalLogTrace implements LogTrace {
 private static final String START_PREFIX = "-->";
 private static final String COMPLETE_PREFIX = "<--";
 private static final String EX_PREFIX = "<X-";
 private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();
 @Override
 public TraceStatus begin(String message) {
 syncTraceId();
 TraceId traceId = traceIdHolder.get();
 Long startTimeMs = System.currentTimeMillis();
 log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX,
traceId.getLevel()), message);
 return new TraceStatus(traceId, startTimeMs, message);
 }
 @Override
 public void end(TraceStatus status) {
 complete(status, null);
 }
 @Override
 public void exception(TraceStatus status, Exception e) {
 complete(status, e);
 }
 private void complete(TraceStatus status, Exception e) {
 Long stopTimeMs = System.currentTimeMillis();
 long resultTimeMs = stopTimeMs - status.getStartTimeMs();
 TraceId traceId = status.getTraceId();
 if (e == null) {
 log.info("[{}] {}{} time={}ms", traceId.getId(),
addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(),
resultTimeMs);
 } else {
 log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,
e.toString());
 }
 releaseTraceId();
 }
 private void syncTraceId() {
 TraceId traceId = traceIdHolder.get();
 if (traceId == null) {
 traceIdHolder.set(new TraceId());
 } else {
 traceIdHolder.set(traceId.createNextId());
 }
 }
 private void releaseTraceId() {
 TraceId traceId = traceIdHolder.get();
 if (traceId.isFirstLevel()) {
 traceIdHolder.remove();//destroy
 } else {
 traceIdHolder.set(traceId.createPreviousId());
 }
 }
 private static String addSpace(String prefix, int level) {
 StringBuilder sb = new StringBuilder();
 for (int i = 0; i < level; i++) {
 sb.append( (i == level - 1) ? "|" + prefix : "| ");
 }
 return sb.toString();
 }
}
```

`traceIdHolder`가 필드에서 `ThreadLocal`로 변경되었다. 따라서 값을 저장할 때는 `set(..)`을 사용하고, 값을 조회할 때는 `get()`을 사용한다.

**ThreadLocal.remove()**  
추가로 쓰레드 로컬을 모두 사용하고 나면 꼭 `ThreadLocal.remove()`를 호출해서 쓰레드 로컬에 저장된 값을 제거해주어야 한다.  
쉽게 이야기해서 다음의 마지막 로그를 출력하고 나면 쓰레드 로컬의 값을 제거해야 한다.
```java
[3f902f0b] hello1
[3f902f0b] |-->hello2
[3f902f0b] |<--hello2 time=2ms
[3f902f0b] hello1 time=6ms //end() -> releaseTraceId() -> level==0,
ThreadLocal.remove() 호출
```

여기서는 `releaseTraceId()`를 통해 `level`이 점점 낮아져서 2->1->0이 되면 로그를 처음 호출한 부분으로 돌아온 것이다.  
따라서 이 경우 연관된 로그 출력이 끝난 것이다. 이제 더이상 `TraceId` 값을 추적하지 않아도 된다.  
그래서 `traceId.isFirstLevel()``(level==0)`인 경우 `ThreadLocal.remove()`를 호출해서 쓰레드 로컬에 저장된 값을 제거해준다. 

코드에 문제가 없는지 간단한 테스트를 만들어서 확인해보자 

**ThreadLocalLogTraceTest**
```java
package hello.advanced.trace.logtrace;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
@Slf4j
class ThreadLocalLogTraceTest {
 ThreadLocalLogTrace trace = new ThreadLocalLogTrace();
 @Test
 void begin_end_level2() {
 TraceStatus status1 = trace.begin("hello1");
 TraceStatus status2 = trace.begin("hello2");
 trace.end(status2);
 trace.end(status1);
 }
 @Test
 void begin_exception_level2() {
 TraceStatus status1 = trace.begin("hello");
 TraceStatus status2 = trace.begin("hello2");
 trace.exception(status2, new IllegalStateException());
 trace.exception(status1, new IllegalStateException());
 }
}
```

**begin_end_level2() - 실행 결과**
```
[3f902f0b] hello1
[3f902f0b] |-->hello2
[3f902f0b] |<--hello2 time=2ms
[3f902f0b] hello1 time=6ms
```

**begin_exception_level2() - 실행 결과**
```
[3dd9e4f1] hello
[3dd9e4f1] |-->hello2
[3dd9e4f1] |<X-hello2 time=3ms ex=java.lang.IllegalStateException
[3dd9e4f1] hello time=8ms ex=java.lang.IllegalStateException
```

멀티쓰레드 상황에서 문제가 없는지는 애플리케이션에 `ThreadLocalLogTrace`를 적용해서 확인해보자. 


</details>


<details> <summary> 8. 쓰레드 로컬 동기화 - 적용 </summary>

## 8. 쓰레드 로컬 동기화 - 적용

**LogTraceConfig - 수정**
```java
package hello.advanced;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class LogTraceConfig {
 @Bean
 public LogTrace logTrace() {
 //return new FieldLogTrace();
 return new ThreadLocalLogTrace();
 }
}
```
동시성 문제가 있는 `FieldLogTrace` 대신에 문제를 해결한 `ThreadLocalLogTrace`를 스프링 빈으로 등록하자.

**정상 실행**
- http://localhost:8080/v3/request?itemId=hello

**정상 실행 로그**
```
[f8477cfc] OrderController.request()
[f8477cfc] |-->OrderService.orderItem()
[f8477cfc] | |-->OrderRepository.save()
[f8477cfc] | |<--OrderRepository.save() time=1004ms
[f8477cfc] |<--OrderService.orderItem() time=1006ms
[f8477cfc] OrderController.request() time=1007ms
```

**예외 실행**
- http://localhost:8080/v3/request?itemId=ex

**예외 실행 로그**
```
[c426fcfc] OrderController.request()
[c426fcfc] |-->OrderService.orderItem()
[c426fcfc] | |-->OrderRepository.save()
[c426fcfc] | |<X-OrderRepository.save() time=0ms
ex=java.lang.IllegalStateException: 예외 발생!
[c426fcfc] |<X-OrderService.orderItem() time=7ms
ex=java.lang.IllegalStateException: 예외 발생!
[c426fcfc] OrderController.request() time=7ms
ex=java.lang.IllegalStateException: 예외 발생!
```

### 동시 요청 
**동시성 문제 확인**  
다음 로직을 1초 안에 2번 실행해보자. 
- http://localhost:8080/v3/request?itemId=hello
- http://localhost:8080/v3/request?itemId=hello

**실행 결과**
```
[nio-8080-exec-3] [52808e46] OrderController.request()
[nio-8080-exec-3] [52808e46] |-->OrderService.orderItem()
[nio-8080-exec-3] [52808e46] | |-->OrderRepository.save()
[nio-8080-exec-4] [4568423c] OrderController.request()
[nio-8080-exec-4] [4568423c] |-->OrderService.orderItem()
[nio-8080-exec-4] [4568423c] | |-->OrderRepository.save()
[nio-8080-exec-3] [52808e46] | |<--OrderRepository.save() time=1001ms
[nio-8080-exec-3] [52808e46] |<--OrderService.orderItem() time=1001ms
[nio-8080-exec-3] [52808e46] OrderController.request() time=1003ms
[nio-8080-exec-4] [4568423c] | |<--OrderRepository.save() time=1000ms
[nio-8080-exec-4] [4568423c] |<--OrderService.orderItem() time=1001ms
[nio-8080-exec-4] [4568423c] OrderController.request() time=1001ms
```

**로그 분리해서 확인하기**
```
[nio-8080-exec-3]
[nio-8080-exec-3] [52808e46] OrderController.request()
[nio-8080-exec-3] [52808e46] |-->OrderService.orderItem()
[nio-8080-exec-3] [52808e46] | |-->OrderRepository.save()
[nio-8080-exec-3] [52808e46] | |<--OrderRepository.save() time=1001ms
[nio-8080-exec-3] [52808e46] |<--OrderService.orderItem() time=1001ms
[nio-8080-exec-3] [52808e46] OrderController.request() time=1003ms
[nio-8080-exec-4]
[nio-8080-exec-4] [4568423c] OrderController.request()
[nio-8080-exec-4] [4568423c] |-->OrderService.orderItem()
[nio-8080-exec-4] [4568423c] | |-->OrderRepository.save()
[nio-8080-exec-4] [4568423c] | |<--OrderRepository.save() time=1000ms
[nio-8080-exec-4] [4568423c] |<--OrderService.orderItem() time=1001ms
[nio-8080-exec-4] [4568423c] OrderController.request() time=1001ms
```

로그를 직접 분리해서 확인해보면 각각의 쓰레드 `nio-8080-exec-3`, `nio-8080-exec-4` 별로 로그가 정확하게 나누어진 것을 확인할 수 있다. 

</details>


<details> <summary> 9. 쓰레드 로컬 - 주의 사항 </summary>

## 9. 쓰레드 로컬 - 주의 사항

쓰레드 로컬의 값을 사용 후 제거하지 않고 그냥 두면 WAS(톰캣)처럼 쓰레드 풀을 사용하는 경우에 심각한 문제가 발생할 수 있다.  
다음 예시를 통해서 알아보자. 

**사용자 A 저장 요청**  
![image](https://user-images.githubusercontent.com/28394879/140542343-8c6aba7b-cba1-4263-8735-beb92cfd3409.png)
1. 사용자 A가 저장 HTTP를 요청했다.
2. WAS는 쓰레드 풀에서 쓰레드를 하나 조회한다.
3. 쓰레드 `thread-A`가 할당되었다.
4. `thread-A`는 `사용자A`의 데이터를 쓰레드 로컬에 저장한다.
5. 쓰레드 로컬의 `thread-A` 전용 보관소에 `사용자A` 데이터를 보관한다. 

**사용자 A 저장 요청 종료**
![image](https://user-images.githubusercontent.com/28394879/140542562-94a81298-ec27-4779-b22d-b3acfd1b8d89.png)
1. 사용자 A의 HTTP 응답이 끝난다.
2. WAS는 사용이 끝난 `thread-A`를 쓰레드 풀에 반환한다. 쓰레드를 생성하는 비용은 비싸기 때문에 쓰레드를 제거하지 않고, 보통 쓰레드 풀을 통해서 쓰레드를 재사용한다.
3. `thread-A`는 쓰레드풀에 아직 살아 있다. 따라서 쓰레드 로컬의 `thread-A` 전용 보관소에 `사용자A` 데이터도 함께 살아있게 된다.

**사용자 B 조회 요청**
![image](https://user-images.githubusercontent.com/28394879/140542808-f4f8bc71-570b-42e7-b931-c7ab4225727b.png)
1. 사용자B가 조회를 위한 새로운 HTTP 요청을 한다.
2. WAS는 쓰레드 풀에서 쓰레드를 하나 조회한다.
3. 쓰레드 thread-A 가 할당되었다. (물론 다른 쓰레드가 할당될 수 도 있다.)
4. 이번에는 조회하는 요청이다. thread-A 는 쓰레드 로컬에서 데이터를 조회한다.
5. 쓰레드 로컬은 thread-A 전용 보관소에 있는 사용자A 값을 반환한다.
6. 결과적으로 사용자A 값이 반환된다.
7. 사용자B는 사용자A의 정보를 조회하게 된다.

결과적으로 사용자B는 사용자A의 데이터를 확인하게 되는 심각한 문제가 발생하게 된다.  
이런 문제를 예방하려면 사용자A의 요청이 끝날 떄 쓰레드 로컬의 값을 `ThreadLocal.remove()`를 통해서 꼭 제거해야 한다.  
쓰레드 로컬을 사용할 때는 이 부분을 꼭 기억해야 한다. 


</details>


# [3. 템플릿 메서드 패턴과 콜백 패턴](./3.template-method-pattern-and-callback-pattern)

<details> <summary> 1. 템플릿 메서드 패턴 - 시작 </summary>

## 1. 템플릿 메서드 패턴 - 시작 

지금까지 로그 추적기를 열심히 잘 만들었다. 요구사항도 만족하고, 파라미터를 넘기는 불편함을 제거하기 위해 쓰레드 로컬도 도입했다.  
그런데 로그 추적기를 막상 프로젝트에 도입하려고 하니 개발자들의 반대의 목소리가 높다.  
로그 추적기 도입 전과 도입 후의 코드를 비교해보자 

**로그 추적기 도입 전 - V0 코드**
```java
//OrderControllerV0 코드
@GetMapping("/v0/request")
public String request(String itemId) {
 orderService.orderItem(itemId);
 return "ok";
}
//OrderServiceV0 코드
public void orderItem(String itemId) {
 orderRepository.save(itemId);
}
```

**로그 추적기 도입 후 - V3 코드**
```java
//OrderControllerV3 코드
@GetMapping("/v3/request")
public String request(String itemId) {
 TraceStatus status = null;
 try {
 status = trace.begin("OrderController.request()");
 orderService.orderItem(itemId); //핵심 기능
 trace.end(status);
 } catch (Exception e) {
 trace.exception(status, e);
 throw e;
 }
 return "ok";
}
//OrderServiceV3 코드
public void orderItem(String itemId) {
 TraceStatus status = null;
 try {
 status = trace.begin("OrderService.orderItem()");
 orderRepository.save(itemId); //핵심 기능
 trace.end(status);
 } catch (Exception e) {
 trace.exception(status, e);
 throw e;
 }
}
```

V0 시절 코드와 비교해서 V3 코드를 보자.  
V0는 해당 메서드가 실제 처리해야 하는 핵심 기능만 깔끔하게 남아있다. 반면에 V3에는 핵심 기능보다 로그를 출력해야 하는 부가 기능 코드가 훨씬 더 많고 복잡하다.  
앞으로 코드를 설명할 때 핵심 기능과 부가 기능으로 구분해서 정리하겠다.

**핵심 기능 vs 부가 기능**
- 핵심 기능
  - 해당 객체가 제공하는 고유의 기능 
  - 예) `orderService`의 핵심 기능은 주문 로직이다. 
  - 메서드 단위로 보면 `orderService.orderItem()`의 핵심 기능은 주문 데이터를 저장하기 위해 리포지토리를 호출하는 `orderRepository.save(itemId)`코드가 핵심 기능이다.
- 부가 기능
  - 핵심 기능을 보조하기 위해 제공되는 기능 
  - 예) 로그 추적 로직, 트랜잭션 기능 
  - 단독으로 사용되지 않고, 핵심 기능과 함께 사용된다. 
  - 예) 로그 추적기능은 어떤 핵심 기능이 호출되었는지 로그를 남기기 위해 사용한다. 그러니까 핵심 기능을 보조하기 위해 존재한다.

V0는 핵심 기능만 있지만, 로그 추적기를 추가한 V3 코드는 핵심 기능과 부가 기능이 함께 섞여있다.  
V3를 보면 로그 추적기의 도입으로 ㅎ개심 기능 코드보다 부가 기능을 처리하기 위한 코드가 더 많아졌다.  
소위 배보다 배꼽이 더 큰 상황이다. 만약 클래스가 수백 개라면 어떻게 하겠는가?  


이 문제를 좀 더 효율적으로 처리할 수 있는 방법이 있을까?  
V3 코드를 유심히 잘 살펴보면 다음과 같이 동일한 패턴이 있다.  
```java
TraceStatus status = null;
try {
 status = trace.begin("message");
 //핵심 기능 호출
 trace.end(status);
} catch (Exception e) {
 trace.exception(status, e);
 throw e;
}
```
`Controller`,`Service`,`Repository`의 코드를 잘 보면, 로그 추적기를 사용하는 구조는 모두 동일하다.  
중간에 핵심 기능을 사용하는 코드만 다를 뿐이다.  
부가 기능과 관련된 코드가 중복이니 중복을 별도의 메서드로 뽑아내면 될 것 같다.  
그런데, `try ~ catch`는 물론이고 ,핵심 기능 부분이 중간에 있어서 단순하게 메서드로 추출하는 것은 어렵다.  

**변하는 것과 변하지 않는 것을 분리**  
좋은 설계는 변하는 것과 변하지 않는 것을 분리하는 것이다.  
여기서 핵심 기능 부분은 변하고, 로그 추적기를 사용하는 부분은 변하지 않는 부분이다.  
이 둘을 분리해서 모듈화 해야 한다.  

템플릿 메서드 패턴(Template Method Pattern)은 이런 문제를 해결하는 디자인 패턴이다.



</details>

<details> <summary> 2. 템플릿 메서드 패턴 - 예제1 </summary>

## 2. 템플릿 메서드 패턴 - 예제1

템플릿 메서드 패턴을 쉽게 이해하기 위해 단순한 예제 코드를 만들어보자 

**TemplateMethodTest**
```java
package hello.advanced.trace.template;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
@Slf4j
public class TemplateMethodTest {
 @Test
 void templateMethodV0() {
 logic1();
 logic2();
 }
 private void logic1() {
 long startTime = System.currentTimeMillis();
 //비즈니스 로직 실행
 log.info("비즈니스 로직1 실행");
 //비즈니스 로직 종료
 long endTime = System.currentTimeMillis();
 long resultTime = endTime - startTime;
 log.info("resultTime={}", resultTime);
 }
 private void logic2() {
 long startTime = System.currentTimeMillis();
 //비즈니스 로직 실행
 log.info("비즈니스 로직2 실행");
 //비즈니스 로직 종료
 long endTime = System.currentTimeMillis();
 long resultTime = endTime - startTime;
 log.info("resultTime={}", resultTime);
 }
}
```
`logic1()`,`logic2()`를 호출하는 단순한 테스트 코드이다.

**실행 결과**
```
비즈니스 로직1 실행
resultTime=5
비즈니스 로직2 실행
resultTime=1
```

`logic1()`과 `logic2()`는 시간을 측정하는 부분과 비즈니스 로직을 실행하는 부분이 함께 존재한다.

- 변하는 부분: 비즈니스 로직
- 변하지 않는 부분: 시간 측정

이제 템플릿 메서드 패턴ㅇ르 사용해서 변하는 부분과 변하지 않는 부분을 분리해보자.




</details>

<details> <summary> 3. 템플릿 메서드 패턴 - 예제2 </summary>

## 3. 템플릿 메서드 패턴 - 예제2

**템플릿 메서드 패턴 구조 그림**  
![image](https://user-images.githubusercontent.com/28394879/140595800-4311a773-1bb6-4d29-bac9-f11582999dd6.png)


**AbstractTemplate**  
주의: 테스트 코드(src/test)에 위치한다.
```java
package hello.advanced.trace.template.code;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public abstract class AbstractTemplate {
 public void execute() {
 long startTime = System.currentTimeMillis();
 //비즈니스 로직 실행
 call(); //상속
 //비즈니스 로직 종료
 long endTime = System.currentTimeMillis();
 long resultTime = endTime - startTime;
 log.info("resultTime={}", resultTime);
 }
 protected abstract void call();
}
```

템플릿 메서드 패턴은 이름 그대로 템플릿을 사용하는 방식이다. 템플릿은 기준이 되는 거대한 틀이다.  
템플릿이라는 틀에 변하지 않는 부분을 몰아둔다. 그리고 일부 변하는 부분을 별도로 호출해서 해결한다.  

`AbstractTemplate`코드를 보자. 변하지 않는 부분인 시간 측정 로직을 몰아둔 것을 확인할 수 있다.  
이제 이것의 하나의 템플릿이 된다. 그리고 템플릿 안에서 변하는 부분은 `call()` 메서드를 호출해서 처리한다.  
템플릿 메서드 패턴은 부모 클래스에 변하지 않는 템플릿 코드를 둔다. 그리고 변하는 부분은 자식 클래스에 두고 상속과 오버라이딩을 사용해서 처리한다.

**SubClassLogic1**  
주의: 테스트 코드 (src/test)에 위치한다.
```java
package hello.advanced.trace.template.code;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class SubClassLogic1 extends AbstractTemplate {
 @Override
 protected void call() {
 log.info("비즈니스 로직1 실행");
 }
}
```
변하는 부분인 비즈니스 로직1을 처리하는 자식 클래스이다. 템플릿이 호출하는 대상인 `call()`메서드를 오버라이딩 한다. 

**SubClassLogic2**  
주의: 테스트 코드 (src/test)에 위치한다.
```java
package hello.advanced.trace.template.code;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class SubClassLogic2 extends AbstractTemplate {
 @Override
 protected void call() {
 log.info("비즈니스 로직2 실행");
 }
}
```
변하는 부분인 비즈니스 로직2를 처리하는 자식 클래스이다. 템플릿이 호출하는 대상인 `call()`메서드를 오버라이딩 한다.

**TemplateMethodTest - templateMethodV1() 추가**
```java
/**
 * 템플릿 메서드 패턴 적용
 */
@Test
void templateMethodV1() {
 AbstractTemplate template1 = new SubClassLogic1();
 template1.execute();
 AbstractTemplate template2 = new SubClassLogic2();
 template2.execute();
}
```
템플릿 메서드 패턴으로 구현한 코드를 실행해보자.

**실행 결과**  
```
비즈니스 로직1 실행
resultTime=0
비즈니스 로직2 실행
resultTime=1
```

**템플릿 메서드 패턴 인스턴스 호출 그림**  
![image](https://user-images.githubusercontent.com/28394879/140595929-9ae574a1-4f08-481a-b968-361814118f4b.png)

`template1.execute()`를 호출하면 템플릿 로직인 `AbstractTemplate.execute()`를 실행한다.   
여기서 중간에 `call()`메서드를 호출하는데, 이 부분이 오버라이딩 되어 있다.  
따라서 현재 인스턴스인 `SubClassLogic1`인스턴스의 `SubClassLogic1.call()`메서드가 호출된다.

템플릿 메서드 패턴은 이렇게 다형성을 사용해서 변하는 부분과 변하지 않는 부분을 분리하는 방법이다. 



</details>

<details> <summary> 4. 템플릿 메서드 패턴 - 예제3 </summary>

## 4. 템플릿 메서드 패턴 - 예제3

**익명 내부 클래스 사용하기**  
템플릿 메서드 패턴은 `SubClassLogic1`, `SubClassLogic2`처럼 클래스를 계속 만들어야 하는 단점이 있다.  
익명 내부 클래스를 사용하면 이런 단점을 보완할 수 있다.  
익명 내부 클래슬르 사용하면 객체 인스턴스를 생성하면서 동시에 생성한 클래스를 상속 받은 자식 클래스를 정의할 수 있다.  
이 클래스는 `SubClassLogic1`처럼 직접 지정하는 이름이 없고 클래스 내부에 선언되는 클래스여서 익명 내부 클래스라 한다.  
익명 내부 클래스에 대한 자세한 내용은 자바 기본 문법을 참고하자. 

**TemplateMethodTest - templateMethodV2() 추가**
```java
/**
 * 템플릿 메서드 패턴, 익명 내부 클래스 사용
 */
@Test
void templateMethodV2() {
 AbstractTemplate template1 = new AbstractTemplate() {
 @Override
 protected void call() {
 log.info("비즈니스 로직1 실행");
 }
 };
 log.info("클래스 이름1={}", template1.getClass());
 template1.execute();
 AbstractTemplate template2 = new AbstractTemplate() {
 @Override
 protected void call() {
 log.info("비즈니스 로직1 실행");
 }
 };
 log.info("클래스 이름2={}", template2.getClass());
 template2.execute();
}
```

**실행 결과**
```
클래스 이름1 class hello.advanced.trace.template.TemplateMethodTest$1
비즈니스 로직1 실행
resultTime=3
클래스 이름2 class hello.advanced.trace.template.TemplateMethodTest$2
비즈니스 로직2 실행
resultTime=0
```

실행 결과를 보면 자바가 임의로 만들어주는 익명 내부 클래스 이름은 `TemplateMethodTest$1`, `TemplateMethodTest$2`인 것을 확인할 수 있다. 



</details>

<details> <summary> 5. 템플릿 메서드 패턴 - 적용1 </summary>

## 5. 템플릿 메서드 패턴 - 적용1

이제 우리가 만든 애플리케이션의 로그 추적기 로직에 템플릿 메서드 패턴을 적용해보자. 

**AbstractTemplate**
```java
package hello.advanced.trace.template;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
public abstract class AbstractTemplate<T> {
 private final LogTrace trace;
 public AbstractTemplate(LogTrace trace) {
 this.trace = trace;
 }
 public T execute(String message) {
 TraceStatus status = null;
 try {
 status = trace.begin(message);
 //로직 호출
 T result = call();
 trace.end(status);
 return result;
 } catch (Exception e) {
 trace.exception(status, e);
 throw e;
 }
 }
 protected abstract T call();
}
```

- `AbstractTemplate`은 템플릿 메서드 패턴에서 부모 클래스이고, 템플릿 역할을 한다.
- `<T>` 제네릭을 사용했다. 반환 타입을 정의한다.
- 객체를 생성할 때 내부에서 사용할 `LogTrace trace`를 전달 받는다.
- 로그에 출력할 `message`를 외부에서 파라미터로 전달 받는다.
- 템플릿 코드 중간에 `call()` 메서드를 통해서 변하는 부분을 처리한다.
- `abstract T call()`은 변하는 부분을 처리하는 메서드이다. 이 부분은 상속으로 구현해야 한다.

**v3 -> v4 복사**  
먼저 기존 프로젝트 코드를 유지하기 위해 v4 애플리케이션을 복사해서 만들자.

- `hello.advanced.app.v4` 패키지 생성
- 복사
  - `v3.OrderControllerV3` -> `v4.OrderControllerV4`
  - `v3.OrderServiceV3` -> `v4.OrderServiceV4`
  - `v3.OrderRepositoryV3` -> `v4.OrderRepositoryV4`
- 코드 내부 의존관계 클래스를 V4으로 변경
  - `OrderControllerV4`: `OrderServiceV3` -> `OrderServiceV4`
  - `OrderServiceV4`: `OrderRepositoryV3` -> `OrderRepositoryV4`
- `OrderControllerV4` 매핑 정보 변경
  - `@GetMapping("/v4/request")`
- `AbstractTemplate`을 사용하도록 코드 변경 

**OrderControllerV4**
```java
package hello.advanced.app.v4;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {
 private final OrderServiceV4 orderService;
 private final LogTrace trace;
 @GetMapping("/v4/request")
 public String request(String itemId) {
 AbstractTemplate<String> template = new AbstractTemplate<>(trace) {
 @Override
 protected String call() {
 orderService.orderItem(itemId);
 return "ok";
 }
 };
 return template.execute("OrderController.request()");
 }
}
```

- `AbstractTemplate<String>`
  - 제네릭을 `String`으로 설정했다. 따라서 `AbstractTemplate`의 반환 타입은 `String`이 된다.
- 익명 내부 클래스
  - 익명 내부 클래스를 사용한다. 객체를 생성하면서 `AbstractTemplate`를 상속받은 자식 클래스를 정의했다.
  - 따라서 별도의 자식 클래스를 직접 만들지 않아도 된다.
- `template.execute("OrderController.request()")`
  - 템플릿을 실행하면서 로그를 남길 `message`를 전달한다. 

**OrderServiceV4**
```java
package hello.advanced.app.v4;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class OrderServiceV4 {
 private final OrderRepositoryV4 orderRepository;
 private final LogTrace trace;
 public void orderItem(String itemId) {
 AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
 @Override
 protected Void call() {
 orderRepository.save(itemId);
 return null;
 }
 };
 template.execute("OrderService.orderItem()");
 }
}
```

- `AbstractTemplate<Void>`
  - 제네릭에서 반환 타입이 필요한데, 반환할 내용이 없으면 `Void`타입을 사용하고 `null`을 반환하면 된다. 참고로 제네릭은 기본 타입인 `void`,`int`등을 선언할 수 없다.

**OrderRepositoryV4**
```java
package hello.advanced.app.v4;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV4 {
 private final LogTrace trace;
 public void save(String itemId) {
 AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
 @Override
 protected Void call() {
 //저장 로직
 if (itemId.equals("ex")) {
 throw new IllegalStateException("예외 발생!");
 }
 sleep(1000);
 return null;
 }
 };
 template.execute("OrderRepository.save()");
 }
 private void sleep(int millis) {
 try {
 Thread.sleep(millis);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
}
``` 

</details>

<details> <summary> 6. 템플릿 메서드 패턴 - 적용2 </summary>

## 6. 템플릿 메서드 패턴 - 적용2

템플릿 메서드 패턴 덕분에 변하는 코드와 변하지 않는 코드를 명확하게 분리했다.  
로그 출력, 템플릿 역할을 하고 변하지 않는 코드 모두 `AbstractTemplate`에 담아두고, 변하는 코드는 자식클래스를 만들어서 분리했다.

**지금까지 작성한 코드를 비교해보자**
```java
//OrderServiceV0 코드
public void orderItem(String itemId) {
 orderRepository.save(itemId);
}
//OrderServiceV3 코드
public void orderItem(String itemId) {
 TraceStatus status = null;
 try {
 status = trace.begin("OrderService.orderItem()");
 orderRepository.save(itemId); //핵심 기능
 trace.end(status);
 } catch (Exception e) {
 trace.exception(status, e);
 throw e;
 }
}
//OrderServiceV4 코드
AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
 @Override
 protected Void call() {
 orderRepository.save(itemId);
 return null;
 }
};
template.execute("OrderService.orderItem()");
```

- `OrderServiceV0`: 핵심 기능만 있다.
- `OrderServiceV3`: 핵심 기능과 부가 기능이 함께 섞여 있다.
- `OrderServiceV4`: 핵심 기능과 템플릿을 호출하는 코드가 섞여 있다.

V4는 템플릿 메서들 패턴을 사용한 덕분에 핵심 기능에 좀 더 집중할 수 있게 되었다.

**좋은 설계란?**  
- 좋은 설계란 **변경**이 일어날때 자연스럽게 드러난다.
- 지금까지 로그를 남기는 부분을 모아서 하나로 모듈화하고, 비즈니스 로직 부분을 분리했다.
- 여기서 만약 로그를 남기는 로직을 변경해야 한다고 생각해보자.
- 그래서 `AbstractTemplate` 코드를 변경해야 한다 가정해보자.
- 단순히 `AbstractTemplate` 코드만 변경하면 된다.
- 템플릿이 없는 `V3` 상태에서 로그를 남기는 로직을 변경해야 한다고 생각해보자. 
- 이 경우 모든 클래스를 다 찾아서 고쳐야 한다. 
- 클래스가 수백 개라면 생각만해도 끔찍한 상황이다.

**단일 책임 원칙(SRP)**
- `V4`는 단순히 템플릿 메서드 패턴을 적용해서 소스코드 몇줄을 줄인 것이 전부가 아니다.
- 로그를 남기는 부분에 대한 단일 책임 원칙(SRP)을 지킨것이다.
- 변경 지점을 하나로 모아서 변경에 쉽게 대처할 수 있는 구조를 만든 것이다. 



</details>

<details> <summary> 7. 템플릿 메서드 패턴 - 정의 </summary>

## 7. 템플릿 메서드 패턴 - 정의

GOF 디자인 패턴에서는 템플릿 메서드 패턴을 다음과 같이 정의했다.

> 템플릿 메서드 디자인 패턴의 목적은 다음과 같다  
> "작업에서 알고리즘의 골격을 정의하고 일분 단계를 하위 클래스로 연기합니다. 템플릿 메서드를 사용하면  
> 하위 클래스가 알고리즘의 구조를 변경하지 않고도 알고리즘의 특정 단게를 재정의 할 수 있다. [GOF]

**GOF 템플릿 메서드 패턴 정의**  
![image](https://user-images.githubusercontent.com/28394879/140649861-e9b4bbcc-0923-4201-ad57-b4f09f7a84fd.png)

풀어서 설명하면 다음과 같다.  
부모 클래스에 알고리즘의 골격인 템플릿을 정의하고, 일부 변경되는 로직은 자식 클래스에 정의하는 것이다.  
이렇게 하면 자식 클래스가 알고리즘의 전체 구조를 변경하지 않고, 특정 부분만 재정의할 수 있다.  
결국 상속과 오버라이딩을 통한 다형성으로 문제를 해결하는 것이다.

**하지만**  
템플릿 메서드 패턴은 상속을 사용한다. 따라서 상속에서 오는 단점들을 그대로 안고간다.  
특히 자식 클래스가 부모 클래스의 컴파일 시점에 강하게 결합되는 문제가 있다.   
이것은 의존관계에 대한 문제이다.  
자식 클래스 입장에서는 부모 클래스의 기능을 전혀 사용하지 않는다.  
이번 장에서 지금까지 작성했던 코드를 떠올려보자. 자식 클래스를 작성할 떄 부모 클래스의 기능을 사용한것이 있었던가?  
그럼에도 불구하고 템플릿 메서드 패턴을 위해 자식 클래스는 부모 클래스를 상속 받고 있다.  

상속을 받는다는 것은 특정 부모 클래스를 의존하고 있다는 것이다. 자식 클래스의 `extends`다음에 바로 부모 클래스가 코드상에 지정되어 있다.  
따라서 부모 클래스의 기능을 사용하든 사용하지 않든간에 부모 클래스를 강하게 의존하게 된다.  
여기서 강하게 의존한다는 뜻은 자식 클래스의 코드에 부모 클래스의 코드가 명확하게 적혀 있다는 뜻이다.  
UML에서 상속을 받으면 삼각형 화살표가 `자식 -> 부모`를 향하고 있는 것은 이런 의존관계를 반영하는 것이다.

자식 클래스 입장에서는 부모 클래스의 기능을 전혀 사용하지 않는데, 부모 클래스를 알아야한다. 이것은 좋은 설계가 아니다.  
그리고 이런 잘못된 의존관계 떄문에 부모 클래스를 수정하면, 자식 클래스에도 영향을 줄 수 있다.  

추가로 템플릿 메서드 패턴은 상속 구조를 사용하기 때문에, 별도의 클래스나 익명 내부 클래스를 만들어야 하는 부분도 복잡하다.  
지금까지 설명한 이런 부분들을 더 깔끔하게 개선하려면 어떻게 해야할까?  

템플릿 메서드 패턴과 비슷한 역할을 하면서 상속의 단점을 제거할 수 있는 디자인 패턴이 바로 전략 패턴(Strategy Pattern)이다.


</details>

<details> <summary> 8. 전략 패턴 - 시작 </summary>

## 8. 전략 패턴 - 시작

전략 패턴의 이해를 돕기 위해 템플릿 메서드 패턴에서 만들었던 동일한 예제를 만들어보자.

**ContextV1Text**
```java
package hello.advanced.trace.strategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
@Slf4j
public class ContextV1Test {
 @Test
 void strategyV0() {
 logic1();
 logic2();
 }
 private void logic1() {
 long startTime = System.currentTimeMillis();
 //비즈니스 로직 실행
 log.info("비즈니스 로직1 실행");
 //비즈니스 로직 종료
 long endTime = System.currentTimeMillis();
 long resultTime = endTime - startTime;
 log.info("resultTime={}", resultTime);
 }
 private void logic2() {
 long startTime = System.currentTimeMillis();
 //비즈니스 로직 실행
 log.info("비즈니스 로직2 실행");
 //비즈니스 로직 종료
 long endTime = System.currentTimeMillis();
 long resultTime = endTime - startTime;
 log.info("resultTime={}", resultTime);
 }
}
```

잘 동작하면 동일한 문제를 전략 패턴으로 풀어보자.

**실행 결과**
```
비즈니스 로직1 실행
resultTime=5
비즈니스 로직2 실행
resultTime=1
```

</details>

<details> <summary> 9. 전략 패턴 - 예제1 </summary>

</details>

<details> <summary> 10. 전략 패턴 - 예제2 </summary>

</details>

<details> <summary> 11. 전략 패턴 - 예제3 </summary>

</details>

<details> <summary> 12. 템플릿 콜백 패턴 - 시작 </summary>

</details>

<details> <summary> 13. 템플릿 콜백 패턴 - 예제 </summary>

</details>

<details> <summary> 14. 템플릿 콜백 패턴 - 적용 </summary>

</details>

<details> <summary> 15. 정리 </summary>

</details>