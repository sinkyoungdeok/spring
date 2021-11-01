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