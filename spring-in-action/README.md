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

### 11.5 리액티브 웹 API 보안

</details>