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

</details>

<details> <summary> 6. 로그 추적기V2 - 파라미터로 동기화 개발 </summary>

</details>

<details> <summary> 7. 로그 추적기 V2 - 적용 </summary>

</details>

<details> <summary> 8. 정리 </summary>

</details>