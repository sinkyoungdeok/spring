# 스프링 인 액션 - 개념 정리


# Part1. 스프링 기초1

<details><summary>1. 스프링 시작하기</summary>

## 1. 스프링 시작하기

### 1.1 스프링이란?

**스프링 애플리케이션 컨텍스트**  
- 스프링이 제공하는 컨테이너
- 애플리케이션 컴포넌트를 생성하고 관리
- 애플리케이션 컴포넌트 or 빈들은 스프링 애플리케이션 컨텍스트 내부에서 서로 연결되어 완전한 애플리케이션을 만듬

**의존성 주입(DI)**
- 빈의 상호 연결을 해주는 패턴
- 컨테이너가 애플리케이션 컴포넌트에서 의존(사용)하는 다른 빈의 생성과 관리를 해줌
- 컨테이너가 모든 컴포넌트를 생성, 관리하고 해당 컴포넌트를 필요로 하는 빈에 주입한다.
- 일반적으로 생성자 인자 또는 속성의 접근자 메서드를 통해 처리한다.

**자동-구성(autoconfiguration)**
- autowiring과 component scanning 기법을 기반으로 함 
- component scanning을 사용하여 애플리케이션의 classpath에 지정된 컴포넌트를 찾은 후 스프링 애플리케이션 컨텍스트의 빈으로 생성 
- autowiring을 사용하여 의존 관계가 있는 컴포넌트를 자동으로 다른 빈에 주입(연결) 한다.

### 1.2 스프링 애플리케이션 초기 설정하기

**@SpringBootApplication**
- @SpringBootConfiguration, @EnableAutoConfiguration, @ComponentScan 으로 구성

**@SpringBootConfiguration**
- 지정한 클래스를 구성클래스로 지정
- @Configuration 애노테이션의 특화된 형태

**@EnableAutoConfiguration**
- 스프링 부트 autoconfiguration을 활성화 한다.
- 우리가 필요로 하는 컴포넌트들을 자동으로 구성하도록 스프링 부트에 알려준다

**@ComponentScan**
- 컴포넌트 검색을 활성화
- @Component, @Controller, @Service등의 애노테이션과 함께 클래스를 선언할 수 있게 해줌
- 자동으로 위 클래스들을 찾아 스프링 애플리케이션 컨텍스트에 컴포넌트로 등록한다.


### 1.3 스프링 애플리케이션 작성하기

**Controller**
- 웹 요청과 응답을 처리하는 컴포넌트

### 1.4 스프링 살펴보기 

### 요약 

</details>

<details><summary> 2. 웹 애플리케이션 개발하기 </summary>

## 2. 웹 애플리케이션 개발하기

### 2.1 정보 보여주기

**Controller의 임무**
- 데이터를 가져오고 처리
- HTTP요청을 처리
- 브라우저에 보여줄 HTML을 뷰에 요청
- REST형태의 응답 몸체에 직접 데이터를 추가

**View의 임무**
- 브라우저에게 보여주는 데이터를 HTML로 나타내는 것

**Model의 임무**
- 컨트롤러와 뷰 사이에서 데이터를 운반하는 객체
- Model 객체의 속성에 있는 데이터는 뷰가 알 수 있는 서블릿 요청 속성들로 복사한다.

### 2.2 폼 제출 처리하기

### 2.3 폼 입력 유효성 검사하기

### 2.4 뷰 컨트롤러로 작업하기

### 2.5 뷰 템플릿 라이브러리 선택하기

</details>

<details> <summary>3. 데이터로 작업하기 </summary>

## 3. 데이터로 작업하기

### 3.1 JDBC를 사용해서 데이터 읽고 쓰기

### 3.2 스프링 데이터 JPA를 사용해서 데이터 저장하고 사용하기

</details>

<details> <summary>4. 스프링 시큐리티 </summary>

## 4. 스프링 시큐리티

### 4.1 스프링 시큐리티 활성화하기

### 4.2 스프링 시큐리티 구성하기

### 4.3 웹 요청 보안 처리하기 

### 4.4 사용자 인지하기

### 4.5 각 폼에 로그아웃 버튼 추가하고 사용자 정보 보여주기

</details>

<details> <summary>5. 구성 속성 사용하기 </summary>

## 5. 구성 속성 사용하기

### 5.1 자동-구성 세부 조정하기

**빈 연결(Bean Wiring)**
- 빈으로 생성되는 애플리케이션 컴포넌트 및 상호 간에 주입되는 방법을 선언하는 구성

**속성 주입(Property injection)**
- 빈의 속성 값을 설정하는 구성

### 5.2 우리의 구성 속성 생성하기

### 5.3 프로파일 사용해서 구성하기

</details>

# Part2. 통합된 스프링 

<details><summary> 6. REST 서비스 생성하기 </summary>

## 6. REST 서비스 생성하기

## 6.1 REST 컨트롤러 작성하기

## 6.2 하이퍼미디어 사용하기

## 6.3 데이터 기반 서비스 활성화하기

</details>

<details><summary> 7. REST 서비스 사용하기 </summary>

## 7. REST 서비스 사용하기

- RestTemplate: 스프링 프레임워크에서 제공하는 간단하고 동기화된 REST 클라이언트
- Traverson: 스프링 HATEOAS에서 제공하는 하이퍼링크를 인식하는 동기화 REST 클라이언트
- WebClient: 스프링5에서 소개된 반응형 비동기 REST 클라이언트

### 7.1 RestTemplate으로 REST 엔드포인트 사용하기

### 7.2 Traverson으로 REST API 사용하기

**Traverson**
- HATEOAS가 활성화된 API를 이동하면서 리소스를 쉽게 가져올 수 있다.
- 리소스를 쓰거나 삭제하는 메서드가 없다.

**RestTemplate**
- 리소스를 쓰거나 삭제할 수 있다.
- API를 이동하면서 리소스를 가져오는게 쉽지 않다

**API 이동과 리소스 변경이나 삭제를 모두 해야 하는경우**
- RestTemplate, Traverson을 함께 사용

**Traverson 동작**

1. Traverson 경로 설정
    ```java
    @Bean 
    public Traverson traverson() {
    Traverson traverson = new Traverson(
        URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);
    return traverson;
    }
    ```

2. 8080/api의 결과 
    ```json
    {
        "_links": {
            "ingredients": {
                "href": "http://localhost:8080/api/ingredients"
            },
            "users": {
                "href": "http://localhost:8080/api/users"
            },
            "orders": {
                "href": "http://localhost:8080/api/orders"
            },
            "tacos": {
                "href": "http://localhost:8080/api/tacos{?page,size,sort}",
                "templated": true
            },
            "profile": {
                "href": "http://localhost:8080/api/profile"
            }
        }
    }
    ```

3. traverson.follow("tacos") 했을 때 
   - localhost:8080/api 의 결과 json에서 tacos의 href의 string값으로 api요청 
   - 결과:
    ```json
    {
        "_embedded": {
            "tacos": [
                {
                    "name": "Carnivore",
                    "createdAt": "2022-01-15T06:23:23.700+0000",
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/tacos/2"
                        },
                        "taco": {
                            "href": "http://localhost:8080/api/tacos/2"
                        },
                        "ingredients": {
                            "href": "http://localhost:8080/api/tacos/2/ingredients"
                        }
                    }
                },
                {
                    "name": "Bovine Bounty",
                    "createdAt": "2022-01-15T06:23:23.718+0000",
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/tacos/3"
                        },
                        "taco": {
                            "href": "http://localhost:8080/api/tacos/3"
                        },
                        "ingredients": {
                            "href": "http://localhost:8080/api/tacos/3/ingredients"
                        }
                    }
                },
                {
                    "name": "Veg-Out",
                    "createdAt": "2022-01-15T06:23:23.722+0000",
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/tacos/4"
                        },
                        "taco": {
                            "href": "http://localhost:8080/api/tacos/4"
                        },
                        "ingredients": {
                            "href": "http://localhost:8080/api/tacos/4/ingredients"
                        }
                    }
                }
            ]
        },
        "_links": {
            "self": {
                "href": "http://localhost:8080/api/tacos{?page,size,sort}",
                "templated": true
            },
            "profile": {
                "href": "http://localhost:8080/api/profile/tacos"
            },
            "recents": {
                "href": "http://localhost:8080/api/tacos/recent"
            }
        },
        "page": {
            "size": 20,
            "totalElements": 3,
            "totalPages": 1,
            "number": 0
        }
    }
    ```

4. traverson.follow("tacos","recents")
   - 위의 3번 작업 후,
   - "http://localhost:8080/api/tacos{?page,size,sort}" 요청의 recents의 href값으로 api 요청 
   

</details>

<details><summary> 8. 비동기 메시지 전송하기 </summary>

## 8. 비동기 메시지 전송하기

### 8.1 JMS로 메시지 전송하기

**메시지 수신 방식**
- 풀 모델: 코드에서 메시지를 요청하고 도착할 때까지 기다림 
- 푸시 모델: 메시지가 수신 가능하게 되면 코드로 자동 전달 

**풀 모델**
- Receiver를 가져다가 사용 (요청하고나서 도착할 때까지 기다린다)
- 애플리케이션 코드에서 Receiver를 호출
   ```java
   @Profile("jms-template")
   @Component("templateOrderReceiver")
   public class JmsOrderReceiver implements OrderReceiver {
   
     private JmsTemplate jms;
   
     public JmsOrderReceiver(JmsTemplate jms) {
       this.jms = jms;
     }
     
     @Override
     public Order receiveOrder() {
       return (Order) jms.receiveAndConvert("tacocloud.order.queue");
     }
     
   }
   ```

**푸시 모델**
- 리스너를 통해서 메시지 수신이 가능할 때 처리 
- 애플리케이션 코드에서 호출하지 않는다. 
   ```java
   @Profile("jms-listener")
   @Component
   public class OrderListener {
     
     private KitchenUI ui;
   
     @Autowired
     public OrderListener(KitchenUI ui) {
       this.ui = ui;
     }
   
     @JmsListener(destination = "tacocloud.order.queue")
     public void receiveOrder(Order order) {
       ui.displayOrder(order);
     }
     
   }
   ```
**JMS**
- 표준 자바 명세에 정의되어 있다
- 여러 브로커에서 지원되므로 자바의 메시징에 많이 사용됨
- 자바 명세이므로 자바 애플리케이션에서만 사용할 수 있다는 단점이 있다 
- RabbitMQ와 카프카 같은 메시징 시스템은 위의 단점을 해결하였다 (다른 언어와 JVM 외의 다른 플랫폼에서 사용 가능)


### 8.2 RabbitMQ와 AMQP 사용하기

**RabbitMQ**
- ![image](https://user-images.githubusercontent.com/28394879/149644670-23bcec37-e48f-41d0-b980-45ac73065bfa.png)
- AMQP의 중요한 구현이 RabbitMQ
- JMS보다 더 진보된 메시지 라우팅 전략을 제공
- JMS는 메시지가 수신자가 가져갈 메시지 도착지의 이름을 주소로 사용하는 반면, AMQP 메시지는 수신자가 리스닝하는 큐와 분리된 거래소(exchange)이름과 라우팅 키를 주소로 사용한다 
- 메시지가 RabbitMQ 브로커에 도착하면 주소로 지정된 거래소에 들어감
- 거래소는 하나 이상의 큐에 메시지를 전달할 책임이 있다
- JMS 와 동일하게 풀 모델, 푸시 모델이 있다.

**거래소**
- 기본(Default): 브로커가 자동으로 생성하는 특별한 거래소. 메시지의 라우팅 키와 이름이 같은 큐로 메시지를 전달함. 모든 큐는 자동으로 기본 거래소와 연결됨 
- 다이렉트(Direct): 바인딩 키가 메시지의 라우팅 키와 같은 큐에 메시지를 전달함
- 토픽(Topic): 바인딩 키(와일드카드를 포함하는)가 메시지의 라우팅 키와 일치하는 하나 이상의 큐에 메시지를 전달
- 팬아웃(Fanout): 바인딩 키나 라우팅 키에 상관없이 모든 연결된 큐에 메시지를 전달
- 헤더(Header): 토픽 거래소와 유사하며, 라우팅 키 대신 메시지 헤더 값을 기반으로 한다는 것만 다르다
- 데드 레더(Dead letter): 정의된 어떤 거래소-큐 바인딩과도 일치하지 않는 모든 메시지를 보관하는 잡동사니 거래소이다.

### 8.3 카프카 사용하기

**Kafka**
- ![image](https://user-images.githubusercontent.com/28394879/149647200-49845a45-8a29-4e5b-b82a-c22a654b8ee3.png)
- 높은 확장성을 제공하는 클러스터로 실행되도록 설계되었다.
- 클러스터의 모든 카프카 인스턴스에 걸쳐 토픽을 파티션으로 분할하여 메시지를 관리한다
- RabbitMQ가 거래소와 큐를 사용해서 메시지를 처리하는 반면, 카프카는 토픽만 사용한다 
- 카프카의 토픽은 클러스터의 모든 브로커에 복제된다
- 클러스터의 각 노드는 하나 이상의 토픽에 대한 리더로 동작하며, 토픽 데이터를 관리하고 클러스터의 다른 노드로 데이터를 복제한다
- 토픽은 여러 개의 파티션으로 분할될 수 있다. 이 경우 클러스터의 각 노드는 한 토픽의 하나 이상의 파티션(토픽 전체가 아니다)의 리더가 된다
- KafkaTemplate은 메시지를 수신하는 메서드를 일체 제공하지 않는다는 점이 JmsTemplate, RabbitTemplate과 다르다.
- 스프링을 사용해서 카프카 토픽의 메시지를 가져오는 유일한 방법은 메시지 리스너를 작성하는 것이다

### 8.4 비동기 메시지 전송과 수신 기능이 추가된 타코 클라우드 애플리케이션 빌드 및 실행하기

</details>

<details> <summary> 9. 스프링 통합하기 </summary>

## 9. 스프링 통합하기

### 9.1 간단한 통합 플로우 선언하기

### 9.2 스프링 통합의 컴포넌트 살펴보기

### 9.3 이메일 통합 플로우 생성하기

</details>

# Part3. 리액티브 스프링

<details> <summary> 10. 리액터 개요 </summary>

## 10. 리액터 개요 

### 10.1 리액티브 프로그래밍 이해하기

**backpressure(배압)**
- 컨슈머가 처리할 수 있는 만큼으로 전달 데이터를 제한함으로써 빠른 데이터 소스로부터의 데이터 전달 폭주를 피할 수 있는 수단이다.

**리액티브 스트림 4개의 인터페이스**
- Publisher(발행자)
- Subscriber(구독자)
- Subscription(구독)
- Processor(프로세서)

### 10.2 리액터 시작하기

**리액터**
- 핵심 타입 2가지: Mono, Flux (두개다 리액티브 스트림의 Publisher 인터페이스를 구현)
- Flux: 0,1 또는 다수의 데이터를 갖는 파이프라인
- Mono: 하나의 데이터 항목만 갖는 데이터셋에 최적화된 리액티브 타입

**리액터 vs RxJava**
- Mono <--> Observable
- Flux <--> Single 
- 위의 2가지가 서로 개념적으로 거의 같고, 여러 동일한 오퍼레이션을 제공한다.

### 10.3 리액티브 오퍼레이션 적용하기

**Flux와 Mono의 오퍼레이션 분류**
- 생성 오퍼레이션
- 조합 오퍼레이션
- 변환 오퍼레이션
- 로직 오퍼레이션

</details>

<details><summary> 11. 리액티브 API 개발하기 </summary>

## 11. 리액티브 API 개발하기

**스프링 MVC**
- 서블릿 기반의 웹 프레임워크
- 스레드 블로킹과 다중 스레드로 수행
- 요청이 될 때 마다 스레드 풀에서 작업 스레드를 가져와서 요청을 처리, 작업 스레드가 종료될 때 까지 요청 스레드는 블로킹됨
- 요청량의 증가에 따른 확장이 어렵다
- 처리가 느린 작업 스레드가 있을 경우 더욱 확장이 어렵다 
- 스레드 풀로 반환되어 또 다른 요청 처리를 준비하는 데 많은 시간이 걸린다

**비동기 웹 프레임워크(WebFlux)**
- ![image](https://user-images.githubusercontent.com/28394879/149870466-934d5abd-8ddd-428e-a151-6a9bf0b0d38f.png)
- 적은 수의 스레드(일반적으로 CPU 코어당 하나)로 높은 확장성을 성취
- "이벤트 루핑"이라는 기법을 적용해 한 스레드당 많은 요청을 처리할 수 있어서, 한 연결당 소요 비용이 경제적이다
- 소수의 스레드로 많은 요청을 처리할 수 있어서 스레드 관리 부담이 줄어들고 확장이 용이하다.

**이벤트 루프**
- 데이터베이스나 네트워크 작업과 같은 집중적인 작업의 콜백과 요청을 비롯해서, 이벤트 루프에서는 모든 것이 이벤트로 처리
- 비용이 드는 작업이 필요할 때 이벤트 루프는 해당 작업의 콜백을 등록하여 병행으로 수행되게 하고 다른 이벤트 처리로 넘어간다


### 11.1 스프링 WebFlux 사용하기

![image](https://user-images.githubusercontent.com/28394879/149870970-fbb886e1-83ad-4859-99bb-89592e6c6df5.png)

### 11.2 함수형 요청 핸들러 정의하기

**스프링 MVC의 단점**
- 어떤 애노테이션 기반 프로그래밍이건 애노테이션이 "무엇"을 하는지와 "어떻게" 해야 하는지를 정의하는데 괴리가 있다.
- 애노테이션이 "무엇"을 정의하며, "어떻게"는 프레임워크 코드의 어딘가에 정의되어 있다.
- 위의 특징들 때문에, 프로그래밍 모델을 커스터마이징하거나 확장할 때 복잡해진다.
- 이런 변경을 하려면 애노테이션 외부에 있는 코드로 작업해야 하기 때문이다.
- 애노테이션에 중단점을 설정할 수 없기 때문에 디버깅도 어렵다.

**함수형 프로그래밍 모델의 네가지 기본 타입**
- RequestPredicate: 처리될 요청의 종류를 선언
- RouterFunction: 일치하는 요청이 어떻게 핸들러에게 전달되어야 하는지를 선언
- ServerRequest: HTTP 요청을 나타내며, 헤더와 몸체 정보를 사용할 수 있다
- ServerResponse: HTTP 요청을 나타내며, 헤더와 몸체 정볼르 포함한다. 

### 11.3 리액티브 컨트롤러 테스트하기

**WebTestClient**
- WebFlux를 사용하는 리액티브 컨트롤러의 테스트를 쉽게 작성하게 해주는 새로운 테스트 유틸리티


### 11.4 REST API를 리액티브하게 사용하기

**WebClient**
- RestTemplate에서는 리액티브 타입을 지원하지 않는다.
- RestTemplate의 리액티브 대안으로 Webclient가 있다
- 외부 API로 요청할 때 리액티브 타입의 전송과 수신 모두를 한다
- WebClient를 사용하는 것은 RestTemplate과 많이 다르다
  - 다수의 메서드로 서로 다른 종류의 요청을 처리하는 대신 WebClient는 요청을 나타내고 전송하게 해주는 빌더 방식의 인터페이스를 사용한다.
  - WebClient를 사용하는 일반적인 패턴은 다음과 같다
    - WebClient의 인스턴스를 생성한다(또는 WebClient 빈을 주입한다)
    - 요청을 전송할 HTTP 메서드를 지정한다
    - 요청에 필요한 URI와 헤더를 지정한다
    - 요청을 제출한다
    - 응답을 사용한다.

### 11.5 리액티브 웹 API 보안

**서블릿 필터**
- 스프링 시큐리티의 웹 보안 모델
- 요청자가 올바른 권한을 갖고 있는지 확인하기 위해 서블릿 기반 웹 프레임워크의 요청 바운드를 가로채야 할 때 사용
- WebFlux에서는 이런 방법이 곤랂하다.
- 스프링 WebFlux로 웹애플리케이션을 작성할 때는 서블릿이 개입된다는 보장이 없다.
- 리액티브 웹 애플리케이션은 Netty나 일부 다른 non-서블릿 서버에 구축될 가능성이 많다.

**WebFlux 애플리케이션의 보안**
- 서블릿 필터를 사용할 수 없다.
- 5.0.0 버전부터 스프링 시큐리티는 서블릿 기반의 스프링 MVC와 리액티브 스프링 WebFlux 애플리케이션 모두의 보안에 사용될 수 있다.
  - 스프링의 WebFilter가 이 일을 해준다.
  - WebFilter는 서블릿 API에 의존하지 않는 스프링 특유의 서블릿 필터 같은 것이다. 
- 리액티브 스프링 시큐리티의 구성 모델과 4장에서 알아본 스프링 시큐리티와 비슷하다.
- 스프링 MVC와 스프링 WebFlux는 다른 의존성을 갖지만, 스프링 시큐리티는 MVC, WebFlux 둘다 동일한 스프링 부트 보안 스타터를 사용한다.
- 하지만, 리액티브 구성 모델과 리액티브가 아닌 구성 모델 간에는 사소한 차이가 있다. 

**웹 애플리케이션의 보안 구성**
1. MVC
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/design", "/orders").hasAuthority("USER")
                .antMatchers("/**").permitAll();
    }
}
```

2. WebFlux
```java
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http 
                .authorizeExchange()
                .pathMatchers("/design", "/orders").hasAuthority("USER")
                .anyExchange().permitAll()
                .and()
                .build();
    }
}
```

**UserDetails 객체로 정의하는 인증 로직**
1. MVC
```java
@Autowired
UserRepository userRepo;

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepo.findByUsername(username);
                if (user == null) {
                    throw new UsernameNotFoundException( username + " not found")
                }
                return user.toUserDetails();
            }
        });    
}
```

2. WebFlux
```java
@Service
public ReactiveUserDetailService userDetailsService(UserRepositroy userRepo) {
    return new ReactiveUserDetailsService() {
        @Override
        public Mono<UserDetails> findByUsername(String username) {
            return userRepo.findByUsername(username)
                .map(user -> {
                   return user.toUserDetails(); 
                });
        }
    }
}
```

### 요약 
- 스프링 WebFlux는 리액티브 웹 프레임워크를 제공한다. 이 프레임워크의 프로그래밍 모델은 스프링 MVC가 많이 반영되었다. 심지어는 애노테이션도 많은 것을 공유한다.
- 스프링 5는 또한 스프링 WebFlux의 대안으로 함수형 프로그래밍 모델을 제공한다
- 리액티브 컨트롤러는 WebTestClient를 사용해서 테스트할 수 있다
- 클라이언트 측에는 스프링 5가 스프링 RestTemplate의 리액티브 버전인 WebClient를 제공한다
- 스프링 시큐리티 5는 리액티브 보안을 지원하며, 이것의 프로그래밍 모델은 리액티브가 아닌 스프링 MVC 애플리케이션의 것과 크게 다르지 않다

</details>

<details><summary> 12. 리액티브 데이터 퍼시스턴스 </summary>

## 12. 리액티브 데이터 퍼시스턴스

### 12.1 스프링 데이터의 리액티브 개념 이해하기

**스프링 데이터 리액티브 개요**
- 스프링 데이터 리액티브의 핵심은 리액티브 리퍼지터리는 도메인 타입이나 컬렉션 대신 Mono나 Flux를 인자로 받거나 반환하는 메서드를 갖는다
- 예)
  - `Flux<Ingredient> findByType(Ingredient.Type type);`
  - `Flux<Taco> saveAll(Publisher<Taco> tacoPublisher);`
- 리액티브가 아닌 리퍼지터리와 거의 동일한 프로그래밍 모델을 공유한다.
- 단, 리액티브 리퍼지터리는 Mono나 Flux를 인자로 받거나 반환하는 메서드를 갖는다는것만 다르다. 

**리액티브와 리액티브가 아닌 타입 간의 변환**
- 관계형 데이터베이스에서는 아직 리액티브 프로그래밍 모델에서 지원하지 않는다. (카산드라, 몽고DB, 카우치베이스, 레디스 만 지원하고 있다)
- 다른 DB로는 전환이 불가능하고, 관계형 DB를 사용하고 있는 상황에서 리액티브 프로그래밍을 적용할 수 없을까? ==> 할 수 있다.
- 클라이언트부터 데이터베이스까지 리액티브 모델을 가질 때 리액티브 프로그래밍의 장점이 완전히 발휘된다.
- 데이터베이스가 리액티브가 아닌 경우에는 일부 장점을 살릴 수 있다. 
  - 데이터베이스가 블로킹 쿼리를 사용하더라도, 블로킹 되는 방식으로 데이터를 가져와서 가능한 빨리 리액티브 타입으로 변환하여 상위 컴포넌트들이 리액티브의 장점을 활용하게 할 수 있다.
- 예) 리퍼지터리의 리액티브가 아닌 블로킹 코드를 격리시키고 애플리케이션의 어디서든 리액티브 타입으로 처리하게 하는 방법
  - `List<Order> findByUser(User user);`
  - ```java
    List<Order> orders = repo.findByUser(someUser);
    Flux<Order> orderFlux = Flux.fromIterable(orders);
    ```
  - ```java
    Order order = repo.findById(Long id);
    Mono<Order>
    ```
- 예) Mono나 Flux를 사용하면서 리액티브가 아닌 JPA 리퍼지터리에 save()를 호출해서 저장해야 할 경우
  - ```java
    Taco taco = tacoMono.block(); // 추출작업을 수행하기 위해 블로킹 오퍼레이션을 실행
    tacoRepo.save(taco);
    ```
  - ```java
    Iterable<Taco> tacos = tacoFlux.toIterable(); // Flux가 발행하는 모든 객체를 모아서 Iterable 타입으로 추출한다. 
    tacoRepo.saveAll(tacos);
    ```
  - Mono.block()이나, Flux.toIterable()은 추출 작업을 할 때 블로킹이 되므로 리액티브 프로그래밍 모델을 벗어난다. 그러므로 이런 식의 Mono나 Flux 사용은 가급적 적게 사용하는 것이 좋다
- 예) 블로킹되는 추출 오퍼레이션을 피하는 더 리액티브한 방법(Mono나 Flux를 구독하면서 발생되는 요소 각각에 대해 원하는 오퍼레이션을 수행)
  - ```java
    tacoFlux.subscribe(taco -> {
        tacoRepo.save(taco); 
    });
    ```
  - save()메서드는 여전히 리액티브가 아닌 블로킹 오퍼레이션이다.
  - 그러나, Flux나 Mono가 발행하는 데이터를 소비하고 처리하는 리액티브 방식의 subscribe()를 사용하므로 블로킹 방식의 일괄처리보다는 더 바람직하다. 


### 12.2 리액티브 카산드라 리퍼지터리 사용하기

**카산드라**
- 분산처리, 고성능, 상시 가용, 궁극적인 일관성을 갖는 NoSQL
- 데이터를 테이블에 저장된 행으로 처리 
- 각 행은 일대다 관계의 많은 분산 노드에 걸쳐 분할된다.
- 한 노드가 모든 데이터를 갖지는 않지만, 특정 행은 다수의 노드에 걸쳐 복제될 수 있으므로 단일 장애점(한 노드에 문제가 생기면 전체가 사용 불가능)을 없애준다.

**스프링 데이터 카산드라**
- 리액티브가 아닌 리퍼지터리와 리액티브 리퍼지터리는 각각 다른 의존성을 빌드에 추가해야한다.
- 키 공간을 자동으로 생성하도록 스프링 데이터 카산드라를 구성할 수 있지만, 우리가 직접 생성 또는 기존키 공간을 사용하는것이 훨씬 쉽다. 
  - CQL셸에서 다음과 같이 명령을 사용하면 타코 클라우드 애플리케이션의 키 공간을 생성할 수 있다
  - ```
    cqlsh> create keyspace tacocloud
    ... with replication={'class':'SimpleStrategy', 'replication_factor':1}
    ... and durable_writes=true;
    ```
- 로컬에서 카산드라 데이터베이스를 사용할 때 필요한 속성
  ```
  spring:
    data:
        cassandra:
            keyspace-name: tacocloud
            shcema-action: recreate-drop-unused
  ``` 

**카산드라 데이터 모델링 이해하기**
- 관계형 데이터베이스에 저장하기 위해 데이터를 모델링하는 것과 다르다.
- 카산드라 테이블은 얼마든지 많은 열을 가질 수 있다.
- 그러나 모든 행이 같은 열을 갖지 않고, 행마다 서로 다른 열을 가질 수 있다
- 파티션키와 클러스터링 키 두 종류의 키를 갖는다
  - 파티션키: 각 행이 유지 관리되는 파티션을 결정하기 위해 사용, 해시 오퍼레이션이 각 행의 파티션 키에 수행됨
  - 클러스터링 키: 각 행이 파티션 내부에서 행의 순서를 결정하기 위해 사용
- 읽기 오퍼레이션에 최적화되어 있다.
  - 테이블이 비정규화되고 데이터가 다수의 테이블에 걸쳐 중복되는 경우가 흔하다
  - 예) 고객 정보는 고객 테이블에 저장되지만, 각 고객의 주문 정보를 포함하는 테이블에도 중복 저장될 수 있다
- JPA 애노테이션을 단순히 카산드라 애노테이션으로 변경한다고 해서 카산드라에 적용할 수 있는 것은 아니다 
  - 데이터를 어떻게 모델링 할 것인지 다시 생각해야 한다 
- 데이터의 컬렉션을 포함하는 열은 네이티브 타입(정수, 문자열등)의 컬렉션이거나, 사용자 정의 타입(UDT)의 컬렉션이어야 한다. 

**도메인 타입 매핑 JPA vs 카산드라**
- @Entity <--> @Table("ingredients")
- @Id <--> @PrimaryKey

![image](https://user-images.githubusercontent.com/28394879/150081388-73c1bd35-9a41-4472-8819-18b06904392b.png)

![image](https://user-images.githubusercontent.com/28394879/150081766-cec7e811-7a20-47e1-92ec-595b17c8cbe4.png)

**리액티브 카산드라 리퍼지터리 작성**
- ReactiveCassandraRepository나 ReactiveCrudRepository를 선택 가능
  - 어떻게 리퍼지터리를 사용하느냐에 따라 선택해야됨
  - ReactiveCassandraRepository: ReactiveCrudRepository를 확장하여 새 객체가 저장될 때 사용되는 insert()메서드의 몇가지 변형 버전을 제공
    - 이외에는 ReactiveCrudRepository와 동일한 메서드를 제공
  - 만일 많은 데이터를 추가한다면 ReactiveCassandraRepository를 선택, 그렇지 않을 땐 ReactiveCrudRepository를 선택하는 것이 좋다 
- 리액티브가 아닌 리퍼지터리를 사용할 때
  - CrudRepository나 CassandraRepository 인터페이스를 우리 리퍼지터리 인터페이스에서 확장하면 됨 
  - 그다음 Flux나 Mono대신, 카산드라 애노테이션이 지정된 도메인 타입이나 이 도메인 타입이 저장된 컬렉션을 우리 리퍼지터리 메서드에서 반환하면 된다 
- 카산드라의 특성상 관계형 데이터베이스에서 SQL로 하듯이 테이블을 단순하게 where절로 쿼리할 수 없다. 
  - 카산드라가 데이터 읽기에 최적화되어 있지만, where절을 사용한 필터링 결과는 빠른 쿼리와는 달리 너무 느리게 처리될 수 있다.
  - 그렇지만 결과가 하나 이상의 열로 필터링되는 테이블 쿼리에는 매우 유용하므로 where절을 사용할 필요가 있다.

**@AllowFiltering**
- @AllowFiltering을 지정하지 않을 경우
  - `select * from users where username='검색할 사용자 이름';`
- @AllowFiltering을 지정한 경우
  - `select * from users where username='검색할 사용자 이름' allow filtering;`
- allow filtering 절은 '쿼리 성능에 잠재적인 영향을 준다는 것을 알고 있지만, 어쨌든 수행해야 한다'는 것을 카산드라에게 알려준다
- 


### 12.3 리액티브 몽고DB 리퍼지터리 작성하기

**MongoDB**
- 잘 알려진 NoSQL
- 카산드라가 테이블의 행으로 데이터를 저장, 몽고DB는 문서형 데이터베이스이다.
- BSON(Binary JSON)형식의 문서로 데이터를 저장하며, 다른 데이터베이스에서 데이터를 쿼리하는 것과 거의 유사한 방법으로 문서를 쿼리하거나 검색 할 수 있다
- NoSQL이므로 관계형 DB랑 다르게 데이터 모델링 및 관리가 필요하다.
- 스프링 데이터로 사용하는 방법은 JPA나 카산드라랑 비슷하다
- 도메인 타입을 문서 구조로 매핑하는 애노테이션을 도메인 클래스에 지정한다.

**Spring Data MongoDB**
- 리액티브가 아닌 몽고 DB, 리액티브 몽고DB 각각 다른 의존성을 빌드해야 한다.
- 기본포트: 27017
- 테스트와 개발에 편리하도록 내장된 몽고DB를 대신 사용할 수 있는데, 이 때는 Flapdoodle 의존성을 빌드에 추가하면 됨
  - Flapdoodle 내장 데이터베이스는 인메모리 몽고 DB 데이터베이스를 사용하는 것과 동일한 편의성을 제공한다.
  - 애플리케이션을 다시 시작하면 모든 데이터가 없어지고 데이터베이스가 초기화된다

**도메인 타입을 문서로 매핑**
- 몽고DB에 저장되는 문서 구조로 도메인 타입을 매핑하는데 유용한 애노테이션 6개를 제공한다.
- 그 중 3개만을 대부분 사용한다
  - @Id: 문서 ID로 지정
  - @Document: 문서로 선언
  - @Field: 문서에 속성을 저장하기 위해 필드이름을 지정(지정 하지 않으면, 필드이름 과 속성이름이 같은 것으로 간주한다)
- 카산드라는 별도의 UDT를 지정해야 됐지만, 몽고DB는 UDT지정 없이 간단하게 구성할 수 있다.
  ```java
  @Data
  @RestResource(rel="tacos", path="tacos")
  @Document
  public class Taco {
  
    @Id
    private String id;
  
    @NotNull
    @Size(min=5, message="Name must be at least 5 chracters long")
    private String name;
  
    private Date createdAt = new Date();
  
    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<Ingredient> ingredients; // UDT필요없다!
  }
  ``` 
- ID로 String 타입의 속성을 사용하면 몽고DB가 자동으로 ID값을 값을 지정해준다 ( null일 경우만 )
- List<Ingredient>는 JPA버전과 다르게 별도의 몽고 DB 컬렉션에 저장되지 않고, 카산드라 버전과 매우 유사하게 비정규화된 상태로 타코 문서에 직접 저장한다.
  - 그러나 카산드라와는 다르게 몽고DB에는 사용자 정의 타입을 만들 필요없이 어떤 타입도 사용할 수 있다.
  - @Document가 지정된 또 다른 타입이나 단순한 POJO 모두 가능하다 

**리액티브 몽고DB 리퍼지터리 인터페이스 작성**
- ReactiveCrudRepository나 ReactiveMongoRepository를 선택 
  - ReactiveCrudRepository: 새로운 문서나 기존 문서의 save() 메서드에 의존 
  - ReactivemongoRepository: 새로운 문서의 저장에 최적화된 소수의 특별한 insert() 메서드를 제공
  - 리액티브가 아닌 경우 CrudRepositroy나 MongoRepository를 사용하면 된다 
- ReactiveCrudRepository는 카산드라나 몽고DB둘다 지원하므로, ReactiveMongoRepository나 ReactiveCasandraRepository가 제공하는 기능을 써야하는 상황을 제외해선 ReactiveCrudRepository를 사용하면, 나중에 DB를 변경할 때 좋은 이점을 가져갈 수 있을 것 이다.
- 문서를 자주 생성해야 하는 상황이면 ReactiveMongoRepositroy를 선택하는것이 좋다. (최적화된 insert()메서드 사용을 위해)
  - 단점은 다른 DB로 변경이 힘들다는점. (다른 DB로전환하지 않으면 써도 된다)
- 카산드라와 마찬가지로 PagingAndSortingRepository는 리액티브 리퍼지터리에 적합하지 않다 ( take로 처리가능 )
  - `repo.findByOrderByCreatedAtDesc().take(12)` 로 대체가능


### 요약
- 스프링 데이터는 카산드라, 몽고DB, 카우치베이스, 레디스 데이터베이스의 리액티브 리퍼지터리를 지원
- 스프링 데이터의 리액티브 리퍼지터리는 리액티브가 아닌 리퍼지터리와 동일한 프로그래밍 모델을 따른다. 단, Flux나 Mono와 같은 리액티브 타입을 사용한다
- JPA 리퍼지터리와 같은 리액티브가 아닌 리퍼지터리는 Mono나 Flux를 사용하도록 조정할 수 있지만, 데이터를 가져오거나 저장할 때 여전히 블로킹이 생긴다
- 관계형이 아닌 데이터베이스를 사용하려면 해당 데이터베이스에서 데이터를 저장하는 방법에 맞게 데이터를 모델링하는 방법을 알아야 한다

</details>

# Part4. 클라우드 네이티브 스프링
<details> <summary> 13. 서비스 탐구하기 </summary>

## 13. 서비스 탐구하기

### 13.1 마이크로서비스 이해하기

**Monolithic의 특징**
- 전체를 파악하기 어렵다: 코드가 점점 많아질수록 애플리케이션에 있는 가 컴포넌트의 역할을 알기 어려워진다
- 테스트가 더 어렵다: 애플리케이션이 커지면서 통합과 테스트가 더 복잡해진다
- 라이브러리 간의 충돌이 생기기 쉽다: 애플리케이션의 한 기능에서 필요한 라이브러리 의존성이 다른 기능에서 필요한 라이브러리 의존성과 호환되지 않을 수 있다
- 확장 시에 비효율적이다: 시스템 확장을 목적으로 더 많은 서버에 애플리케이션을 배포해야 할 때는 애플리케이션의 일부가 아닌 전체를 배포해야 한다. 애플리케이션 기능의 일부만 확장하더라도 마찬가지다.
- 적용할 테크놀러지를 결정할 때도 애플리케이션 전체를 고려해야 한다: 애플리케이션에 사용할 프로그래밍 언어, 런타임 플랫폼, 프레임워크, 라이브러리를 선택할 때 애플리케이션 전체를 고려하여 선택해야 한다
- 프로덕션으로 이양하기 위해 많은 노력이 필요하다: 애플리케이션을 한 덩어리로 배포하므로 프로덕션으로 이양하는 것이 더 쉬운 것처럼 보일 수 있다. 그러나 일반적으로 단일 애플리케이션은 크기와 복잡도 때문에 더 엄격한 개발 프로세스와 더욱 철두철미한 테스트가 필요하다. 고품질과 무결함을 보장하기 위해서다. 

**MSA의 특징**
- 마이크로서비스는 쉽게 이해할 수 있다: 다른 마이크로서비스와 협력할 때 각 마이크로서비스는 작으면서 한정된 처리를 수행한다. 따라서 마이크로서비스는 자신의 목적에만 집중하므로 더 이해하기 쉽다
- 마이크로서비스는 테스트가 쉽다: 크기가 작을수록 테스트가 쉬워지는 것은 분명한 사실이다. 마이크로서비스 테스트도 이와 마찬가지다.
- 마이크로서비스는 라이브러리 비호환성 문제가 생기지 않는다: 각 마이크로서비스는 다른 마이크로서비스와 공유되지 않는 빌드 의존성을 가지므로 라이브러리 충돌 문제가 생기지 않는다
- 마이크로서비스는 독자적으로 규모를 조정할 수 있다: 만일 특정 마이크로서비스의 규모가 더 커야 한다면, 애플리케이션의 다른 마이크로서비스에 영향을 주지 않고 메모리 할당이나 인스턴스의 수를 더 크게 조정할 수 있다
- 각 마이크로서비스에 적용할 테크놀러지를 다르게 선택할 수 있다: 각 마이크로서비스에 사용할 프로그래밍 언어, 플랫폼, 프레임워크, 라이브러리를 서로 다르게 선택할 수 있다. 실제로 자바로 개발된 마이크로서비스가 C#으로 개발된 다른 마이크로서비스와 함께 동작하도록 할 수 있다
- 마이크로서비스는 언제든 프로덕션으로 이양할 수 있다: 마이크로서비스 아키텍처 기반으로 개발된 애플리케이션이 여러 개의 마이크로서비스로 구성되었더라도 각 마이크로서비스를 따로 배포할 수 있다. 그리고 마이크로서비스는 작으면서 특정 목적에만 집중되어 있고 테스트하기 쉬우므로, 마이크로서비스를 프로덕션으로 이양하는 데 따른 노력이 거의 들지 않는다. 또한, 프로덕션으로 이양하는 데 필요한 시간도 수개월이나 수주 대신 수시간이나 수분이면 된다.


### 13.2 서비스 레지스트리 설정하기

### 13.3 서비스 등록하고 찾기

### 요약

</details>