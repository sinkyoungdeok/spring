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