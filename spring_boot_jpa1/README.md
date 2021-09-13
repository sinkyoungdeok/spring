# 1. 프로젝트 환경설정
<details> <summary> 1. 프로젝트 생성</summary>

</details>

<details> <summary> 2. 라이브러리 살펴보기</summary>

- gradle 의존관계 보기
    - `./gradlew dependencies -configuration compileClasspath`

- spring-boot-starter-web
    - spring-boot-starter-tomcat: 톰캣 (웹서버)
    - spring-webmvc: 스프링 웹 MVC
- spring-boot-starter-thymeleaf: 타임리프 템플릿 엔진(View)
- spring-boot-starter-data-jpa
    - spring-boot-starter-aop
    - spring-boot-starter-jdbc
        - HikariCP 커넥션 풀 (부트 2.0 기본)
    - hibernate + JPA: 하이버네이트 + JPA
    - spring-data-jpa: 스프링 데이터 JPA
- spring-boot-starter(공통): 스프링 부트 + 스프링 코어 + 로깅
    - spring-boot
        - spring-core
    - spring-boot-starter-logging
- logback, slf4j

### 테스트 라이브러리
- spring-boot-starter-test
    - junit: 테스트 프레임워크
    - mockito: 목 라이브러리
    - assertj: 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리
    - spring-test: 스프링 통합 테스트 지원
- 핵심 라이브러리
    - 스프링 MVC
    - 스프링 ORM
    - JPA, 하이버네이트
    - 스프링 데이터 JPA
- 기타 라이브러리
    - H2 데이터베이스 클라이언트
    - 커넥션 풀: 부트 기본은 HikariCP
    - WEB(thymeleaf)
    - 로깅 SLF4J & LogBack
    - 테스트

참고: 스프링 데이터 JPA는 스프링과 JPA을 먼저 이해하고 사용해야 하는 응용기술이다.

</details>

<details> <summary> 3. View 환경설정</summary>

- thymeleaf 템플릿 엔진
    - thymeleaf 공식 사이트: https://www.thymeleaf.org/
    - 스프링 공식 튜토리얼: https://spring.io/guides/gs/serving-web-content/
    - 스프링부트 메뉴얼: https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/
      boot-features-developing-web-applications.html#boot-features-spring-mvc-template-engines
- 스프링 부트 thymeleaf viewName 매핑
    - `resources:templates/` + (ViewName) + `.html`

- 참고: spring-boot-devtools 라이브러리를 추가하면, html 파일을 컴파일만 해주면 서버 재시작 없이
View 파일 변경이 가능하다.
- 인텔리J 컴파일 방법: 메뉴 build Recompile

</details>

<details> <summary> 4. H2 데이터베이스 설치 </summary>

</details>

<details> <summary> 5. JPA와 DB 설정, 동작 확인 </summary>

</details>
