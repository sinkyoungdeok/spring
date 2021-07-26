# 1. 웹 어플리케이션 이해

## A. 웹 서버, 웹 애플리케이션 서버

### 모든 것이 HTTP
- HTTP 메시지에 모든 것을 전송
    - HTML, TEXT
    - IMAGE, 음성, 영상, 파일
    - JSON, XML (API)
    - 거의 모든 형태의 데이터 전송 가능
    - 서버간에 데이터를 주고 받을 때도 대부분 HTTP 사용
    - 지금은 HTTP 시대!
    
### 웹 서버(Web Server)
- HTTP 기반으로 동작
- 정적 리소스 제공, 기타 부가기능
- 정적(파일): HTML, CSS, JS, 이미지, 영상
- ex) NGINX, APACHE

### 웹 애플리케이션 서버(WAS - Web Application Server)
- HTTP 기반으로 동작
- 웹 서버 기능 포함 + (정적 리소스 제공 가능)
- 웹서버와의 차이점: 프로그램 코드를 실행해서 애플리케이션 로직 수행
    - 동적 HTML, HTTP API(JSON)
    - 서블릿, JSP, 스프링 MVC
- ex) 톰캣(Tomcat) Jetty, Undertow

### 웹 서버, 웹 애플리케이션 서버(WAS) 차이
- 웹 서버는 정적 리소스(파일), WAS는 애플리케이션 로직
- 사실은 둘의 용어도 경계도 모호함
  - 웹 서버도 프로그램을 실행하는 기능을 포함하기도 함
  - 웹 애플리케이션 서버도 웹 서버의 기능을 제공함
- 자바는 서블릿 컨테이너 기능을 제공하면 WAS
  - 서블릿 없이 자바코드를 실행하는 서버 프레임워크도 있음
- WAS는 애플리케이션 코드를 실행하는데 더 특화

### 웹 시스템 구성 - WAS, DB
- WAS, DB 만으로 시스템 구성 가능
- WAS는 정적 리소스, 애플리케이션 로직 모두 제공 가능
- WAS가 너무 많은 역할을 담당, 서버 과부하 우려
- 가장 비싼 애플리케이션 로직이 정적 리소스 때문에 수행이 어려울 수 있음
- WAS 장애시 오류 화면도 노출 불가능

### 웹 시스템 구성 - WEB, WAS, DB
- 정적 리소스는 웹 서버가 처리
- 웹 서버는 애플리케이션 로직 같은 동적인 처리가 필요하면 WAS에 요청을 위임
- WAS는 중요한 애플리케이션 로직 처리 전담
- 효율적인 리소스 관리
  - 정적 리소스가 많이 사용되면 Web 서버 증설 하면 됨
  - 애플리케이션 리소스가 많이 사용되면 WAS 증설 하면 됨
- 정적 리소스만 제공하는 웹 서버는 잘 죽지 않음
- 애플리케이션 로직이 동작하는 WAS 서버는 잘 죽음
- WAS, DB 장애시 WEB 서버가 오류 화면 제공 가능

## B. 서블릿

### 서블릿 
- 서버에서 처리해야하는 업무
  1. 서버 TCP/IP 대기, 소켓 연결
  2. HTTP 요청 메시지를 파싱해서 읽기
  3. POST 방식, /save URL 인지
  4. Content-Type 확인
  5. HTTP 메시지 바디 내용 파싱 ( username, age등의 데이터를 사용할 수 있게 파싱 )
  6. 저장 프로세스 실행
  7. 비즈니스 실행 ( 데이터베이스에 저장 요청 )
  8. HTTP 응답 메시지 생성 시작
    - HTTP 시작 라인 생성
    - Header 생성
    - 메시지 바디에 HTML 생성에서 입력
  9. TCP/IP에 응답 전달, 소켓 종료
- 서블릿은 서버에서 처리해야하는 업무의 7번을 제외한 모든 일을 다 자동으로 지원해준다.
- 특징
  - urlPatterns(/hello)의 URL이 호출되면 서블릿 코드가 실행
  - HTTP 요청 정보를 편리하게 사용할 수 있는 HttpServletRequest
  - HTTP 응답 정보를 편리하게 제공할 수 있는 HttpServletResponse
  - 개발자는 HTTP 스펙을 매우 편리하게 사용 
- HTTP 요청, 응답 흐름
  - HTTP 요청
  - WAS는 Request, Response 객체를 새로 만들어서 서블릿 객체 호출
  - 개발자는 Request 객체에서 HTTP 요청 정보를 편리하게 꺼내서 사용
  - 개발자는 Response 객체에 HTTP 응답 정보를 편리하게 입력
  - WAS는 Response 객체에 담겨있는 내용으로 HTTP 응답 정보를 생성

### 서블릿 컨테이너
- 톰캣처럼 서블릿을 지원하는 WAS를 서블릿 컨테이너라고 함
- 서블릿 컨테이너는 서블릿 객체를 생성, 초기화, 호출, 종료하는 생명주기 관리
- 서블릿 객체는 싱글톤으로 관리
  - 고객의 요청이 올 때 마다 계속 객체를 생성하는 것은 비효율 ( request, response 객체는 요청마다 새로 만드는게 좋지만 이 서블릿은 비효율적이다.)
  - 최초 로딩 시점에 서블릿 객체를 미리 만들어두고 재활용
  - 모든 고객 요청은 동일한 서블릿 객체 인스턴스에 접근
  - 공유 변수 사용 주의
  - 서블릿 컨테이너 종료시 함께 종료
- JSP도 서블릿으로 변환 되어서 사용
- 동시 요청을 위한 멀티 쓰레드 처리 지원

## C. 동시 요청 - 멀티 쓰레드

### 쓰레드
- 애플리케이션 코드를 하나하나 순차적으로 실행하는 것은 쓰레드
- 자바 메인 메서드를 처음 실행하면 main이라는 이름의 쓰레드가 실행
- 쓰레드가 없다면 자바 애플리케이션 실행이 불가능
- 쓰레드는 한번에 하나의 코드 라인만 수행
- 동시 처리가 필요하면 쓰레드를 추가로 생성

### 요청 마다 쓰레드 생성 장단점
- 장점
  - 동시 요청을 처리할 수 있다.
  - 리소스(CPU, 메모리)가 허용할 때 까지 처리가능
  - 하나의 쓰레드가 지연 되어도, 나머지 쓰레드는 정상 동작한다.
- 단점
  - 쓰래드는 생성 비용이 매우 비싸다.
    - 고객의 요청이 올때 마다 쓰레드를 생성하면, 응답 속도가 늦어진다.
  - 쓰레드는 컨텍스트 스위칭 비용이 발생한다. ( 코어가 1개이고, 쓰레드가 2개일떄 너무 빨라서 동시에 처리 하는 것이다, 실제로는 1개의 코어가 1개의 쓰레드를 마치고나면 다음 쓰레드를 처리하는것인데 다음쓰레드로 변경할때 일어나는 것을 컨텍스트 스위칭 이라고 한)
  - 쓰레드 생성에 제한이 없다
    - 고객 요청이 너무 많으면, CPU, 메모리 임계점을 넘어서 서버가 죽을 수 있다.
  

### 쓰레드 풀
- 요청 마다 쓰레드 생성의 단점을 보완 했다.
- 특징
  - 필요한 쓰레드를 쓰레드 풀에 보관하고 관리한다.
  - 쓰레드 풀에 생성 가능한 쓰레드의 최대치를 관리한다. 톰캣은 최대 200개 기본 설정 (변경 가능)
- 사용
  - 쓰레드가 필요하면, 이미 생성되어 있는 쓰레드를 쓰레드 풀에서 꺼내서 사용한다.
  - 사용을 종료하면 쓰레드 풀에 해당 쓰레드를 반납한다.
  - 최대 쓰레드가 모두 사용중이어서 쓰레드 풀에 쓰레드가 없으면?
    - 기다리는 요청은 거절하거나 특정 숫자만큼만 대기하도록 설정할 수 있다.
- 장점
  - 쓰레드가 미리 생성되어 있으므로, 쓰레드를 생성하고 종료하는 비용(CPU)이 절약되고, 응답 시간이 빠르다.
  - 생성 가능한 쓰레드의 최대치가 있으므로 너무 많은 요청이 들어와도 기존 요청은 안전하게 처리할 수 있다.
- 실무 팁
  - WAS의 주요 튜닝 포인트는 최대 쓰레드(max thread)수 이다.
  - 이 값을 너무 낮게 설정하면?
    - 동시 요청이 많으면, 서버 리소스는 여유롭지만, 클라이언트는 금방 응답 지연
  - 이 값을 너무 높게 설정하면?
    - 동시 요청이 많으면, CPU, 메모리 리소스 임계점 초과로 서버 다운
  - 장애 발생시?
    - 클라우드면 일단 서버부터 늘리고, 이후에 튜닝
    - 클라우드가 아니면 열심히 튜닝
- 쓰레드 풀의 적정 숫자
  - 적정 숫자는 어떻게 찾나요? 애플리케이션 로직의 복잡도, CPU, 메모리, IO 리소스 상황에 따라 모두 다름
  - 성능 테스트
    - 최대한 실제 서비스와 유사하게 성능 테스트 시도
    - 툴: 아파치 ab, 제이미터, nGrinder

### WAS의 멀티 쓰레드 지원 (핵심)
- 멀티 쓰레드에 대한 부분은 WAS가 처리
- 개발자가 멀티 쓰레드 관련 코드를 신경쓰지 않아도 됨
- 개발자는 마치 싱글 쓰레드 프로그래밍을 하듯이 편리하게 소스 코드를 개발
- 멀티 쓰레드 환경이므로 싱글톤 객체(서블릿, 스프링 빈)는 주의해서 사용


## D. HTML, HTTP API, CSR, SSR

### 정적 리소스
- 고정된 HTML 파일, CSS, JS, 이미지, 영상 등을 제공
- 주로 웹 브라우저

### HTTP API
- HTML이 아니라 데이터를 전달
- 주로 JSON 형식 사용
- 다양한 시스템에서 호출
- 데이터만 주고 받음, UI 화면이 필요하면, 클라이언트가 별도 처리
- 앱, 웹 클라이언트, 서버 to 서버
- 다양한 시스템 연동
  - 주로 JSON 형태로 데이터 통신
  - UI 클라이언트 접점
    - 앱 클라이언트(아이폰, 안드로이드, PC 앱)
    - 웹 브라우저에서 자바스크립트를 통한 HTTP API 호출
    - React, Vue.js 같은 웹 클라이언트
  - 서버 to 서버
    - 주문 서버 -> 결제 서버
    - 기업간 데이터 통신

### SSR(서버 사이드 렌더링), CSR(클라이언트 사이드 렌더링)
- SSR
  - HTMl 최종 결과를 서버에서 만들어서 웹 브라우저에 전달
  - 주로 정적인 화면에 사용
  - 관련기술: JSP, 타임리프 -> 백엔드 개발자
- CSR
  - HTML 결과를 자바스크립트를 사용해 웹 브라우저에서 동적으로 생성해서 적용
  - 주로 동적인 화면에 사용, 웹 환경을 마치 앱 처럼 필요한 부분부분 변경할 수 있음
  - ex) 구글 지도, Gmail, 구글 캘린더
  - 관련기술: React, Vue.js -> 웹 프론트엔드 개발자
- 참고
  - React, Vue.js를 CSR+SSR 동시에 지원하는 웹 프레밍워크도 있음
  - SSR를 사용하더라도, 자바스크립트를 사용해서 화면 일부를 동적으로 변경 가능

### 어디까지 알아야 하나? 백엔드 개발자 입장에서 UI 기술
- 백엔드 - 서버 사이드 렌더링 기술
  - JSP, 타임리프
  - 화면이 정적이고, 복잡하지 않을 때 사용
  - 백엔드 개발자는 서버 사이드 렌더링 기술 학습 필수
- 웹 프론트 엔드 - 클라이언트 사이드 렌더링 기술
  - React, Vue.js
  - 복잡하고 동적인 UI 사용
  - 웹 프론트엔드 개발자의 전문 분야
- 선택과 집중
  - 백엔드 개발자의 웹 프론트엔드 기술 학습은 옵션
  - 백엔드 개발자는 서버, DB, 인프라 등등 수 많은 백엔드 기술을 공부해야 한다.
  - 웹 프론트엔드도 깊이있게 잘 하려면 숙련에 오랜 시간이 필요하다.
  

## E. 자바 백엔드 웹 기술 역사

### 과거 기술
- 서블릿 - 1997
  - HTML 생성이 어려움
- JSP - 1999
  - HTML 생성은 편리하지만, 비즈니스 로직까지 너무 많은 역할 담당
- 서블릿, JSP 조합 MVC 패턴 사용
  - 모델, 뷰, 컨트롤러로 역할을 나누어 개발
- MVC 프레임워크 춘추 전국 시대 - 2000년 초 ~ 2010년 초
  - MVC 패턴 자동화, 복잡한 웹 기술을 편리하게 사용할 수 있는 다양한 기능 지원
  - 스트럿츠, 웹워크, 스프링 MVC(과거 버전)
### 현재 사용 기술
- 애노테이션 기반의 스프링 MVC 등장
  - @Controller
  - MVC 르에미워크의 춘추 전국 시대 마무리
- 스프링 부트의 등장
  - 스프링 부트는 서버를 내장
  - 과거에는 서버에 WAS를 직접 설치하고, 소스는 War 파일을 만들어서 설치한 WAS에 배포
  - 스프링 부트는 빌드 결과(Jar)에 WAS 서버 포함 -> 빌드 배포 단순화
  
### 최신 기술 - 스프링 웹 기술의 변화
- Web Servlet - Spring MVC
- Web Reactive - Spring WebFlux

### 최신 기술 - 스프링 웹 플럭스(WebFlux)
- 특징
  - 비동기 넌 블러킹 처리
  - 최소 쓰레드로 최대 성능 - 쓰레드 컨텍스트 스위칭 비용 효율화
  - 함수형 스타일로 개발 - 동시처리 코드 효율화
  - 서블릿 기술 사용X
- 그런데
  - 웹 플럭스는 기술적 난이도 매우 높음
  - 아직은 RDB 지원 부족
  - 일반 MVC의 쓰레드 모델도 충분히 빠르다.
  - 실무에서 아직 많이 사용하지는 않음 (전체 1% 이하)

### 자바 뷰 템를릿 역사 (HTML을 편리하게 생성하는 뷰 기능)
- JSP
  - 속도 느림, 기능 부족
- 프리마커(Freemarker), Velocity(벨로시티)
  - 속도 문제 해결, 다양한 기능
- 타임리프(Thymeleaf)
  - 내추럴 템플릿: HTML의 모양을 유지하면서 뷰 템플릿 적용가능
  - 스프링 MVC와 강력한 기능 통합
  - 최선의 선택, 단 성능은 프리마커, 벨로시티가 더 빠름

# 2. 서블릿
## A. Hello 서블릿
- 참고: 서블릿은 톰캣 같은 웹 애플리케이션 서버를 직접 설치하고, 그 위에 서블릿 코드를 클래스 파일로
빌드해서 올린 다음, 톰캣 서버를 실행하면 된다. 하지만 이 과정은 매우 번거롭기떄문에 스프링 부트로 진행.
스프링부트는 톰캣 서버를 내장하고 있으므로, 톰캣 서버 설치 없이 편리하게 서블릿 코드를 실행할 수 있다.
  
### 스프링 부트 서블릿 환경 구성
- `@ServletComponentScan`: 스프링 부트는 서블릿을 직접 등록해서 사용할 수 있도록 `@ServletComponentScan`을 지원한다. 
- `@WebServlet` 서블릿 애노테이션
  - name: 서블릿 이름
  - urlPatterns: URL 매핑
  - HTTP 요청을 통해 매핑된 URL이 호출되면 서블릿 컨테이너는 service 메서드를 실행한다.
  
### HTTP 요청 메시지 로그로 확인하기
- application.properties에 logging.level.org.apache.coyote.http11=debug를 추가하면 됨
- 참고: 운영서버에 이렇게 모든 요청 정보를 다 남기면 성능저하가 발생할 수 있다. 개발 단계에서만 적용하자.

## B. HttpServletRequest - 개요

### HttpServletRequest 역할

- HTTP 요청 메시지를 개발자가 직접 파싱해서 사용해도 되지만, 매우 불편할 것이다.
서블릿은 개발자가 HTTP 요청 메시지를 편리하게 사용할 수 있도록 개발자 대신에 HTTP 요청 메시지를 파싱 한다.
그리고 그 결과를 `HttpServletRequest`객체에 담아서 제공한다.

- HTTP 요청 메시지
  - START LINE
    - HTTP 메소드
    - URL
    - 쿼리 스트링
    - 스키마, 프로토콜
  - 헤더
    -헤더 조회
  - 바디
    - form 파라미터 형식 조회
    - message body 데이터 직접 조회
  - HttpservletRequest 객체는 추가로 여러가지 부가기능도 함께 제공한다.

- 임시 저장소 기능
  - 해당 HTTP 요청이 시작부터 끝날 때 까지 유지되는 임시 저장소 기능
    - 저장: `request.setAttribute(name,value)`
    - 조회: `request.getAttribute(name)`

- 세션 관리 기능
  - `request.getSession(create: true)`
  
- 중요
  - HttpservletRequest, HttpservletResponse를 사용할 때 가장 중요한 점은 이 객체들이
  HTTP 요청 메시지, HTTP 응답 메시지를 편리하게 사용하도록 도와주는 객체라는 점이다. 따라서
  이 기능에 대해서 깊이있는 이해를 하려면 "HTTP 스펙이 제공하는 요청, 응답 메시지 자체를 이해" 해야 한다.
    
## C. HttpServletRequest - 기본 사용법
- 참고: 로컬에서 테스트하면 IPv6 정보가 나오는데, IPv4 정보를 보고 싶으면 다음 옵션을 VM options에 넣어주면 된다. (`-Djava.net.preferIPv4Stack=true`)

## D. HTTP 요청 데이터 - 개요
- HTTP 요청 메시지를 통해 클라이언트에서 서버로 데이터를 전달하는 방법을 알아보자.
- 주로 다음 3가지 방법을 사용한다.
- GET - 쿼리 파라미터
  - /url?username=hello&age=20
  - 메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달
  - ex) 검색, 필터, 페이징등에서 많이 사용하는 방식
- POST - HTML Form
  - content-type: application/x-www-form-urlencoded
  - 메시지 바디에 쿼리 파라미터 형식으로 전달 username=hello&age=20
  - ex) 회원 가입, 상품 주문, HTML Form 사용
- HTTP message body에 데이터를 직접 담아서 요청
  - HTTP API에서 주로 사용, JSON, XML, TEXT
  - 데이터 형식은 주로 JSON 사용
  - POST, PUT, PATCH

## E. HTTP 요청 데이터 - GET 쿼리 파라미터
- 다음 데이터를 클라이언트에서 서버로 전송해보자.
- 전달 데이터
  - username=hello
  - age=20
- 메시지 바디 없이, URL의 "쿼리 파라미터"를 사용해서 데이터를 전달하자.
ex) 검색, 필터, 페이징 등에서 많이 사용하는 방식
- 쿼리 파라미터는 URL에 `?`를 시작으로 보낼 수 있다. 추가 파라미터는 `&`로 구분하면 된다.
- 서버에서는 `HttpServletRequest`가 제공하는 다음 메서르르 통해 쿼리 파라미터를 편리하게 조회할 수 있다.

- 복수 파라미터에서 단일 파라미터 조회
  - `username=hello&username=kim`과 같이 파라미터 이름은 하나인데, 값이 중복이면 어떻게 될까?
  - `request.getParameter()`는 하나의 파라미터 이름에 대해서 단 하나의 값만 있을 때 사용해야 한다. 지금 처럼 중복일 때는 
  `request.getParameterValues()`를 사용해야 한다.
  - 참고로 이렇게 중복일 때 `request.getParameter()`를 사용하면 `request.getParameterValues()`의 첫 번째 값을 반환한다.
  
## F. HTTP 요청 데이터 - POST HTML Form
- 이번에는 HTML의 Form을 사용해서 클라이언트에서 서버로 데이터를 전송해보자.
주로 회원 가입, 상품 주문 등에서 사용하는 방식이다.
- 특징
  - content-type: `application/x-www-form-urlencoded`
  - 메시지 바디에 쿼리 파라미터 형식으로 데이터를 전달한다. `username=hello&age=20`
  
- `request.gtParameter()`는 GET URL 쿼리 파라미터 형식도 지원하고, POST HTML Form 형식도 둘 다 지원한다.
- 참고
  - content-type은 HTTP 메시지 바디의 데이터 형식을 지정한다.
  - GET URL 쿼리 파라미터 형식으로 클라이언트에서 서버로 데이터를 전달할 때는 HTTP 메시지 바디를 사용하지 않기 떄문에 content-type이 없다.
  - POST HTML Form 형식 으로 데이터를 전달하면 HTTP 메시디 바디에 해당 데이터를 포함해서 보내기 떄문에 바디에 포함된 데이터가 어떤 형식인지
  content-type을 꼭 지정해야 한다. 이렇게 폼으로 데이터를 전송하는 방식을 `application/x-www-form-urlencoded`라 한다.
- Postman을 사용한 테스트
  - 이런 간단한 테스트에 HTML form을 만들기는 귀찮다. 이때는 Postman을 사용하면 된다.
- Postman 테스트 주의사항
  - POST 전송시
    - Body -> `x-www-form-urlencoded` 선택
    - Headers에서 content-type: `application/x-www-form-urlencoded`로 지정된 부분 꼭 확인
  
## G. HTTP 요청 데이터 - API 메시지 바디 - 단순 텍스트
- HTTP message body에 데이터를 직접 담아서 요청
  - HTTP API에서 주로 사용, JSON, XML, TEXT
  - 데이터 형식은 주로 JSON 사용
  - POST, PUT, PATCH
- 먼저 가장 단순한 텍스트 메시지를 HTTP 메시지 바디에 담아서 전송하고, 읽어보자.
- HTTP 메시지 바디의 데이터를 InputStream을 사용해서 직접 읽을 수 있다.
- 참고
  - inputStream은 byte 코드를 반환한다. byte코드를 우리가 읽을 수 있는 문자(String)로 보려면 문자표(Charset)를 지정해주어야 한다.
  여기서는 UTF_8 Charset을 지정해주었다.
- 문자전송
  - POST http://localhost:8080/request-body-string
  - content-type: text/plain
  - message body: `hello`
  - 결과: `messageBody = hello`

## H. HTTP 요청 데이터 - JSON
- 이번에는 HTTP API에서 주로 사용하는 JSON 형식으로 데이터를 전달해보자.
- JSON 형식 전송
  - POST http://localhost:8080/request-body-json
  - content-type: application/json
  - messagebody: `{"username": "hello", "age":20 }`
  - 결과: `messageBody = {"username": "hello", "age":20}`

- 참고
  - JSON 결과를 파싱해서 사용할 수 있는 자바 객체로 변환하려면 Jackson, Gson 같은 JSON 변환 라이브러리를 추가해서 사용해야 한다.
  스프링 부트로 Spring MVC를 선택하면 기본으로 Jackson 라이브러리 (`ObjectMapper`)를 함께 제공한다.
  - HTML form 데이터도 메시지 바디를 통해 전송되므로 직접 읽을 수 있다. 하지만 편리한 파라미터 조회 기능
    (`request.getParameter(...)`)을 이미 제공하기 때문에 파라미터 조회 기능을 사용하면 된다.
    
## I. HttpServletResponse - 기본 사용법
### HttpServletResponse 역핧
- HTTP 응답 메시지 생성
  - HTTP 응답코드 지정
  - 헤더 생성
  - 바디 생성
- 편의 기능 제공
  - Content-Type, 쿠키, Redirect

## J. HTTP 응답 데이터 - 단순 텍스트, HTML
- HTTP 응답 메시지는 주로 다음 내용을 담아서 전달한다.
- 단순 텍스트 응답
  - 앞에서 살펴봄 (`writer.println("ok");`)
  - HTML 응답
  - HTTP API - MessageBody JSON 응답
  
### HttpServletResponse - HTML 응답
- HTTP 응답으로 HTML을 반환할 때는 content-type을 `text/html`로 지정해야 한다.

## K. HTTP 응답 데이터 - API JSON
- HTTP 응답으로 JSON을 반환할 때는 content-type을 `application/json`로 지정해야 한다.
- Jackson 라이브러리가 제공하는 `objectMapper.writerValueAsString()`를 사용하면 객체를 JSON 문자로 변경할 수 있다.
- 참고
  - `application/json`은 스펙상 utf-8 형식을 사용하도록 정의되어 있다. 그래서 
  스펙에서 charset=utf-8과 같은 추가 파라미터를 지원하지 않는다. 따라서 `application/json` 
  이라고만 사용해야지 `application/json;charset=utf-8` 이라고 전달하는 것은
  의미 없는 파라미터를 추가한 것이 된다. 
  - response.getWriter()를 사용하면 추가 파라미터를 자동으로 추가해버린다. 이때는 
  response.getOutputStream()으로 출력하면 그런 문제가 없다.

# 3. 서블릿, JSP, MVC 패턴
## A. 회원 관리 웹 애플리케이션 요구사항
- 회원 정보
  - 이름: `username`
  - 나이: `age`
- 기능 요구사항
  - 회원 저장
  - 회원 목록 조회
  
## B. 서블릿으로 회원 관리 웹 애플리케이션 만들기
- 템플릿 엔진으로
  - 지금까지 서블릿과 자바 코드만으로 HTML을 만들어 보았다. 서블릿 덕분에 동적으로 원하는 HTML을 마음껏 만들 수 있다.
  정적인 HTML문서라면 화면이 계속 달라지는 회원의 저장 결과라던가, 회원 목록 같은 동적인 HTML을 만드는 일은 불가능 할것이다.
  - 그런데, 코드에서 보듯이 이것은 매우 복잡하고 비효율 적이다. 자바 코드로 HTML을 만들어 내는 것 보다 차라리 HTML 문서에
  동적으로 변경해야 하는 부분만 자바 코드를 넣을 수 있다면 더 편리할 것이다.
  - 이것이 바로 템플릿 엔진이 나온 이유이다. 템플릿 엔진을 사용하면 HTML 문서에서 필요한 곳만 코드를 적용해서 동적으로 변경할 수 있다.
  - 템플릿 엔진에는 JSP, Thymeleaf, Freemaker, Velocity등이 있다.
- 참고
  - JSP는 성능과 기능면에서 다른 템플릿 엔진과의 경쟁에서 밀리면서, 점점 사장되어 가는 추세이다. 템플릿 엔진들은 각각 장단점이 있는데,
  JSP는 잠깐 다루고, 스프링과 잘 통합되는 Thymeleaf를 사용한다.
  

## C. JSP로 회원 관리 웹 애플리케이션 만들기
### JSP 라이브러리 추가
- JSP를 사용하려면 먼저 다음 라이브러리를 추가해야 한다.
  - //JSP 추가 시작
    implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'
    implementation 'javax.servlet:jstl'
    //JSP 추가 끝
- 회원 등록 폼 JSP
  - `<%@ page contentType="text/html;charset=UTF-8" language="java" %>`
    - 첫줄은 JSP 문서라는 뜻이다. JSP 문서는 이렇게 시작해야 한다.
  - 회원 등록폼 JSP를 보면 첫 줄을 제외하고는 완전히 HTML와 똑같다. JSP는 서버 내부에서 서블릿으로 변환되는데, 우리가 만들었던 MemberFormServlet과 거의 비슷한 모습으로 변환된다.
- JSP는 자바 코드를 그대로 다 사용할 수 있다.
  - `<%@ page import="hello.servlet.domain.member.MemberRepository" %>` 
    - 자바의 import 문과 같다.
  - `<% ~~ %>`
    - 이 부분에는 자바 코드를 입력할 수 있다.
  - `<%= ~~ %>`
    - 이 부분에는 자바코드를 html로 출력할 때 사용 
  - 회원 저장 JSP를 보면, 회원 저장 서블릿 코드와 같다. 다른점이 있다면, HTML을 중심으로 하고, 자바 코드를 부분 부분 입력해주었다. `<% ~ %>`를
  사용해서 HTML 중간에 자바 코드를 출력하고 있다.
### 서블릿과 JSP의 한계
- 서블릿으로 개발할 때는 뷰(View)화면을 위한 HTML을 만드는 작업이 자바 코드에서 섞여서 지저분하고 복잡했다.
- JSP를 사용한 덕분에 뷰를 생성하는 HTML 작업을 깔끔하게 가져가고, 중간중간 동적으로 변경이 필요한 부분에만 자바 코드를 적용했다. 그런데 이렇게 해도
해결되지 않는 몇가지 고민이 남는다.
- 회원 저장 JSP를 보자. 코드의 상위 절반은 회원을 저장하기 위한 비즈니스 로직이고, 나머지 하위 절반만 결과를 HTML로 보여주기 위한 뷰 영역이다. 
회원 목록의 경우에도 마찬가지다.
- 코드를 잘 보면, JAVA 코드, 데이터를 조회하는 리포지토리 등등 다양한 코드가 모두 JSP에 노출되어 있다. JSP가 너무 많은 역할을 한다.
이렇게 작은 프로젝트도 벌써 머리가 아파오는데, 수백 수천줄이 넘어가는 JSP를 떠올려보면 정말 지옥과 같을 것이다.

- MVC 패턴의 등장
  - 비즈니스 로직은 서블릿 처럼 다른곳에서 하고, JSP는 목적에 맞게 HTML로 화면(View)을 그리는 일에 집중하도록 하자. 
  과거 개발자들도 모두 비슷한 고민이 있었고 ,그래서 MVC 패턴이 등장했다. 우리도 직접 MVC패턴을 적용해서 프로젝트를 리팩터링 해보자.

## D. MVC 패턴 - 개요
- 너무 많은 역할
  - 하나의 서블릿이나 JSP만으로 비즈니스 로직과 뷰 렌더링까지 모두 처리하게 되면, 너무 많은 역할을 하게 되고, 결과적으로 유지보수가 어려워진다.
  비즈니스 로직을 호출하는 부분에 변경이 발생해도 해당 코드를 손대야 하고, UI를 변경할 일이 있어도 비즈니스 로직이 함께 있는 
  해당 파일을 수정해야 한다. HTML 코드 하나 수정해야 하는데, 수백줄의 자바 코드가 함께 있다고 상상해봐라! 또는 비즈니스
  로직을 하나 수정해야 하는데 수백 수천줄의 HTML코드가 함께 있다고 상상해봐라.
- 변경의 라이프 사이클
  - 사실 이게 정말 중요한데, 진짜 문제는 둘 사이에 변경의 라이프 사이클이 다르다는 점이다. 예를 들어서 UI를 일부 수정하는 일과 
  비즈니스 로직을 수정하는 일은 각각 다르게 발생할 가능성이 매우 높고 대부분 서로에게 영향을 주지 않는다. 이렇게 변경의
  라이프 사이클이 다른 부분을 하나의 코드로 관리하는 것은 유지보수하기 좋지 않다(물론 UI가 많이 변하면 함께 변경될 가능성도 있다.)
- 기능 특화
  - 특히 JSP 같은 뷰 템플릿은 화면을 렌더링 하는데 최적화 되어 있기 때문에 이부분의 업무만 담당하는 것이 가장 효과적이다.
- Model View Controller
  - MVC 패턴은 지금까지 학습한 것 처럼 하나의 서블릿이나, JSP로 처리하는 것을 컨트롤러(Controller)와 뷰(View)라는
  영역으로 서로 역할을 나눈 것을 말한다. 웹 애플리케이션은 보통 이 MVC패턴을 사용한다.
  - 컨트롤러: HTTP요청을 받아서 파라미터를 검증하고, 비즈니스 로직을 실행한다. 그리고 뷰에 전달할 결과 데이터를 조회해서 모델에 담는다.
  - 모델: 뷰에 출력할 데이터를 담아둔다. 뷰가 필요한 데이터를 모두 모델에 담아서 전달해주는 덕분에 뷰는 비즈니스 로직이나 데이터 접근을 몰라도 되고,
  화면을 렌더링 하는 일에 집중할 수 있다.
  - 뷰: 모델에 담겨있는 데이터를 사용해서 화면을 그리는 일에 집중한다. 여기서는 HTML을 생성하는 부분을 말한다.
- 참고
  - 컨트롤러에 비즈니스 로직을 둘 수도 있지만, 이렇게 되면 컨트롤러가 너무 많은 역할을 담당한다. 그래서 일반적으로 비즈니스
  로직은 서비스(Service)라는 계층을 별도로 만들어서 처리한다. 그리고 컨틀롤러는 비즈니스 로직이 있는 서비스를 호출하는
  담당한다. 참고로 비즈니스 로직을 변경하면 비즈니스 로직을 호출하는 컨트롤러의 코드도 변경할 수 있다. 앞에서는
  이해를 돕기 위해 비즈니스 로직을 호출한다는 표현 보다는, 비즈니스 로직이라 설명했다.

## E. MVC 패턴 - 적용
- 서블릿을 컨트롤러로 사용하고, JSP를 뷰로 사용해서 MVC 패턴을 적용한다.
- Model은 HttpServletRequest 객체를 사용 한다. request는 내부에 데이터 저장소를 가지고 있는데,
`request.setAttribute()`,`request.getAttribute()`를 사용하면 데이터를 보관하고, 조회할 수 있다.
- `dispatcher.forwrad()`: 다른 서블릿이나 JSP로 이동할 수 있는 기능이다. 서부 내부에서 다시 호출이 발생한다.
- `/WEB-INF`: 이 경로안에 JSP가 있으면 외부에서 직접 JSP를 호출 할 수 없다. 우리가 기대하는 것은 항상 컨트롤러를 통해서 
JSP를 호출하는 것이다.
- redirect vs forward
  - 리다이렉트는 실제 클라이언트(웹 브라우저)에 응답이 나갔다가, 클라이언트가 redirect 경로로 다시 요청한다.
  따라서 클라이언트가 인지할 수 있고, URL 경로도 실제로 변경된다. 반면에 포워드는 서버 내부에서 일어나는 호출하기 떄문에 
  클라이언트가 전혀 인지하지 못한다.
  - redirect
    1. 고객이 고객센터로 상담원에게 123번으로 전화를 건다.
    2. 상담원은 고객에게 다음과 같이 이야기한다. "고객님 해당 문의사항은 124번으로 다시 문의 해주시겠어요?"
    3. 고객은 다시 124번으로 문의해서 일을 처리한다.
  - forward
    1. 고객이 고객센터로 상담원에게 123번으로 전화를 건다.
    2. 상담원은 해당 문의사항에 대해 잘 알지 못하고 옆의 다른 상담원에게 해당 문의사항에 답을 얻는다.
    3. 상담원은 고객에게 문의사항을 처리해준다.
  
- `<%= request.getAttribute("member")` 로 모델에 저장한 member 객체를 꺼낼 수 있지만, 너무 복잡해진다.
JSP는 `${}`문법을 제공하는데, 이 문법을 사용하면 request의 attribute에 담긴 데이터를 편리하게 조회할 수 있다.

- 모델에 담아둔 members를 JSP가 제공하는 taglib기능을 사용해서 반복하면서 출력 할 수 있다. `members`리스트에서
`member`를 순서대로 꺼내서 `item`변수에 담고, 출력하는 과정을 반복한다.
- `<c:forEach>` 이 기능을 사용하려면 다음과 같이 선언해야 한다. 
  - `<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>`
- JSP는 잘 사용하고 있지 않기 떄문에 깊게 공부할 필요는 없다.
## F. MVC 패턴 - 한계
- MVC 패턴을 적용한 덕분에 컨트롤러의 역할과 뷰를 렌더링 하는 역할을 명확하게 구분할 수 있다.
- 특히 뷰는 화면을 그리는 역할에 충실한 덕분에, 코드가 깔끔하고 직관적이다. 단순하게 모델에서 필요한
데이터를 꺼내고, 화면을 만들면 된다. 그런데 컨트롤러는 딱 봐도 중복이 많고, 필요하지 않는 코드들도 많이 보인다.

- MVC 컨트롤러의 단점
  - 포워드 중복
    - View로 이동하는 코드가 항상 중복 호출되어야 한다. 물론 이 부분을 메서드로 공통화해도 되지만, 해당 메서드도 항상 직접 호출해야 한다.
  - ViewPath의 중복
    - prefix: `WEB-INF/views/`
    - subffix: `.jsp`
    - 그리고 만약 jsp가 아닌 thymeleaf같은 다른 뷰로 변경한다면 전체 코드를 다 변경해야 한다.
  - 사용하지 않는 코드
    - HttpServletRequest request, HttpServletResponse response 코드를 사용할 때도 있고, 사용하지 않을 떄도 있다.
    특히 response는 현재코드에서 사용되지 않는다. 그리고 이 코드로 테스트 케이스를 작성하기도 어렵다.
  - 공통 처리가 어렵다.
    - 기능이 복잡해질수록 컨트롤러에서 공통으로 처리해야 하는 부분이 점점 더 많이 증가할 것이다. 단순히 
    공통 기능을 메서드로 뽑으면 될 것 같지만, 결과적으로 해당 메서드를 항상 호출해야 하고, 실수로 호출하지 않으면
    문제가 될 것이다. 그리고 호출하는 것 자체도 중복이다.
  - 정리하면 공통 처리가 어렵다는 문제가 있다.
    - 이 문제를 해결하려면 컨트롤러 호출 전에 먼저 공통 기능을 처리해야 한다. 소위 "수문장 역할"을 하는 기능이 필요하다.
    프론트 컨트롤러(Front Controller)패턴을 도입하면 이런 문제를 깔끔하게 해결할 수 있다. (입구를 하나로!)
    스프링 MVC의 핵심이 바로 이 프론트 컨트롤러에 있다.
      
# 4. MVC 프레임워크 만들기
## A. 프론트 컨트롤러 패턴 소개
- FrontController 패턴 특징
  - 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받음
  - 프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아서 호출
  - 입구를 하나로!
  - 공통 처리 가능
  - 프론트 컨트롤러를 제외한 나머지 컨트롤러는 서블릿을 사용하지 않아도 됨 
- 스프링 웹 MVC와 프론트 컨트롤러
  - 스프링 웹 MVC의 핵심도 바로 "FrontController"
  - 스프릉 웹 MVC의 "DispatcherServlet"이 FrontController 패턴으로 구현되어 있음

## B. 프론트 컨트롤러 도입 - v1
## C. View 분리 - v2
- 모든 컨트롤러에서 뷰로 이동하는 부분에 중복이 있고, 깔끔하지 않다.
- 이 부분을 깔끔하게 분리하기 위해 별도로 뷰를 처리하는 객체를 만들자.
## D. Model 추가 - v3
- 서블릿 종속성 제거 
  - 컨트롤러 입장에서 HttpServletRequest, HttpServletResponse가 꼭 필요할까?
  - 요청 파라미터 정보는 자바의 Map으로 대신 넘기도록 하면 지금 구조에서는 컨트롤러가 서블릿 기술을 몰라도 동작할 수 있다.
  - 그리고 request 객체를 Model로 사용하는 대신에 별도의 Model 객체를 만들어서 반환하면 된다.
  - 우리가 구현하는 컨트롤러 서블릿 기술을 전혀 사용하지 않도록 변경해보자.
  - 이렇게 하면 구현 코드도 매우 단순해지고, 테스트 코드 작성이 쉽다.
- 뷰 이름 중복 제거
  - 컨트롤러에서 지정하는 뷰 이름에 중복이 있는 것을 확인할 수 있다.
  - 컨트롤러는 "뷰의 논리 이름"을 반환하고, 실제 물리 위치의 이름은 프론트 
  컨트롤러에서 처리하도록 단순화 하자.
  - 이렇게 해두면 향후 뷰의 폴더 위치가 함께 이동해도 프론트 컨트롤러만 고치면 된다.
  - `/WEB-INF/views/new-form.jsp` -> new-form
  - `/WEB-INF/views/save-result.jsp` -> save-result
  - `/WEB-INF/views/members.jsp` -> members
  
- ModelView
  - 지금까지 컨트롤러에서 서블릿에 종속적인 HttpSeRvletRequest를 사용했다. 그리고 Model도
  `request.setAttribute()`를 통해 데이터를 저장하고 뷰에 전달 했다.
  서블릿의 종속성을 제거하기 위해 Model을 직접 만들고, 추가로 View 이름까지 전달하는 객체를 만들어보자.
  (이번 버전에서는 컨트롤러에서 HttpServletRequest를 사용할 수 없다. 따라서 직접 `request.setAttribute()`를 호출 할 수 도 없다.
  따라서 Model이 별도로 필요하다.)
## E. 단순하고 실용적인 컨트롤러 - v4
- 앞서 만든 v3 컨트롤러는 서블릿 종속성을 제거하고 뷰 경로의 중복을 제거하는 등, 잘 설계된 컨트롤러이다.
그런데 실제 컨트롤러 인터페이스를 구현하는 개발자 입장에서 보면, 항상
ModelView 객체를 생성하고 반환해야 하는 부분이 조금은 번거롭다.
좋은 프레임워크는 아키텍처도 중요하지만, 그와 더불어 실제 개발하는
개발자가 단순하고 편리하게 사용할 수 있어야 한다. 소위 실용성이 있어야 한다.
- 기본적인 구조는 V3와 같다. 대신에 컨트롤러가 ModelView를 반환하지 않고, ViewName만 반환한다.
- 이번 버전은 인터페이스에 ModelView가 없다. model 객체는 파라미터로 전달되기 떄문에 그냥 사용하면 되고,
결과로 뷰의 이름만 반환해주면 된다.
  
- 정리
  - 이번 버전의 컨트롤러는 매우 단순하고 실용적이다. 기존 구조에서 모델을 파라미터로 넘기고, 뷰의 놀리 이름을 반환한다는 작은 아이디어를 적용했을 뿐인데,
  컨트롤러를 구현하는 개발자 입장에서 보면 이제 군더더기 없는 코드를 작성할 수 있다.
  또한 중요한 사실은 여기까지 한번에 온 것이 아니라는 점이다. 프레임워크가 점진적으로 발전하는 과정 속에서 
  이런 방법도 찾을 수 있었다.
  - 프레임워크가 공통 기능이 수고로워야 사용하는 개발자가 편리해진다.

## F. 유연한 컨트롤러1 - v5
- 만약 어떤 개발자는 `ControllerV3` 방식으로 개발하고 싶고, 어떤 개발자는 `ControllerV4` 방식으로
개발하고 싶다면 어떻게 해야 될까?
- 어댑터 패턴
  - 지금까지 우리가 개발한 프론트 컨트롤러는 한가지 방식의 컨트롤러 인터페이스만 사용할 수 있다.
  - `ControllerV3`, `ControllerV4`는 완전히 다른 인터페이스이다. 따라서 호환이 불가능하다.
  마치 v3는 110v이고, v4는 220v 전기 콘센트 같은 것이다. 이럴 때 사용하는 것이 바로 어댑터이다.
  어댑터 패턴을 사용해서 프론트 컨트롤러가 다양한 방식의 컨트롤러를 처리할 수 있도록 변경해보자.
- 핸들러 어댑터: 중간에 어댑터 역할을 하는 어댑터가 추가되었는데 이름이 핸들러 어댑터이다. 여기서 어댑터 역할을 해주는 덕분에
다양한 종류의 컨트롤러를 호출할 수 있다.
- 핸들러: 컨트롤러의 이름을 더 넓은 범위인 핸들러로 변경했다. 그 이유는 이제 어댑터가 있기 떄문에 꼭 컨트롤러의 개념 뿐만 아니라
어떠한 것이든 해당하는 종류의 어댑터만 있으면 다 처리할 수 있기 때문이다.

## G. 유연한 컨트롤러2 - v5
- `FrontControllerServletV5`에 `ControllerV4` 기능 추가

## 정리
- v1: 프론트 컨트롤러를 도입
  - 기존 구조를 최대한 유지하면서 프론트 컨트롤러를 도입
- v2: View 분류
  - 단순 반복 되는 뷰 로직 분리
- v3: Model 추가
  - 서블릿 종속성 제거
  - 뷰 이름 중복 제거
- v4: 단순하고 실용적인 컨트롤러
  - v3와 거의 비슷
  - 구현 입장에서 ModelView를 직접 생성해서 반환하지 않도록 편리한 인터페이스 제공
- v5: 유연한 컨트롤러
  - 어댑터 도입
  - 어댑터를 추가해서 프레임워크를 유연하고 확장성 있게 설계

- 여기에 애노테이션을 사용해서 컨트롤러를 더 편리하게 발전시킬 수도 있다.
만약 애노테이션을 사용해서 컨트롤러를 편리하게 사용할 수 있게 하려면
어떻게 해야 할까? 바로 애노테이션을 지원하는 어댑터를 추가하면 된다!
다형성과 어댑터 덕분에 기존 구조를 유지하면서, 프레임워크의 기능을
확장할 수 있다.

- 스프링 MVC
  - 여기서 더 발전시키면 좋겠지만, 스프링 MVC의 핵심 구조를 파악하는데 
  필요한 부분은 모두 만들어 보았다.
  사실은 지금까지 작성한 코드는 스프링 MVC 프레임워크의 핵심 코드의
  축약 버전이고, 구조도 거의 같다.
  - 스프링 MVC에는 지금까지 우리가 학습한 내용과 거의 같은 구조를 가지고 있다.

# 5. 스프링 MVC - 구조 이해
## 스프링 MVC 전체 구조
### 직접 만든 프레임워크 -> 스프링 MVC 비교
- FrontController -> DispatcherServlet
- handlerMappingMap -> HandlerMapping
- MyHandlerAdapter -> HandlerAdapter
- ModelView -> ModelAndView
- viewResolver -> ViewResolver
- MyView -> View

### DispatcherServlet 구조 살펴보기
- `org.springframework.web.servlet.DispatcherServlet`
  - 스프링 MVC도 프론트 컨트롤러 패턴으로 구현되어 있다.
  - 스프링 MVC의 프론트 컨트롤러가 바로 디스패처 서블릿 이다.
  - 그리고 이 디스패쳐 서블릿이 바로 스프링 MVC의 핵심이다.
- DispatcherServlet 서블릿 등록
  - `DispatcherServlet`도 부모 클래스에서 `HttpServlet`을 상속
  받아서 사용하고, 서블릿으로 동작한다.
    - DispatcherServlet -> FramworkServlet -> HttpServletBean -> HttpServlet
  - 스프링 부트는 `DispatcherServlet`을 서블릿으로 자동으로 등록하면서
  모든 경로(`urlPatterhns="/")에 대해서 매핑한다.
    - 참고: 더자세한 경로가 우선순위가 높다. 그래서 기존에 등록한 서블릿도 함께 동작한다.

- 요청 흐름
  - 서블릿이 호출되면 `HttpServlet`이 제공하는 `service()`가 호출된다.
  - 스프링 MVC는 `DispatcherServlet`의 부모인 `FrameworkServlet`에서 
  `service()`를 오버라이드 해두었다.
  - `FrameworkServlet.service()`를 시작으로 여러 메서드가 호출되면서 `DispatcherServlet.doDispatch()`가 호출된다.
  
- 동작 순서
  1. 핸들러 조회: 핸들러 매핑을 통해 요청 URL에 매핑된 핸들러(컨트롤러)를 조회한다.
  2. 핸들러 어댑터 조회: 핸들러를 실행할 수 있는 핸들러 어댑터를 조회한다. 
  3. 핸들럴 어댑터 실행: 핸들러 어댑터를 실행한다.
  4. 핸들러 실행: 핸들러 어댑터가 실제 핸들러를 실핸한다.
  5. ModelAndView 반환: 핸들러 어댑터는 핸들러가 반환하는 정보를 ModelAndView로 "변환"해서 반환한다.
  6. viewResolver 호출: 뷰 리졸벌르 찾고 실행한다.
    - JSP의 경우: `InternalResourceViewResolver`가 자동 등록되고, 사용된다.
  7. View 반환: 뷰 리졸버는 뷰의 논리 이름을 물리 이름으로 바꾸고, 렌더링 역할을 담당한느 뷰 객체를 반환한다.
    - JSP의 경우: `InternalResourceView(JstView)`를 반환하는데, 내부에 `forward()`로직이 있다.
  8. 뷰 렌더링: 뷰를 통해서 뷰를 렌더링 한다.

- 인터페이스 살펴보기
  - 스프링 MVC의 큰 강점은 `DispatcherServlet`코드의 변경 없이, 원하는 기능을 변경하거나
  확장할 수 있다는 점이다. 지금까지 설명한 대부분을 확장 가능할 수 있게 인터페이스로 제공한다.
  - 이 인터페이스들만 구현해서 `DispatcherServlet`에 등록하면 나만의 컨트롤러를 만들 수도 있다.

- 주요 인터페이스 목록
  - 핸들러 매핑: `org.springframework.web.servlet.HandlerMapping`
  - 핸들러 어댑터: `org.springframework.web.servlet.HandlerAdapter`
  - 뷰 리졸버: `org.springframework.web.servlet.ViewResolver`
  - 뷰: `org.springframework.web.servlet.View`
- 정리
  - 스프링 MVC는 코드 분량도 매우 많고, 복잡해서 내부 구조를 다 파악하는 것은 쉽지 않다.
  - 사실 해당 기능을 직접 확장하거나 나만의 컨트롤러를 만드는 일은 없으므로 걱정 하지 않아도 된다.
  - 왜냐하면 스프링 MVC는 전세계 수 많은 개발자들의 요구사항에 맞추어 기능을 계속 확장해왔고, 그래서
  우리의 웹 애플리케이션을 만들때 피룡로 하는 대부분의 기능이 이미 다 구현되어 있다.
  - 그래도 이렇게 핵심 동작방식을 알아두어야 향후 문제가 발생했을 때 어떤 부분에서 문제가 발생햇는지 쉽게
  파악하고, 문제를 해결할 수 있다. 그리고 확장 포인트가 필요할 때, 어떤 부분을 확장해야 할지 감을 잡을 수 있다.
  - 실제 다른 컴포넌트를 제공하거나 기능을 확장하는 부분들은 향후 정리하겠다. 지금은 전체적인 구조가 이렇게 되어 있구나 하고 이해하면 된다.
  

## 핸들러 매핑과 핸들러 어댑터
- 핸들러 매핑과 핸들러 어댑터가 어떤 것들이 어떻게 사용되는지 알아보자.
- 지금은 전혀 사용하지 않지만, 과거에 주로 사용했던 스프링이 제공하는 간단한 컨트롤러로 핸들러 매핑과 어댑털르 이해해보자.
### Controller 인터페이스
- 과거 버전 스프링 컨트롤러
  - `org.springframework.web.servlet.mvc.Controller`
  - 스프링도 처음에는 이런 딱딱한 형식의 컨트롤러를 제공했다.
  - 이 컨트롤러가 호출 되려면 다음 2가지가 필요하다.
    - HandlerMapping(핸들러 매핑)
      - 핸들러 매핑에서 이 컨트롤러를 찾을 수 있어야 한다.
      - ex) 스프링 빈의 이름으로 핸들러를 찾을 수 있는 핸들러 매핑이 필요하다.
    - HandlerAdapter(핸들러 어댑터)
      - 핸들러 매핑을 통해서 찾은 핸들러를 실행할 수 있는 핸들러 어댑터가 필요하다.
      - ex) `Controller`인터페이스를 실행할 수 있는 핸들러 어댑터를 찾고 실행해야 한다.
  - 스프링은 이미 필요한 핸들러 매핑과 핸들러 어댑터를 대부분 구현해두었다. 개발자가 직접 핻르럴 매핑과 핸들러 어댑터를 만드는일은 거의 없다.
- 스프링 부트가 자동 등록하는 핸들러 매핑과 핸들러 어댑터
  - 실제로는 더 많지만, 중요한 부분 위주로 설명하기 위해 일부 생략
  - HandlerMapping
    - 0순위 = RequestMappingHandlerMapping : 애노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
    - 1순위 = BeanNameUrlHandlerMapping : 스프링 빈의 이름으로 핸들러를 찾는다.
  - HandlerAdapter
    - 0순위 = RequestMappingHandlerAdapter : 애노테이션 기반의 컨트롤러인 @RequestMapping 에서 사용
    - 1순위 = HttpRequestHandlerAdapter : HttpRequestHandler 처리
    - 2순위 = SimpleControllerHandlerAdapter : Controller 인터페이스(애노테이션X, 과거에 사용) 처리
  - 핸들러 매핑도, 핸들러 어댑터도 모두 순서대로 찾고 만약 없으면 다음 순서로 넘어간다.
    1. 핸들러 매핑으로 핸들러 조회
      1. HandlerMapping을 순서대로 실행해서, 핸들러를 찾는다.
      2. 이 경우 빈 이름으로 핸들러를 찾아야 하기 떄문에 이름 그대로 빈 이름으로 핸들러를 찾아주는 `BeanNameUrlHandlerMapping`가 실행에 성공하고 핸들러인 `OldController`를 반환한다.
    2. 핸들러 어댑터 조회
      1. `HandlerAdapter`의 `supports()`를 순서대로 호출한다.
      2. `SimpleControllerHandlerAdapter`가 `Controller`인터페이스를 지원하므로 대상이 된다.
    3. 핸들러 어댑터 실행
      1. 디스패처 서블릿이 조회한 `SimpleControllerHandlerAdapter`를 실행하면서 핸들러 정보도 함께 넘겨준다.
      2. `SimpleControllerHandlerAdapter`는 핸들러인 `OldController`를 내부에서 실행하고, 그 결과를 반환한다.
    - 정리 - OldController 핸들러 어댑터 매핑, 어댑터
      - `OldController`를 실행하면서 사용된 객체는 다음과 같다.
      - `HandlerMapping = BeanNameUrlHandlerMapping`
      - `HandelrAdapter = SimpleControllerHandlerAdapter`
  
### HttpRequestHandler
- 핸들러 매핑과, 어댑터를 더 잘 이해하기 위해 Controller 인터페이스가 아닌 다른 핸들러를 알아보자.
- HttpRequestHandler핸들러(컨트롤러)는 "서블릿과 가장 유사한 형태"의 핸들러이다.
1. 핸들러 매핑으로 핸들러 조회
  1. HandlerMapping 을 순서대로 실행해서, 핸들러를 찾는다.
  2. 이 경우 빈 이름으로 핸들러를 찾아야 하기 떄문에 이름 그대로 빈 이름으로 핸들러를 찾아주는
  `BeanNameUrlHandlerMapping`가 실행에 성공하고 핸들러인 `MyHttpRequestHandler`를 반환한다.
2. 핸들러 어댑터 조회
  1. `HandlerAdapter`의 `supports()`를 순서대로 호출한다.
  2. `HttpRequestHandlerAdapter`가 `HttpRequestHandler` 인터페이스를 지원하므로 대상이 된다.
3. 핸들러 어댑터 실행
  1. 디스패처 서블릿이 조회한 `HttpRequestHandlerAdapter`를 실행하면서 핸들러 정보도 함께 넘겨준다.
  2. `HttpReqeustHandlerAdapter`는 핸들러인 `MyHttpRequestHandler`를 내부에서 실행하고, 그 결과를 반환한다.
- 정리 - MyHttpRequestHandler 핸들러 매핑, 어댑터
  - `MyHttpRequestHandler`를 실행하면서 사용된 객체는 다음과 같다.
  - `HandlerMapping = BeanNameUrlHandlerMapping`
  - `HandlerAdapter = HttpRequestHandlerAdapter`
- "@RequestMapping"
  - 조금 뒤에서 정리하겠지만, 가장 우선순위가 높은 핸들러 매핑과 핸들러 어댑터는 `RequestMappingHandlerMapping`, `RequestMappingHandlerAdapter`이다.
  - `@RequestMapping`의 앞글자를 따서 만든 이름인데, 이것이 바로 지금 스프링에서 주로 사용하는 애노테이션 기반의 컨트롤러를 
  지원하는 매핑과 어댑터이다. 실무에서는 99.9% 이 방식의 컨트롤러를 사용한다.
  

## 뷰 리졸버
- 실행해보면 컨트롤러를 정상 호출되지만, Whitelabel Error Page 오류가 발생한다.
- `application.properties`에 다음 코드를 추가하자.
  - spring.mvc.view.prefix=/WEB-INF/views/
  - spring.mvc.view.suffix=.jsp
- 뷰 리졸버 - InternalResourceViewResolver
  - 스프링 부트는 `InternlResourceViewResolver`라는 뷰 리졸버를 자동으로 등록하는데, 이때
  `application.properties`에 등록한 `spring.mvc.view.prefix`, `spring.vc.view.suffix`설정 정보를 사용해서 등록한다.
  - 참고로 권장하지는 않지만 설정 없이 전체경로를 주어도 동작하기는 한다.
- 스프링 부트가 자동 등록하는 뷰 리졸버 
  - (실제로는 더 많지만, 중요한 부분 위주로 설명하기 위해 일부 생략)
  - 1 = BeanNameViewResolver : 빈 이름으로 뷰를 찾아서 반환한다 ( 예: 엑셀 파일 생성 기능에 사용)
  - 2 = InternalResourceViewResolver : JSP를 처리할 수 있는 뷰를 반환한다.
1. 핸들러 어댑터 호출
  - 핸들러 어댑터를 통해 `new-form`이라는 논리 뷰 이름을 획득한다.
2. ViewResolver 호출
  - `new-form`이라는 뷰 이름으로 viewResolver를 순서대로 호출한다.
  - `BeanNameViewResolver`는 `new-form`이라는 이름의 스프링 빈으로 등록된 뷰를 찾아야 하는데 없다.
  - `InternalResourceViewResolver`가 호출된다.
3. InternalResourceViewResolver
  - 이 뷰 리졸버는 `InternalResourceView`를 반환한다.
4. 뷰 - InternalResourceView
  - `InternalResourceView`는 JSP처럼 포워드 `forward()`를 호출해서 처리할 수 있는 경우에 사용한다.
5. view.render()
  - `view.render()`가 호출되고 `InternalResourceView`는 `forward()`를 사용해서 JSP를 실행한다.
- 참고 
  - `InternalResourceViewResolver`는 만약 JSTL 라이브러리가 있으면 `InternalResourceView`를 상속받은 
  `JstlView`를 반환한다. `JstlView`는 JSTL태그 사용시 약간의 부가 기능이 추가된다.
  - 다른 뷰는 실제 뷰를 렌더링하지만, JSP의 경우 `forward()`통해서 해당 JSP로 이동(실행)해야 렌더링이 된다. JSP를 제외한 
  나머지 뷰 템플릿들은 `forward()`과정 없이 바로 렌더링 한다.
  - Thymeleaf뷰 템플릿을 사용하면 `ThymeleafViewResolver`를 등록해야 한다. 최근에는 라이브러리만 추가하면 스프링 부트가 이런 작업도 모두 자동화 해준다.


## 스프링 MVC - 시작하기
- 스프링이 제공하는 컨트롤러는 애노테이션 기반으로 동작해서, 매우 유연하고 실용적이다. 과거에는 자바 언어에 애노테이션이 없기도 햇꼬,
스프링도 처음부터 이런 유연한 컨트롤러를 제공한 것은 아니다.
- `@RequestMapping`
  - 스프링은 애노테이션을 활용한 매우 유연하고, 실용적인 컨트롤러를 만들었는데 이것이 바로 `@RequestMapping`애노테이션을
  사용하는 컨트롤러이다. 
  여담이지만 과거에는 스프링 프레임워크가 MVC부분이 약해서 스프링을 사용하더라도 MVC 웹 기술은 스트럿츠 같은 다른
  프레임웤르르 사용했었다. 그런데 `@RequestMapping`기반의 애노테이션 컨트롤러가 등장하면서, MVC 부분도 스프링의 완승으로 끝이 났다.
  - `RequestMappingHandlerMapping`
  - `RequestMappingHandlerAdapter`
- 앞서 보았듯이 가장 우선순위가 높은 핸들러 매핑과 핸들러 어댑터는 `RequestMappingHandlerMapping`,
RequestMappingHandlerAdapter이다. `@RequestMapping`의 앞글자를 따서 만든 이름인데, 이것이 바로 지금
스프링에서 주로 사용하는 애노테이션 기반의 컨트롤러를 지원하는 핸들러 매핑과 어댑터이다. 실무에서는 99.9% 이 방식의 컨트롤러를 사용한다.

- `@Controller`
  - 스프링이 자동으로 스프링 빈으로 등록한다. (내부에 `@Component` 애노테이션이 있어서 컴포넌트 스캔의 대상이 됨)
  - 스프링 MVC에서 애노테이션 기반 컨트롤러로 인식한다.
- `@RequestMapping`: 요청 정보를 매핑한다. 해당 URL이 호출되면 이 메서드가 호출된다. 애노테이션을 기반으로 동작하기 때문에, 메서드의 이름은 임의로 지으면 된다.
- `ModelAndView`: 모델과 뷰 정보를 담아서 반환하면 된다.
- `RequestMappingHandlerMapping`은 스프링 빈 중에서 `@RequestMapping` 또는 `@Controller`가 클래스 레벨에 붙어 있는 경우에 매핑 정보로 인식한다.


## 스프링 MVC - 컨트롤러 통합
- `@RequestMapping`을 잘 보면 클래스 단위가 아니라 메서드 단위에 적용된 것을 확인할 수 있다. 따라서 컨트롤러 클래스를 유연하게 하나로 통합할 수 있다.
- 조합
  - 컨트롤러 클래스를 통합하는 것을 넘어서 조합도 가능하다.
  - 다음 코드는 "/springmvc/v2/members"라는 부분에 중복이 있다.
## 스프링 MVC - 실용적인 방식
- MVC 프레임 워크 만들기에서 v3은 ModelView를 개발자가 직접 생성해서 반환했기 때문에, 불편햇던 기억이 날 것이다. 물론 v4를 만들면서 실용적으로 개선한 기억도 날 것이다.
- 스프링 MVC는 개발자가 편리하게 개발할 수 있돌고 수 많은 편의 기능을 제공한다.
- 실무에서는 여기에서 사용한 방법을 위주로 사용한다.
- Model 파라미터
  - save(), members()를 보면 Model을 파라미터로 받는 것을 확인할 수 있다. 스프링 MVC도 이런 편의 기능을 제공한다.
- ViewName 직접 반환
  - 뷰의 논리이름을 반환할 수 있다.
- @RequestParam 사용
  - 스프링은 HTTP 요청 파라미터를 `@RequestParam`으로 받을 수 있다.
  - `@RequestParam("username")`은 `request.getParameter("username")`와 거의 같은 코드라 생각하면 된다.
  물론 GET 쿼리파라미터, POST Form 방식을 모두 지원한다.
- @RequestMapping -> @GetMapping, @PostMapping
  - `@RequestMapping`은 URL만 매칭하는 것이 아니라, HTTP Method도 함께 구분할 수 있다.
  예를 들어서 URL이 `/new-form`이고, HTTP Method가 GET인 경우를 모두 만족하는 매핑을 하려면 method= RequestMethod.GET을 넣어주면 된다.
  - 위에것을 `@GetMapping`, `@PostMapping`으로 더 편리하게 상욯라 수 있다.
  참고로 Get, Post, Put, Delete, Patch 모두 애노테이션이 준비되어 있다.
    
# 6. 스프링 MVC - 기본 기능
## A. 프로젝트 생성
## B. 로깅 간단히 알아보기
- 앞으로 로그를 사용할 것이기 때문에, 이번에는 로그에 대해서 간단히 알아보자.
- 운영 시스템에서는 `System.out.println`같은 시스템 콘솔을 사용해서 필요한
정보를 출력하지 않고, 별도의 로깅 라이브러리를 사용해서 로그를 출력한다.
참고로 로그 관련 라이브러리도 많고, 깊게 들어가면 끝이 없기 때문에, 여기서는 최소한의 사용방법만 알아본다.
- 로깅 라이브러리
  - 스프링 부트 라이브러리를 사용하면 스프링 부트 로깅 라이브러리(`spring-boot-starter-logging`)가 함께 포함된다.
  스프링 부트 로깅 라이브러리는 기본으로 다음 로깅 라이브러리를 사용한다.
  - SLF4J - http:///www.slf4j.org
  - Logback - http://logback.qos.ch
- 로그 라이브러리는 Logback, Log4J, Log4J2 등등 수 많은 라이브러리가 있는데,
그것을 통합해서 인터페이스로 제공하는 것이 바로 SLF4J 라이브러리이다.
쉽게 이야기해서 SLF4J는 인터페이스이고, 그 구현체로 Logback 같은 로그 라이브러리를 
선택하면 된다. 실무에서는 스프링 부트가 제공하는 Logback을 대부분 사용한다.
- 로그 선언
  - private Logger log = LoggerFactory.getLogger(getClass());
  - private static final Logger log = LoggerFactory.getLogger(Xxx.class)
  - `@Slf4j`: 롬복 사용 가능
- 로그 호출
  - log.info("hello")
  - System.out.println("hello")
  시스템 콘솔로 직접 출력하는 것 보다 로그를 사용하면 다음과 같은 장점이 있다. 실무에서는 항상 로그를 사용해야 한다.

- 매핑 정보
  - `@ResController`
    - `@Controller`는 반환 값이 `String`이면 뷰 이름으로 인식된다. 그래서 "뷰를 찾고 뷰가 랜더링" 된다.
    - `@ResController`는 반환 값으로 뷰를 찾는 것이 아니라, "HTTP 메시지 바디에 바로 입력" 한다. 따라서 실행 결과로
    ok 메시지를 받을 수 있다. `@ResponseBody`와 관련이 있는데, 뒤에서 자세히 설명하겠다.
- 테스트
  - 로그가 출력되는 포멧 확인
    - 시간, 로그, 레벨, 프로세스ID, 쓰레드 명, 클래스 명, 로그 메시지
  - 로그 레벨 설정을 변경해서 출력 결과를 보자
    - LEVEL: TRACE > DEBUG > INFO > WARN > ERROR
    - 개발 서버는 debug 출력
    - 운영 서버는 info 출력
  - `@Slf4j`로 변경
- 올바른 로그 사용법
  - log.debug("data="+data)
    - 로그 출력 레벨을 info로 설정해도 해당 코드에 있는 "data="+data가 실제 실행이 되어 버린다. 결과적으로 문자 더하기 연산이 발생한다. ==>X
  - log.debug("data={}", data)
    - 로그 출력 레벨을 info로 설정하면 아무일도 발생하지 않는다. 따라서 앞과 같은 의미없는 연산이 발생하지 않는다 ==> O
- 로그 사용시 장점
  - 쓰레드 정보, 클래스 이름 같은 부가 정보를 함께 볼 수 있고, 출력 모양을 조정할 수 있다.
  - 로그 레벨에 따라 개발 서버에서는 모든 로그를 출력하고, 운영서버에서는 출력하지 않아도 되는 등 로그를 상황에 맞게 조절할 수 있다.
  - 시스템 아웃 콘솔에만 출력하는 것이 아니라, 파일이나 네트워크 등, 로그를 별도의 위치에 남길 수 있다. 특히 파일로 남길때는 일별, 특정 용량에 따라 로그를
  분할 하는 것도 가능하다.
  - 성능도 일반 System.out보다 좋다. (내부 버퍼링, 멀티 쓰레드 등등) 그래서 실무에서는 꼭 로그를 사용해야 한다.
  
## C. 요청 매핑
- 매핑 정보(한번더)
  - `@RestController`
    - `@Controller`는 반환 값이 `String` 이면 뷰 이름으로 인식 된다. 그래서
    "뷰를 찾고 뷰가 렌더링" 된다.
    - `@RestController`는 반환 값으로 뷰를 찾는 것이 아니라, "HTTP 메시지 바디에 바로 입력"한다. 따라서 실행 결과로
    ok 메시지를 받을 수 있다. `@ResponseBody`와 관련이 있는데, 뒤에서 더 자세히 설명한다.
  - `@RequestMapping("/hello-basic")`
    - `/hello-basic` URL 호출이 오면 이 메서드가 실행되도록 매핑한다.
    - 대부분의 속성을 `배열[]`로 제공하므로 다중 설정이 가능하다. `{"/hello-basic", "/hello-go"}`
- 둘다 허용
  - 다음 두가지 요청은 다른 URL이지만, 스프링은 다음 URL 요청들을 같은 요청으로 매핑한다.
    - 매핑: `/hello-basic` 
    - URL 요청: `/hello-basic`, `/hello-basic/`
- HTTP 메서드
  - `@RequestMapping`에 `method` 속성으로 HTTP 메서드를 지정하지 않으면 HTTP 메서드와 무관하게 호출된다.
  모두 허용 GET,HEAD,POST,PUT,PATCH,DELETE
- HTTP 메서드 매핑 축약
  - HTTP 메서드를 축약한 애노테이션을 사용하는 것이 더 직관적이다. 코드를 보면 내부에서 `@RequestMapping`과 `method`를 지정해서 사용하는 것을 확인할 수 있다.
- 최근 HTTP API는 다음과 같이 리소스 경로에 식별자를 넣는 스타일을 선호한다.?
  - `/mapping/userA`
  - `/users/1`
  - `@RequestMapping`은 URL 경로를 템플릿화 할 수 있는데, `@PathVariable`을 사용하면 매칭 되는 부분을 편리하게 조회할 수 있다.
  - `@PathVariable`의 이름과 파라미터 이름이 같으면 생략할 수 있다.
- HTTP API 그외 방법들
  - PathVariable 사용 - 다중
  - 특정 파라미터 조건 매핑
    - 특정 파라미터가 있거나 없는 조건을 추가할 수 있다. 잘 사용하지는 않는다.
  - 특정 헤더 조건 매핑
    - 파라미터 매핑과 비슷하지만, HTTP 헤더를 사용한다.
  - 미디어 타입 조건 매핑 - HTTP 요청 Content-Type, consume
    - HTTP 요청의 Content-Type 헤더를 기반으로 미디어 타입으로 매핑한다.
    만약 맞지 않으면 HTTP 415 상태코드(Unsupported Media Type)을 반환한다.
  - 미디어 타입 조건 매핑 - HTTP 요청 Accept, produce
    - HTTP 요청의 Accept 헤더를 기반으로 미디어 타입으로 매핑한다.
    만약 맞지 않으면 HTTP 406 상태코드(Not Acceptable)을 반환한다.
    
## D. 요청 매핑 - API 예시
- 회원 관리를 HTTP API로 만든다 생각하고 매핑을 어떻게 하는지 알아보자.
  (실제 데이터가 넘어가는 부분은 생략하고 URL매핑만)
- 회원 관리 API
  - 회원 목록 조회: GET `/users`
  - 회원 등록: POST `/users`
  - 회원 조회: GET `/users/{userId}`
  - 회원 수정: PATCH `/users/{userId}`
  - 회원 삭제: DELETE `/users/{userId}`
  
## E. HTTP 요청 - 기본, 헤더 조회
- 애노테이션 기반의 스프링 컨트롤러는 다양한 파라미터를 지원한다.
- 이번에는 HTTP 헤더 정보를 조회한느 방법을 알아보자.
- 조회 가능한 헤더
  - `HttpServletRequest`
  - `HttpSErvletREsponse`
  - `HttpMethod`: HTTP메서드를 조회한다.
  - `Locale` : Locale 정보를 조회한다 ex) ko_KR
  - `@RequestHeader MultiValueMap<String, String> headerMap`: 모든 HTTP 헤더를 MultiValueMap 형식으로 조회한다.
  - `@RequestHeader("host") String host` 
    - 특정 HTTP 헤더를 조회한다.
    - 속성
      - 필수 값 여부: `required`
      - 기본 값 속성: `defaultValue`
  - `@CookieVluae(value="myCookie", required=false) String cookie`
    - 특정 쿠키를 조회한다.
    - 속성
      - 필수 값 여부: `required`
      - 기본 값: `defaultValue`
- `MultiValueMap`
  - Map과 유사한데, 하나의 키에 여러 값을 받을 수 있다.
  - HTTP header, HTTP 쿼리 파라미터와 같이하나의 키에 여러 값을 받을 때 사용 한다.
    - keyA=value1&keyA=value2
- 참고
  - `@Controller`의 사용가능한 파라미터 목록
    - https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-arguments
  - `@Controller`의 사용가능한 응답 값 목록
    - https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-return-types

## F. HTTP 요청 파라미터 - 쿼리 파라미터, HTML Form
### HTTP 요청 데이터 조회 - 개요
- 서블릿에서 학습했던 HTTP 요청 데이터를 조회 하는 방법을 다시 떠올려보자. 그리고 서블릿으로 학습햇떤 내용을
스프링이 얼마나 깔끔하고 효율적으로 바꾸어 주는지 알아보자.
- HTTP 요청 메시지를 통해서 클라이언트에서 서버로 데이터를 전달하는 방법을 알아보자.
- 클라이언트에서 서버로 요청 데이터를 전달할 때는 주로 다음 3가지 방벙을 사용한다.
  - GET - 쿼리 파라미터
    - /url?username=hello&age=20
    - 메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달
    - ex) 검색, 필터, 페이징등에서 많이 사용하는 방식
  - POST - HTML Form
    - content-type: application/x-www-form-urlencoded
    - 메시지 바디에 쿼리 파라미터 형식으로 전달 username=hello&age=20
    - ex) 회원 가입, 상품 주문, HTML Form 사용
  - HTTP message body에 데이터를 직접 담아서 요청 
    - HTTP API에서 주로 사용, JSON, XML, TEXT
    - 데이터 형식은 주로 JSON사용
    - POST, PUT, PATCH
### 요청 파라미터 - 쿼리 파라미터, HTML Form
- `HttpServletRequest`의 `request.getParameter()`를 사용하면 다음 두가지 요청 파라미터를 조회할 수 있다.
  - GET, 쿼리 파라미터 전송
  - POST, HTML Form 전송 
  - GET 쿼리 파라미터 전송 방식이든, POST HTML Form 전송 방식이든 둘다 형식이 같으므로 구분없이 조회 할 수 있다.
  이것을 간단히 요청파라미터(request parameter)조회 라고 한다.
    
## G. HTTP 요청 파라미터 - @RequestParam
- 스프링이 제공하는 `@RequestParam`을 사용하면 요청 파라미터를 매우 편리하게 사용할 수 있다.
- `@RequestParam`: 파라미터 이름으로 바인딩
- `@ResponseBody`: View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
- `@RequestParam`의 `name(value)`속성이 파라미터 이름으로 사용
  - @RequestParam("username") String "memberName"
  - -> request.getParameter("username")
- HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
  - String, int, Integer 등의 단순 타입이면 `@RequestParam`도 생략 가능
- 참고
  - 이렇게 애노테이션을 완전히 생략해도 되는데, 너무 없는 것도 약간 과하다는 주관적 생각이 있다.
  - `@RequestParam`이 있으면 ㅁ여확하게 요청 파라미터에서 데이터를 읽는 다는 것을 알 수 있다.
- `@RequestParam.required`
  - 파라미터 필수 여부
  - 기본값이 파라미터 필수(`true`)이다.
- `/request-param`요청
  - `username`이 없으므로 400 예외가 발생한다.
- 주의! - 파라미터 이름만 사용
  - `/request-param?username=`
  - 파라미터 이름만 있고 값이 없는 경우 -> 빈 문자로 통과
- 주의 - 기본형(primitive)에 null 입력
  - `/request-param`요청
  - `@RequestParam(required = false) int age`
  - `null`을 `int`에 입력하는 것은 불가능(500 예외 발생)
    - 따라서 `null`을 받을 수 있는 `Integer`로 변경하거나, 또는 다음에 나오는 `defaultValue` 사용
- 기본값 적용 - requestParamDefault
  - 파라미터에 값이 없는 경우 `defaultValue`를 사용하면 기본 값을 적용할 수 있다.
  - 이미 기본 값이 있기 때문에 `required`는 의미가 없다.
  - `defaultValue`는 빈 문자의 경우에도 설정한 기본 값이 적용된다.
    - `/request-param?username=`
- 파라미터를 Map으로 조회하기 - requestParamMap
  - 파라미터를 Map, MultiValueMap으로 조회할 수 있다.
  - @RequestParam Map
    - Map(key=value)
  - @RequestParam MultiValueMap
    - MultiValueMap(key=[value1,value2,...] ex) key= userIds, value=[id1,id2])`
    - ?userIds=id1&userIds=id2
      - 파라미터의 값이 1개가 확실하다면 `Map`을 사용해도 되지만, 그렇지 않다면 `MultiValueMap`을 사용하자.

## H. HTTP 요청 파라미터 - ModelAttribute
- 실제 개발을 하면 요청 파라미터를 받아서 필요한 객체를 만들고 그 객체에 값을 넣어주어야 한다.
- 스프링은 이 과정을 완전히 자동화해주는 `@ModelAttribute`기능을 제공한다.
- 롬복 `@Data`
  - `@Getter`, `@Setter`, `@ToString`, `@EqualsAndHashCode`, `@RequiredArgsConstructor`를 자동으로 적용해준다.
- @ModelAttribute 적용 - modelAttributeV1
  - 마치 마법처럼 `HelloData`객체가 생성되고, 요청 파라미터의 값도 모두 들어가 있다.
  - 스프링 MVC는 `@ModelAttribute`가 있으면 다음을 실행한다.
    - `HelloData`객체를 생성한다.
    - 요청 파라미터의 이름으로 `HelloData`객체의 프로퍼티를 찾는다. 그리고 해당 프로퍼티의 setter를 호출해서 파라미터의 값을 입력(바인딩)한다.
    - ex) 파라미터 이름이 `username`이면 `setUsername()`메서드를 찾아서 호출하면서 값을 입력한다.
- 프로퍼티
  - 객체에 `getUsername()`, `setUsername()` 메서드가 있으면, 이 객체는 `username`이라는 프로퍼티를 가지고 있따.
  `usernmame` 프로퍼티의 값을 변경하면 `setUsername()`이 호출되고, 조회하면 `getUsername()`이 호출된다.
- 바인딩 오류
  - `age=abc` 처럼 숫자가 들어가야 할 곳에 문자를 넣으면 `BindException`이 발생한다. 이런 바인딩 오류를 처리하는 방법은 검증 부분에서 다룬다.
- @ModelAttribute 생략 - modelAttributeV2
  - `@ModelAttribute` 는 생략할 수 있다.
  - 그런데 `@RequestParam`도 생략할 수 있으니 혼란이 발생할 수 있다.
- 스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
  - `String`, `int`, `Integer`는 같은 단순타입 = `@RequestParam`
  - 나머지 = `@ModelAttribute`(argument resolver로 지정해둔 타입은 적용되지 않는다 ex) HttpServletResponse)
- 참고
  - argument resolver는 뒤에서 학습한다.
## I. HTTP 요청 메시지 - 단순 텍스트
- 서블릿에서 학습한 내용을 떠올려보자.
- HTTP message body에 데이터를 직접 담아서 요청
  - HTTP API에서 주로 사용, JSON, XML, TEXT
  - 데이터 형식은 주로 JSON 사용
  - POST, PUT, PATCH
  - 요청 파라미터와 다르게, HTTP 메시지 바디를 통해 데이터가 직접 데이터가 넘어오는 경우는 
  `@RequestParam`, `@ModelAttribute`를 사용할 수 없다.(물론 HTML Form 형식으로
    전달되는 경우는 요청 파라미터로 인정된다.)
- 먼저 가장 단순한 텍스트 메시지를 HTTP 메시지 바디에 담아서 전송하고, 읽어보자.
- HTTP 메시지 바디의 데이터를 `InputStream`을 사용해서 직접 읽을 수 있다.
- 스프링 MVC는 다음 파라미터를 지원한다.
  - HttpEntity: HTTP header, body 정보를 편리하게 조회
    - 메시지 바디 정보를 직접 조회
    - 요청 파라미터를 조회하는 기능과 관계 없음 `@RequestParam` X, `@ModelAttribute` X
  - HttpEntity는 응답에도 사용 가능
    - 메시지 바디 정볼 직접 변환
    - 헤더 정보 포함 가능
    - view 조회 X
- `HttpEntity`를 상속받은 다음 객체들도 같은 기능을 제공한다.
  - RequestEntity
    - HttpMethod, url 정보가 추가, 요청에서 사용
  - ResponseEntity
    - HTTP 상태 코드 설정 가능, 응답에서 사용
    - `return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED)`
- 참고
  - 스프링 MVC 내부에서 HTTP 메시지 바디를 읽어서 문자나 객체로 변환해서 전달해주는데, 이때 HTTP
  메시지 컨버터(`HttpMessageConverter`)라는 기능을 사용한다. 이것은 조금 뒤에 HTTP 메시지 컨버터에서
  자세히 설명한다.
- @RequestBody - requestBodyStringV4
  - @RequestBody
    - `@RequestBody`를 사용하면 HTTP 메시지 바디 정보를 편리하게 조회할 수 있다. 참고로 헤더 정보가 필요하다면
    `HttpEntity`를 사용하거나 `@RequestHeader`를 사용하면 된다.
    이렇게 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 `@RequestParam`, `@ModelAttribute`와는 전혀 관계가 없다.
  - 요청 파라미터 vs HTTP 메시지 바디
    - 요청 파라미터를 조회하는 기능: `@RequestParam`, `@ModelAttribute`
    - HTTP 메시지 바디를 직접 조회하는 기능: `@RequestBody`
  - @ResponseBody
    - `@ResponseBody`를 사용하면 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달할 수 있다.
    물론 이 경우에도 view를 사용하지 않는다.
  
## J. HTTP 요청 메시지 - JSON
- 이번에는 HTTP API에서 주로 사용하는 JSON 데이터 형식을 조회해보자.
- 기존 서블릿에서 사용했던 방식과 비슷하게 시작해보자.
- requestBodyJsonV3 - @RequestBody 객체 변환
  - @RequestBody 객체 파라미터
    - `@RequestBody HelloData data`
    - `@RequestBody` 에 직접 만든 객체를 지정할 수 있다.
    - `HttpEntity`, `@RequestBody`를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가
    원하는 문자나 객체 등으로 변환해준다.
    HTTP 메시지 컨버터는 문자 뿐만 아니라 JSON도 객체롤 변환해주는데, 우리가 방금 V2에서 했던 작업을 대신 처리 해준다.
    자세한 내용은 뒤에 HTTP 메시지 컨버터에서 다룬다.
  - @RequestBody는 생략 불가능
    - `@ModelAttribute`에서 학습한 내용을 떠올려보자
    - 스프링은 `@ModelAttribute`, `@RequestParam`해당 생략시 다음과 같은 규칙을 적용한다.
    - `String`,`int`, `Integer` 같은 단순 타입 = `@RequestParam`
    - 나머지 = `@ModelAttribute` (argument resolver로 지정해둔 타입은 예외)
    - 따라서 이 경우 HelloData에 `@RequestBody`를 생략하면 `@ModelAttribute`가 적용 되어 버린다.
    `HelloData data` -> `@ModelAttribute HelloData data`
    따라서 생략하면 HTTP 메시지 바디가 아니라 요청 파라미터를 처리하게 된다.
- 주의
  - HTTP 요청시에 content-type이 application/json 인지 꼭! 확인해야 한다. 그래야 JSON을 처리할 수 있는 HTTP 메시지 컨버터가 실행된다.
- 물론 앞서 배운 것과 같이 HttpEntity를 사용해도 된다.
  - requestBodyJsonV4 - HttpEntity
- requestBodyJsonV5
  - `@ResponseBody`
    - 응답의 경우에도 `@ResponseBody`를 사용하면 해당 객체를 HTTP 메시지 바디에 직접 넣어줄 수 있다.
    물론 이 경우에도 `HttpEntity`를 사용해도 된다.
  - `@RequestBody`요청
    - JSON 요청 -> HTTP 메시지 컨버터 -> 객체
  - `@ResponseBody` 응답
    - 객체 -> HTTP 메시지 컨버터 -> JSON 응답

## K. 응답 - 정적 리소스, 뷰 템플릿
- 응답 데이터는 이미 앞에서 일부 다룬 내용들이지만, 응답 부분에 초점을 맞추어서 정리해보자.
스프링(서버)에는 응답 데이터를 만드는 방법은 크게 3가지 이다.
- 정적 리소스
  - ex) 웹 브라우저에 정적인 HTML, css, js 을 제공할 떄는 "정적 리소스"를 사용한다.
- 뷰 템플릿 사용
  - ex) 웹 브라우저에 동적인 HTML을 제공할 때는 뷰 템플릿을 사용한다.
- HTTP 메시지 사용
  - HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야 하므로, HTTP 메시지 바디에
  JSON 같은 형식으로 데이터를 실어 보낸다.
### 정적 리소스
- 스프링 부트는 클래스패스의 다음 디렉토리에 있는 정적 리소스를 제공한다.
  - `/static`, `/public`, `/resources`, `/META-INF/resources`
  - `src/main/resources`는 리소스를 보관하는 곳이고, 또 클래스패스의 시작 경로이다.
  따라서 다음 디렉토리에 리소르를 넣어두면 스프링 부트가 정적 리소스로 서비스를 제공한다.
  - 정적 리소스 경로: `src/main/resoucres/static`
  - 다음 경로에 파일이 들어있으면: `src/main/resources/static/basic/hello-form.html`
  - 웹 브라우저에서 다음과 같이 실행하면 된다: `http://localhost:8080/basic/hello-form.html`
  - 정적 리소스는 해당 파일을 변경 없이 그대로 서비스 하는 것이다.
### 뷰 템플릿
- 뷰 템플릿을 거쳐서 HTML이 생성되고, 뷰가 응답을 만들어서 전ㄷ라한다.
일반적으로 HTML을 동적으로 생성하는 용도로 사용하지만, 다른 것들도 가능하다. 뷰 템플릿이 만들 수 
있는 것이라면 뭐든지 가능하다.
- 스프링 부트는 기본 뷰 템플릿 경로를 제공한다.
- "뷰 템플릿 경로": `src/main/resoucres/templates`
- "뷰 템플릿 생성": `src/main/resoucres/templates/response/hello.html`
- String을 반환하는 경우 - View or HTTP 메시지
  - `@ResponseBody`가 없으면 `response/hello`로 뷰 리졸버가 실행 되어서 뷰를 찾고, 렌더링 한다.
  - `@ResponseBody`가 있으면 뷰 리졸버를 실행하지 않고, HTTP 메시지 바디에 직접 `response/hello`라는 문자가 입력된다.
  - 여기서는 뷰의 논리 이름인 `response/hello`를 반환하면 다음 경로의 뷰 템플릿이 렌더링 되는 것을 확인할 수 있다.
    - 실행: `templates/response/hello.html`
  - Void를 반환하는 경우
    - `@Controller`를 사용하고, `HttpServletResponse`, `OutputStream(Writer)` 같은 HTTP 메시지 바디를 처리하는 
    파라미터가 없으면 요청 URL을 참고해서 논리 뷰 이름으로 사용
      - 요청 URL: `/response/hello`
      - 실행: `templates/response/hello.html`
    - 참고로 이 방식은 명시성이 너무 떨어지고 이렇게 딱 맞는 경우도 많이 없어서, 권장하지 않는다.
  - HTTP 메시지
    - `@ResponseBody`, `HttpEntity`를 사용하면, 뷰 템플릿을 사용하는 것이 아니라, HTTP 메시지 바디에 직접 응답 데이터를 출력할 수 있다.
### Thymeleaf 스프링 부트 설정
- 다음 라이브러리를 추가하면("이미 추가 되어 있다")
  - build.gradle: implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
  - 스프링 부트가 자동으로 `ThymeleafViewResolver`와 필요한 스프링 빈들을 등록한다. 그리고 다음 설정도 사용한다. 
  이 설정은 기본 값 이기 때문에 변경이 필요할 때만 설정하면 된다.
  - `application.properties` 이 설정이 디폴트 값이다.
    - spring.thymeleaf.prefix=classpath:/templates/
    - spring.thymeleaf.suffix=.html
## L. HTTP 응답 - HTTP API, 메시지 바디에 직접 입력
- HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야 하므로, HTTP 메시지 바디에 JSON 같은 형식으로 데이터를 실어 보낸다.
- HTTP 요청에서 응답까지 대부분 다루었으므로 이번 시간에는 정리를 해보자.
- 참고
  - HTML이나 뷰 템플릿을 사용해도 HTTP 응답 메시지 바디에 HTML 데이터가 담겨서 전달 된다. 여기서 설명하는 내용은 정적 리소스나 뷰 템플릿을 거치지
  않고, 직접 HTTP 응답 메시지를 전달하는 경우를 말한다.
- responseBodyV1
  - 서블릿을 직접 다룰 때 처럼 HttpServletResponse 객체를 통해서 HTTP 메시지 바디에 직접 `ok`응답 메시지를 전달한다.
    - response.getWriter().write("ok)
- responseBodyV2
  - ResponseEntity 엔티티는 HttpEntity를 상속 받았는데, HttpEntity는 HTTP 메시지의 헤더, 바디 정보를 가지고 있다.
  `ResponseEntity`는 여기에 더해서 HTTP 응답 코드를 설정할 수 있다.
  - HttpStatus.CREATED 로 변경하면 201 응답이 나가는 것을 확인할 수 있다.
- responseBodyV3
  - `@ResponseBody`를 사용하면 view를 사용하지 않고, HTTP 메시지 컨버터를 통해서 HTTP 메시지를 직접 입력할 수 있다.
  `ResponseEntity`도 동일한 방식으로 동작한다.
- responseBodyJsonV1
  - ResponseEntity를 반환한다. HTTP 메시지 컨버터를 통해서 JSON 형식으로 반환되어서 반환된다.
- responseBodyJsonV2
  - ResponseEntity는 HTTP 응답 코드를 설정할 수 있는데, `@ResponseBody`를 사용하면 이런 것을 설정하기 까다롭다.
  `@ResponseStatus(HttpStatus.OK)`애노테이션을 사용하면 응답 코드로 설정할 수 있다.
  - 물론 애노테이션이기 때문에 응답 코드를 동적으로 변경할 수는 없다. 프로그램 조건에 따라서 동적으로 변경 하려면 `ResponseEntity`를 사용하면된다.
- @RestController
  - @Controller 대신에 @RestController 애노테이션을 사용하면, 해당 컨트롤러에 모두 `@ResponseBody`가 적용되는 효과가 있다.
  따라서 뷰 템플릿을 사용하는 것이 아니라, HTTP 메시지 바디에 직접 데이터를 입력한다. 이름 그대로 Rest API(HTTP API)를 만들 때 사용하는 컨트롤러이다.
  - 참고로 `@ResponseBody`는 클래스 레벨에 두면 전체 메서드에 적용되는데, `@ResController` 애노테이션 안에 `@ResponseBody`가 적용되어 있다.

## M. HTTP 메시지 컨버터
- 뷰 템플릿으로 HTML을 생성해서 응답하는 것이 아니라, HTTP API처럼 JSON 데이터를 HTTP 메시지 바디에서 직ㅈ버 읽거나 쓰는 경우 HTTP 컨버터를 사용하면 편리하다.
- `@ResponseBody` 사용 원리 (과거 스프링 입문 버전)
  - HTTP의 BODY에 문자 내용을 직접 반환
  - `viewResolver` 대신에 `HttpMessageConverter`가 동작
  - 기본 문자처리: `StringHttpMessageConverter`
  - 기본 객체처리: `MappingJackson2HttpMessageConverter` ( ObjectMapper )
  - bvyte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음
- 참고
  - 응답의 경우 클라이언트의 HTTP Accept 해더와 서버의 컨트롤러 반환 타입 정보 둘을 조합해서
  `HttpMesageConverter`가 선택된다. 더 자세한 내용은 스프링 MVC에서 설명하겠다.
- 스프링 MVC는 다음의 경우에 HTTP 메시지 컨버터를 적용한다.
  - HTTP 요청: `@RequestBody`, `HttpEntitty(RequestEntity)`
  - HTTP 응답: `@ResponseBody`, `HttpEntity(ResponseEntity)`
- HTTP 메시지 컨버터 인터페이스
  - `org.springframework.http.converter.HttpMessageConverter`
  - HTTP 메시지 컨버터는 HTTP 요청, HTTP 응답 둘 다 사용 된다.
    - `canRead()`, `canWriter()`: 메시지 컨버터가 해당 클래스, 미디어타입을 지원하는지 체크
    - `read()`, `writer()`: 메시지 컨버터를 통해서 메시지를 읽고 쓰는 기능
- 스프링 부트 기본 메시지 컨버터
  - 0 = ByteArrayHttpMesageConverter
  - 1 = StringHttpMessageConverter
  - 2 = MappingJackson2HttpMessageConverter
  - 스프링 부트는 다양한 메시지 컨버터를 제공하는데, 대상 클래스 타입과 미디어 타입 둘을 체크해서 사용 여부를 결정한다.
  만약 만족하지 않으면 다음 메시지 컨버터로 우선순위가 넘어간다.
  - 몇가지 주요한 메시지 컨버터를 알아보자.
    - `ByteArrayHttpMessageConverter`: `byte[]` 데이터를 처리한다.
      - 클래스 타입: `byte[]`, 미디어타입: `*/*`,
      - 요청 예) `@RequestBody byte[] data`
      - 응답 예) `@ResponseBody return byte[]`쓰기 미디어타입 `application/octet-stream`
    - `StringHttpMessageConverter`: `String`문자로 데이터를 처리한다.
      - 클래스 타입: `String`, 미디어타입: `*/*`
      - 요청 예) `@RequestBody String data`
      - 응답 예) `@ResponseBody return "ok"` 쓰기 미디어타입 `test/plain`
    - `MappingJackson2HttpMessageConverter`: application/json
      - 클래스 타입: 객체 또는 `HashMap`, 미디어 타입 `application/json` 관련
      - 요청 예) `@RequestBody HelloData data`
      - 응답 예) `@ResponseBody return helloData`쓰기 미디어타입 `applicaion/json`관련
- HTTP 요청 데이터 읽기
  - HTTP 요청이 오고, 컨트롤러에서 `@RequestBody`, `HttpEntity` 파라미터를 사용한다.
  - 메시지 컨버터가 메시지를 읽을 수 있는지 확인하기 위해 `canRead()`를 호출한다.
    - 대상 클래스 타입을 지원하는가
      - ex) `@RequestBody`의 대상 클래스 (`byte[]`, `String`, `HelloData`)
    - HTTP 요청의 Content-Type 미디어 타입을 지원하는가
      - ex) `text/plain`, `application/json`, `*/*`
    - `canRead()` 조건을 만족하면 `read()`를 호출해서 객체 생성하고, 반환한다.
- HTTP 응답 데이터 생성
  - 컨트롤러에서 `@ResponseBody`, `HttpEntity`로 값이 반환된다.
  - 메시지 컨버터가 메시지를 쓸 수 있는지 확인하기 위해 `canWrite()`를 호출한다.
    - 대상 클래스 타입을 지원하는가
      - 예) return의 대상 클래스 (`byte[]`,`String`,`HelloData`)
    - HTTP 요청의 Accept 미디어 타입을 지원하는가 ( 더 정확히는 `@RequestMapping`의 `produces`)
      - 예) `text/plain`, `application/json`, `*/*`
    - `canWrite()`조건을 만족하면 `write()`를 호출해서 HTTP 응답 메시지 바디에 데이터를 생성한다.
- 예시1
  - content-type: application/json
  - @RequestMapping void Hello(@RequestBody String data) {}
  - ==> 이 경우에는 RequestBody가 String 타입의 클래스인데, StringHttpMessageConverter가 미디어타입 */*(아무거나가능) 이므로 StringHttpMessageConverter가 호출된다.
  - ==> 즉, 정답 StringHttpMessageConverter
- 예시2
  - content-type: application/json
  - @RequestMapping void Hello(@RequestBody HelloData data) {}
  - ==> 즉, 정답 MappingJackson2HttpMessageConverter
- 예시3 (오류뜸)
  - content-type: text/html
  - @RequestMapping void Hello(@RequestBody HelloData data) {}
  - ==> 정답: ??? 해당되는게 없다.
## N. 요청 매핑 헨들러 어뎁터 구조
- HTTP 메시지 컨버터는 스프링 MVC 어디쯤에서 사용 되는 것일까?
- SpringMVC 구조
  - 모든 비밀은 애노테이션 기반의 컨트롤러, 그러니까 `@RequestMapping`을 처리하는 핸들러 어댑터인
  `RequestMappingHandlerAdapter`(요청 매핑 핸들러 어댑터)에 있다.
- RequestMappingHandlerAdapter 동작 방식
  - ArgumentResolver
    - 생각해보면, 애노테이션 기반의 컨트롤러는 매우 다양한 파라미터를 사용할 수 있었다.
    - `HttpServletRequest`, `Model`은 물론이고, `@RequestParam`, `@ModelAttribute`같은
    애노테이션 그리고 `@RequestBody`, `HttpEntity`같은 HTTP 멧지리ㅡㄹ 처리하는 부분까지 매우 큰 유연함을 
    보여주었다. 이렇게 파라미터를 유연하게 처리할 수 있는 이유가 바로 `ArgumentResolver`덕분이다.
    - 애노테이션 기반 컨트롤러를 처리하는 `RequestMappingHandlerAdapter`는 바로 이 `ArgumentReolsver`
    호출해서 컨트롤러(핸들러)가 필요로 하는 다양한 파라미터의 값(객체)을 생성한다. 그리고 이렇게 파라미터의 값이
    모두 준비되면 컨트롤러를 호출하면서 값을 넘겨준다.
    - 스프링은 30개가 넘는 `ArgumentResolver`를 기본으로 제공한다. 
  - 정확히는 `HandlerMethodArgumentResolver`인데 줄여서 `ArgumentResolver`라고 부른다.
  - 동작 방식
    - `ArgumentResolver`의 `supportsParameter()`를 호출해서 해당 파라미터를 지원하는지 체크하고, 지원하면 
    `resolveArgument()`를 호출해서 실제 객체를 생성한다. 그리고 이렇게 생성된 객체가 컨트롤러 호출 시 넘어가는 것이다.
    그리고 원한다면 우리가 직ㅈ버 이 인터페이스를 확장해서 원하는 `ArgumentResolver`를 만들 수도 있다.
    실제 확장하는 예제는 향후 로그인 처리에서 진행하겠다.
  - RetuenValueHandler
    - `HandlerMethodReturnValueHandler`를 줄여서 `ReturnValueHandle`라 부른다.
    - `ArgumentResolver`와 비슷한데, 이것은 응답 값을 변환하고 처리한다.
    - 컨트롤러에서 String으로 뷰 이름을 반환해도, 동작하는 이유가 바로 ReturnValueHandler 덕분이다.
    - 스프링은 10여개가 넘는 `ReturnValueHandler`를 지원한다. ex) `ModelAndView`, `@ResponseBody`, `HttpEntity`, `String`
### HTTP 메시지 컨버터
- HTTP 메시지 컨버터 위치
  - HTTP 메시지 컨버터는 어디쯤 있을까?
  - HTTP 메시지 컨버터를 사용하는 `@RequestBody`도 컨트롤러가 필요로 하는 파라미터의 값에 사용된다.
  `@ResponseBody`의 경우도 컨트롤러의 반환 값을 이용한다.
  - 요청
    - `@RequestBody`를 처리하는 `ArgumentResolver`가 있고, `HttpEntity`를 처리하는
    `ArgumentResovler`가 있다. 이 `ArgumentResolver`들이 HTTP 메시지 컨버터를 사용해서
    필요한 객체를 생성하는 것이다.
  - 응답
    - `@ResponseBody`와 `HttpEntity`를 처리하는 `ReturnValueHandler`가 있다. 그리고 여기에서
    HTTP 메시지 컨버터를 호출해서 응답 결과를 만든다.
  - 스프링 MVC는 `@RequestBody` `@ResponseBody`가 있으면 `RequestResponseBodyMethodProcessor`(ArgumentResolver)
    `HttpEntity`가 있으면 `HttpEntityMethodProcessor`(ArgumentResolver)를 사용한다.
### 확장
- 스프링은 다음을 모두 인터페이스로 제공한다. 따라서 필요하면 언제든지 기능을 확장할 수 있다.
  - `HandlerMethodArgumentResolver`
  - `HandlerMethodReturnValueHandler`
  - `HttpMessageConverter`
- 스프링이 필요한 대부분의 기능을 제공하기 떄문에 실제 기능을 확장할 일이 많지는 않다. 기능 확장은
`WebMvcConfigurer`를 상속 받아서 스프링 빈으로 등록하면 된다. 실제 자주 사용하지는 않으니
실제 기능 확장이 필요할 때 `WebMvcConfigurer`를 검색해보자.
  

# 7. 스프링 MVC - 웹 페이지 만들기
## A. 프로젝트 생성
## B. 요구사항 분석
- 상품을 관리할 수 있는 서비스를 만들어보자
- "상품 도메인 모델"
  - 상품 ID
  - 상품명
  - 가격
  - 수량
- "상품 관리 기능"
  - 상품 목록
  - 상품 상세
  - 상품 등록
  - 상품 수정
- 요구사항이 정리되고 디자이너, 웹 퍼블리셔, 백엔드 개발자가 업무를 나누어 진행한다.
  - 디자이너: 요구사항에 맞도록 디자인하고, 디자인 결과물을 웹 퍼블리셔에게 넘겨준다.
  - 웹 퍼블리셔: 디자이너에서 받은 디자인을 기반으로 HTML, CSS를 만들어 개발자에게 제공한다.
  - 백엔드 개발자: 디자이너, 웹 퍼블리셔를 통해서 HTML화면이 나오기 전까지 시스템을 설계하고, 핵심 비즈니스 모델을 개발한다.
  , 이후 HTML이 나오면 이 HTML을 뷰 템플릿으로 변환해서 동적으로 화면으로 그리고, 또 웹 화면의 흐름을 제어한다.
    
- 참고
  - React, Vue.js 같은 웹 클라이언트 기술을 사용하고, 웹 프론트엔드 개발자가 별도로 있으면, 웹 프론트엔드 개발자가 웹 퍼블리셔 역할까지 포함해서 하는
  경우도 있다. 웹 클라이언트 기술을 사용하면, 웹 프론트엔드 개발자가 HTML을 동적으로 만드는 역할과 웹 화면의 흐름을 담당한다.
  이 경우 백엔드 개발자는 HTML 뷰 템플릿을 직접 만지는 대신에, HTTP API를 통해 웹 클라이언트가 필요로 하는 데이터와 기능을 제공하면 된다.
## C. 상품 도메인 개발
## D. 상품 서비스 HTML
- 참고
  - 부트스트랩은 웹사잍르ㅡㄹ 쉽게 만들 수 있게 도와주는 HTMl, CSS, JS 프레임 워크이다.
  하나의 CSS로 휴대폰, 태블릿, 데스크탑까지 다양한 기기에서 작동한다. 다양한 기능ㅇ르 제공하여 사용자가
  쉽게 웹사이트를 제작, 유지, 보수 할 수 있도록 도와준다.

## E. 상품 목록 - 타임리프
## F. 상품 상세
## G. 상품 등록 폼
## H. 상품 등록 처리 - @ModelAttribute
## I. 상품 수정
## J. PRG Post/Redirect/Get
## K. RedirectAttributes
## L. 정리