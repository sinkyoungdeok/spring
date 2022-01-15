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

### 7.3 REST API 클라이언트가 추가된 타코 클라우드 애플리케이션 빌드 및 실행하기

</details>





