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

</details>

<details> <summary> 5. 로그 추적기V1 - 적용 </summary>

</details>

<details> <summary> 6. 로그 추적기V2 - 파라미터로 동기화 개발 </summary>

</details>

<details> <summary> 7. 로그 추적기 V2 - 적용 </summary>

</details>

<details> <summary> 8. 정리 </summary>

</details>