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

### 자기서술적 메시지
- 요청하는 데이터가 어떻게 처리 되어져야 하는지 충분한 데이터를 포함 할 수 있어야 한다.
- HTTP 기반의 REST에서는 HTTP Method와 Header의 정보로 이를 표현할 수 있다 

### 애플리케이션 상태에 대한 엔진으로서 하이퍼미디어
- REST API를 개발할 떄에도 단순히 Client 요청에 대한 데이터만 내리는 것이 아닌 관련된 리소스에 대한 Link 정보까지 같이 포함되어야 한다
- 이러한 조건들을 잘 갖춘 경우 **REST Ful**하다고 말하고 이를 **REST API**라고 부른다


### URI
1. URI(Uniform Resource Identifier)
   - 인터넷에서 특정 자원을 나타내는 주소값
   - 해당 값은 유일하다
   - ex: https://www.foo.co.kr/resource/sample/1
   - response: sample1.pdf, sample2.pdf, sample.doc
2. URL(Uniform Resource Locator)
   - 인터넷 상에서의 자원, 특정 파일이 어디에 위치하는지 식별 하는 주소
   - ex: https://woo.foo.co.kr/sample1.pdf
- URL은 URI의 하위 개념이다.

### URI 설계

URI 설계 원칙 (RFC-3986)
- 슬래시 구분자 (/)는 계층 관계를 나타내는 데 사용한다
  - https://foo.co.kr/vehicles/suv/q6
- URI 마지막 문자로 (/)는 포함하지 않는다
- 하이픈(-)은 URI가독성을 높이는데 사용한다
  - https:/foo.co.kr/vehicles/suv/q-series/6 (o)
- 밑줄(_)은 사용하지 않는다
  - https://foo.co.kr/vehicles/suv/q_series/6 (x)
- URI 경로에는 소문자가 적합하다.
  - https://foo.co.kr/vehicles/suv/q6 (O)
  - https://Foo.co.kr/Vehicles/SUV/Q6 (X)
- 파일 확장자는 URI에 포함하지 않는다
  - https://foo.co.kr/vehicles/suv/q6.jsp
- 프로그래밍 언어에 의존적인 확장자를 사용하지 않는다
  - https://foo.co.kr/vehicles/suv/q6.do
- 구현에 의존적인 경로를 사용하지 않는다
  - https://foo.co.kr/servlet/vehicles/suv/q6
- 세션 ID를 포함하지 않는다
  - https://foo.co.kr/vehicles/suv/q6?session-id=abcdef
- 프로그래밍 언어의 Method명을 이용하지 않는다
  - https://foo.co.kr/vehicles/suv/q6?action=intro
- 명사에 단수형 보다는 복수형을 사용해야 한다. 컬렉션에 대한 표현은 복수로 사용
  - https://foo.co.kr/vehicles/suv/q6
- 컨트롤러 이름으로는 동사나 동사구를 사용한다
  - https://foo.co.kr/vehicles/suv/q6/re-order
- 경로 부분 중 변하는 부분은 유일한 값으로 대체 한다
  - https://foo.co.kr/vehicles/suv/q7/{car-id}/users/{user-id}/release
  - https://foo.co.kr/vehicles/suv/q7/117/users/steve/release
- CRUD 기능을 나타내는것은 URI에 사용하지 않는다
  - GET : https://foo.co.kr/vehicles/q7/delete/{car-id} ( X )
  - DELETE : https://foo.co.kr/vehicles/q7/{car-id} ( O )
- URI Query Parameter 디자인
  - URI 쿼리 부분으로 컬렉션 결과에 대해서 필터링 할 수 있다.
    https://foo.co.kr/vehicles/suv?model=q7
  - URI 쿼리는 컬렉션의 결과를 페이지로 구분하여 나타내는데
    사용한다.
    https://foo.co.kr/vehicles/suv?page=0&size=10&sort=asc
- API에 있어서 서브 도메인은 일관성 있게 사용해야 한다.
  - https://foo.co.kr
  - https://api.foo.co.kr
- 클라이언트 개발자 포탈 서브 도메인은 일관성 있게 만든다.
  - https://dev-api.foo.co.kr/vehicles/suv/q6
  - https://developer-api.foo.co.kr/vehicles/suv/q6

### HTTP
1. HTTP (Hyper Text Transfer Protocol) 로 RFC 2616에서 규정된 Web에서 데이터를 주고 받는 프로토콜
2. 이름에는 하이퍼텍스트 전송용 프로토콜로 정의되어 있지만 실제로는 HTML, XML, JSON, Image, Voice, Video, Javascript, PDF 등 다양한 컴퓨터에서 다룰 수 있는 것은 모두 전송 할 수 있다 
3. HTTP는 TCP를 기반으로한 REST의 특징을 모두 구현하고있는 Web기반의 프로토콜

- HTTP는 메시지를 주고(Request) 받는(Response)의 형태의 통신 방식이다.


### HTTP Method
HTTP의 요청을 특정하는 Method는 8가지가 있다.  
REST를 구현하기 위한 인터페이스이니 알아둬야 한다.

![image](https://user-images.githubusercontent.com/28394879/146872853-5f43a393-45e8-4cf3-b5e2-a58bffdd5859.png)

### HTTP Status Code
- 응답의 상태를 나타내는 코드
![image](https://user-images.githubusercontent.com/28394879/146873042-0ae4737a-47db-4239-a1a4-99fa37ffc393.png)

- 자주 사용되는 Code
  ![image](https://user-images.githubusercontent.com/28394879/146873135-6bf089bf-caed-4da4-ac77-9152b9065074.png)

</details>