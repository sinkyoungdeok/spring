# 출처
- url: https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EC%BD%94%ED%8B%80%EB%A6%B0/dashboard
- 강사님: 예상국님

# 1. 소개

<details><summary> 1. 강의 소개 </summary>

## 1. 강의 소개

### Why Kotlin?
- JVM 언어/ Java와 100% 호환
- 현대 언어 지향
- 안정적인 null point 대응
- Java에 비해서 간결한 코드, Kotlin만의 표준함수 지원
- (지금은 Java10 이상 지원) 타입추론의 편리함 

</details>

<details><summary> 2. 개발환경 설치 </summary>

## 2. 개발환경 설치

- java8
- intellij

</details>

# 2. 스프링부트 소개 

<details><summary> 1. 스프링 부트 소개 </summary>

## 1. 스프링 부트 소개
- Spring Boot는 단순히 실행되며, 프러덕션 제품 수준의 스프링 기반 어플리케이션을 쉽게 만들 수 있다
- Spring Boot 어플리케이션에는 Spring 구성이 거의 필요 없다
- Spring Boot java -jar로 실행하는 Java 어플리케이션을 만들 수 있다

### 주요 목표
- Spring 개발에 대해 빠르고, 광범위하게 적용할 수 있는 환경
- 기본값 설정이 있지만 설정을 바꿀 수 있다
- 대규모 프로젝트에 공통적인 비 기능 제공 (보안, 모니터링 등등)
- XML 구성 요구사항이 전혀 없음 

### 정리
1. 어플리케이션 개발에 필수 요소들만 모아두었다
2. 간단한 설정으로 개발 및 커스텀이 가능하다
3. 간단하고, 빠르게 어플리케이션 실행 및 배포가 가능하다
4. 대규모프로젝트(운영환경)에 필요한 비 기능적 기능도 제공한다
5. 오랜 경험에서 나오는 안정적인 운영이 가능하다
6. Spring에서 불편한 설정이 없어졌다 (XML 설정 등등) 

</details>

# 3. 웹 개론

<details><summary> 1. Web 개론 </summary>

## 1. Web 개론

### Web 이란?
- (World Wide Web, WWW, W3)은 인터넷에 연결된 컴퓨터를 통해 사람들이 정볼르 공유 할 수 있는 전 세계적인 정보 공간을 말한다
- Web의 용도는 다양하게 나눌 수 있다
- 그중에서 우리가 제일 많이 접하는 부분
  1. Web Site (google, naver, daum, yahoo etc...)
  2. User Interface (Chrome, Safari, Explorer, Smart Watch, etc ...)
  3. API (Application Programming Interface) * Web Service (Kakao Open API, Google Open API, Naver Open API, etc ...)

### Web의 기반
1. HTTP
   - 어플리케이션 컨트롤
   - GET, POST, PUT, DELETE, OPTIONS, HEAD, TRACE, CONNECT의 Method가 존재 
2. URI
   - 리소스 식별자
   - 특정 사이트, 특정 쇼핑 목록, 동영상 목록 등 모든 정보에 접근 할 수 있는 정보
3. HTML
   - 하이퍼 미디어 포맷
   - XML을 바탕으로한 범용 문서 포맷
   - 이를 활용하여 Chrome, Safari, Explorer에서 사용자가 알아보기 쉬운 형태로 표현 

### REST
- REST(Representational State Transfer, 자원의 상태 전달)
- 네트워크 아키텍처 원리
1. Client, Server: 클라이언트와 서버가 서로 독립적으로 분리되어져 있어야 한다
2. Stateless: 요청에 대해서 클라이언트의 상태가 서버에 저장을 하지 않는다
3. 캐시: 클라이언트는 서버의 응답을 캐시 할 수 있어야 한다. 클라이언트가 캐시를 통해서 응답을 재사용할 수 있어야 하며, 이를 통해서 서버의 부하를 낮춘다
4. 계층화 (Layered System): 서버와 클라이언트 사이에, 방화벽, 게이트웨이, Proxy 등 다계층 형태를 구성할 수 있어야 하며, 확장 할 수 있어야 한다
5. 인터페이스 일관성: 아키텍처를 단순화시키고 작은 단위로 분리하여서, 클라이언트, 서버가 독집적으로 개선될 수 있어야 한다
6. Code On Demand (optional) 자바 애플릿, 자바스크립트 플래시 등 특정기능을 서버가 클라이언트에 코드를 전달하여 실행 할 수 있어야 한다

- 인터페이스의 일관성: 인터페이스 일관성이 잘 지켜졌는지에 따라 REST를 잘 사용했는지 판단을 할 수 있다.
  1. 자원 식별
  2. 메시지를 통한 리소스 조작
  3. 자기 서술적 메시지
  4. 애플리케이션 상태에 대한 엔진으로서 하이퍼미디어 

### 자원 식별
- 웹 기반의 REST에서는 리소스 접근을 URI를 사용한다
- https://foo.co.kr/user/100
  - Resource: user
  - 식별자: 100

### 메시지를 통한 리소스 조작
- Web에서는 다양한 방식으로 데이터를 전송할 수 있다.
- 그중에서는 HTML, XML, JSON, TEXT 등 다양한 방법이 있다
- 이 중에서 리소스의 타입을 알려주기 위해서 header 부분에 content-type를 통해서 어떠한 타입인지를 지정할 수 있다. 


</details>